package com.alvintio.calcuwaste.utils

import android.content.Context
import android.content.SharedPreferences
import com.alvintio.calcuwaste.utils.Const.EMAIL
import com.alvintio.calcuwaste.utils.Const.IS_LOGIN
import com.alvintio.calcuwaste.utils.Const.NAME
import com.alvintio.calcuwaste.utils.Const.PREFS_NAME
import com.alvintio.calcuwaste.utils.Const.TOKEN
import com.alvintio.calcuwaste.utils.Const.USER_ID

internal class Preference(context: Context) {
    private var pref: SharedPreferences = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun setStringPreference(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun setBooleanPreference(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun clearPreference(){
        editor.clear().apply()
    }

    val token = pref.getString(TOKEN, "")
    val name = pref.getString(NAME, "")
    val email = pref.getString(EMAIL, "")
}