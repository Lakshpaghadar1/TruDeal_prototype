package com.trudeals.core

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CryptLib @Inject constructor(
    @param:Named("aes-key") private val encKey: String,
    @param:Named(
        "iv-key"
    ) private var encIv: String,
) {
    private val CHARSET = "UTF-8"
    private var hashKey: ByteArray

    /**
     * Encryption mode enumeration
     */
    private enum class EncryptMode {
        ENCRYPT, DECRYPT
    }

    // cipher to be used for encryption and decryption
    private var _cx: Cipher? = null

    // encryption key and initialization vector
    private val _key: ByteArray
    private val _iv: ByteArray

    /**
     * @param inputText Text to be encrypted or decrypted
     * @param mode      specify the mode encryption / decryption
     * @return encrypted or decrypted bytes based on the mode
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    @Throws(
        UnsupportedEncodingException::class,
        InvalidKeyException::class,
        InvalidAlgorithmParameterException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    private fun encryptDecrypt(inputText: String, mode: EncryptMode): ByteArray {
        System.arraycopy(hashKey, 0, _key, 0, 32)
        System.arraycopy(encIv.toByteArray(charset(CHARSET)), 0, _iv, 0, 16)
        val keySpec = SecretKeySpec(
            _key,
            "AES"
        ) // Create a new SecretKeySpec for the specified key data and algorithm name.
        val ivSpec =
            IvParameterSpec(_iv) // Create a new IvParameterSpec instance with the bytes from the specified buffer iv used as initialization vector.

        // encryption
        return if (mode == EncryptMode.ENCRYPT) {
            // Potentially insecure random numbers on Android 4.3 and older. Read for more info.
            // https://android-developers.blogspot.com/2013/08/some-securerandom-thoughts.html
            _cx!!.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec) // Initialize this cipher instance
            _cx!!.doFinal(inputText.toByteArray(charset(CHARSET))) // Finish multi-part transformation (encryption)
        } else {
            _cx!!.init(Cipher.DECRYPT_MODE, keySpec, ivSpec) // Initialize this cipher instance
            val decodedValue = Base64.decode(inputText.toByteArray(), Base64.DEFAULT)
            _cx!!.doFinal(decodedValue) // Finish multi-part transformation (decryption)
        }
    }

    /***
     * This function computes the SHA256 hash of input string
     * @param text input text whose SHA256 hash has to be computed
     * @param length length of the text to be returned
     * @return returns SHA256 hash of input text
     * @throws NoSuchAlgorithmException when algorithm not found
     * @throws UnsupportedEncodingException when encoding is not supported
     */
    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    private fun SHA256(text: String, length: Int): ByteArray {
        val resultString: String
        val md = MessageDigest.getInstance("SHA-256")
        md.update(text.toByteArray(charset(CHARSET)))
        val digest = md.digest()
        val result = StringBuilder()
        for (b in digest) {
            result.append(String.format("%02x", b)) //convert to hex
        }
        resultString = if (length > result.toString().length) {
            result.toString()
        } else {
            result.toString().substring(0, length)
        }
        return resultString.toByteArray(charset(CHARSET))
    }

    fun encryptPlainText(plainText: ByteArray?): ByteArray {
        var bytes = ByteArray(0)
        return try {
            bytes = encryptDecrypt(String(plainText!!, charset(CHARSET)), EncryptMode.ENCRYPT)
            Base64.encodeToString(bytes, Base64.NO_WRAP).toByteArray(charset(CHARSET))
        } catch (e: Exception) {
            e.printStackTrace()
            bytes
        }
    }

    fun encryptSimple(plainText: String): String {
        var bytes = ByteArray(0)
        try {
            bytes = encryptDecrypt(plainText, EncryptMode.ENCRYPT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Base64.encodeToString(
            bytes,
            Base64.NO_WRAP
        ) /*.replaceAll(System.getProperty("line.separator"), "")*/
    }

    /* public String decryptCipherText(String cipherText) {

         byte[] bytes = new byte[0];
         try {
             bytes = encryptDecrypt(cipherText, EncryptMode.DECRYPT);
         } catch (Exception e) {
             Timber.e(e);
         }
         return new String(bytes);
     }*/
    fun decryptSimple(cipherText: String): String {
        try {
            System.arraycopy(hashKey, 0, _key, 0, 32)
            System.arraycopy(encIv.toByteArray(charset(CHARSET)), 0, _iv, 0, 16)
            val keySpec = SecretKeySpec(
                _key,
                "AES"
            ) // Create a new SecretKeySpec for the specified key data and algorithm name.
            val ivSpec =
                IvParameterSpec(_iv) // Create a new IvParameterSpec instance with the bytes from the specified buffer iv used as initialization vector.

            // encryption
            _cx!!.init(Cipher.DECRYPT_MODE, keySpec, ivSpec) // Initialize this cipher instance
            val decodedValue = Base64.decode(cipherText.toByteArray(), Base64.DEFAULT)
            return String(_cx!!.doFinal(decodedValue)) // Finish multi-part transformation (decryption)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        }
        return ""
    }

    @Throws(Exception::class)
    fun encryptPlainTextWithRandomIV(plainText: String): String {
        encIv = generateRandomIV16()
        val bytes = encryptDecrypt(encIv + plainText, EncryptMode.ENCRYPT)
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun decryptCipherTextWithRandomIV(cipherText: String): String {
        encIv = generateRandomIV16()
        val bytes = encryptDecrypt(cipherText, EncryptMode.DECRYPT)
        val out = String(bytes)
        return out.substring(16, out.length)
    }

    /**
     * Generate IV with 16 bytes
     *
     * @return random iv
     */
    fun generateRandomIV16(): String {
        val ranGen = SecureRandom()
        val aesKey = ByteArray(16)
        ranGen.nextBytes(aesKey)
        val result = StringBuilder()
        for (b in aesKey) {
            result.append(String.format("%02x", b)) //convert to hex
        }
        return if (16 > result.toString().length) {
            result.toString()
        } else {
            result.toString().substring(0, 16)
        }
    }

    init {

        // initialize the cipher with transformation AES/CBC/PKCS5Padding
        try {
            _cx = Cipher.getInstance("AES/CBC/PKCS5Padding")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        }
        _key = ByteArray(32) //256 bit key space
        _iv = ByteArray(16) //128 bit IV
        hashKey = try {
            SHA256(encKey, 32)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            ByteArray(32)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            ByteArray(32)
        }
    }
}