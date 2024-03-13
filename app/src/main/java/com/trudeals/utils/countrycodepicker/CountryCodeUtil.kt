package com.trudeals.utils.countrycodepicker

import android.app.Activity
import com.google.gson.Gson
import java.io.IOException
import java.nio.charset.Charset

/**
 * Created by hlink on 31/1/18.
 */
object CountryCodeUtil {
    fun loadJSONFromAsset(activity: Activity): String? {
        var json: String? = null
        json = try {
            val `is` = activity.assets.open("countryCode.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun parseCountryCode(activity: Activity): List<CountryWithFlag> {
        val jsonStr = loadJSONFromAsset(activity)
        val gson = Gson()
        val countryCode = gson.fromJson(jsonStr, CountryCode::class.java)
        return countryCode.countries
    }
}