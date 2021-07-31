package com.kresna.movies.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {

    companion object {
        const val MEETING_PREF = "USER_PREF"
    }

    val sharedPref = context.getSharedPreferences(MEETING_PREF, 0)

    fun setValues(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun getAll(): MutableMap<String, *>? {
        return sharedPref.all
    }

    @SuppressLint("CommitPrefEdits")
    fun clearValues(key: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(key)
    }

    fun clearAll() {
        val settings = context.getSharedPreferences(MEETING_PREF, 0)
        settings.edit().clear().apply()
    }
}