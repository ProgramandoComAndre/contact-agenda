package pt.programandocomandre.contactagenda

import android.content.Context
import android.content.SharedPreferences

object DataUtils {
    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    fun writePin(pin: String, context: Context) {
        var sharedPreferences = getSharedPreferences(context)

        sharedPreferences.edit().putString("pin", pin).apply()
    }
}