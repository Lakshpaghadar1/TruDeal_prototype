package com.trudeals.core

import com.trudeals.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.Buffer
import java.io.IOException
import javax.inject.Inject

/**
 * Created by hlink21 on 29/11/17.
 */

class AESCryptoInterceptor @Inject constructor(cryptLib: CryptLib?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val cryptLib = CryptLib(BuildConfig.aesKey, BuildConfig.ivKey)

        val request = chain.request()

        val plainQueryParameters = request.url.queryParameterNames
        var httpUrl = request.url
        // Check Query Parameters and encrypt
        if (plainQueryParameters != null && !plainQueryParameters.isEmpty()) {
            val httpUrlBuilder = httpUrl.newBuilder()
            for (i in plainQueryParameters.indices) {
                val name = httpUrl.queryParameterName(i)
                val value = httpUrl.queryParameterValue(i)
                val enc = cryptLib.encryptSimple(value!!)
                httpUrlBuilder.setQueryParameter(name, enc)
            }
            httpUrl = httpUrlBuilder.build()
        }

        // Get Header for encryption
        val apiKey = request.headers[Session.API_KEY]
        val token = request.headers[Session.USER_SESSION]
        val language = request.headers[Session.LANGUAGE]

        val newRequest: Request
        var requestBuilder = request.newBuilder()

        val encryptedApiKey = cryptLib.encryptSimple(apiKey!!)
//       val encryptedToken = cryptLib.encryptSimple(token!!)
        val encryptedLanguage = cryptLib.encryptSimple(language!!)


        // Check if any body and encrypt
        val requestBody = request.body
        if (requestBody != null && requestBody.contentType() != null) {
            // bypass multipart parameters for encryption
            val isMultipart = requestBody.contentType()!!
                .type.equals("multipart", ignoreCase = true)
            val bodyPlainText =
                if (isMultipart) bodyToString(requestBody) else cryptLib.encryptPlainText(
                    bodyToString(requestBody)
                )
            if (!isMultipart) {
                if (bodyPlainText != null) {
                    requestBuilder.post(
                        RequestBody.create(
                            "text/plain".toMediaTypeOrNull(),
                            bodyPlainText
                        )
                    )
                }
            } else {
                requestBuilder.post(requestBody)
            }
        }

        // Build the final request
//        newRequest = requestBuilder.url(httpUrl)
//            .header("API-KEY", cryptLib.encryptSimple(apiKey))
//            .header("TOKEN", cryptLib.encryptSimple(token))
//            .build()

        requestBuilder.url(httpUrl)
        requestBuilder.header(Session.API_KEY, cryptLib.encryptSimple(apiKey!!))
        if (!token.isNullOrEmpty())
            requestBuilder.header(Session.USER_SESSION, cryptLib.encryptSimple(token!!))
        requestBuilder.header(Session.LANGUAGE, cryptLib.encryptSimple(language!!))


        newRequest = requestBuilder.build()

        // execute the request
        val proceed = chain.proceed(newRequest)

        // get the response body and decrypt it.
        val cipherBody = proceed.body!!.string()
        val plainBody = cryptLib.decryptSimple(cipherBody)

        // create new Response with plaint text body for further process
        return proceed.newBuilder()
            .body(
                ResponseBody.create("text/json".toMediaTypeOrNull(),
                    plainBody.trim { it <= ' ' })
            )
            .build()
    }

    private fun bodyToString(request: RequestBody?): ByteArray? {
        return try {
            val buffer = Buffer()
            if (request != null) request.writeTo(buffer) else return null
            buffer.readByteArray()
        } catch (e: IOException) {
            null
        }
    }
}