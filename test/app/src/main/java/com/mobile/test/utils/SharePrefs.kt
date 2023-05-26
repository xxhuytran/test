package com.mobile.test.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mobile.test.model.response.SignUpResponseModel

class SharedPrefs(context: Context) {
    val preferences: SharedPreferences = context.getSharedPreferences("SavePreferences", Context.MODE_PRIVATE)

    companion object {
        const val User = "User"
    }

    fun saveLocalSimpleData(key: String, value: Any) {
        val editor = preferences.edit()
        when (value) {
            is Int -> {
                editor.putInt(key, value)
            }
            is String -> {
                editor.putString(key, value)
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
            is Float -> {
                editor.putFloat(key, value)
            }
            is MutableSet<*> -> {
                editor.putStringSet(key, value as MutableSet<String>)
            }
            is SignUpResponseModel -> {
                val json = Gson().toJson(value)
                editor.putString(key, json)
            }
        }
        editor.apply()
    }

    inline fun <reified T> getLocalSimpleData(key: String): Any? {
        when (T::class) {
            Float::class -> {
                return preferences.getFloat(key, -1f); // -1 as null
            }
            Int::class -> {
                return preferences.getInt(key, -1); // -1 as null
            }
            String::class -> {
                return preferences.getString(key, "");
            }
            Boolean::class -> {
                return preferences.getBoolean(key, false) // only false or true, not null
            }
            SignUpResponseModel::class -> {
                val json: String? = preferences.getString(key, null)
                return Gson().fromJson(json, SignUpResponseModel::class.java)
            }

        }
        return null
    }

    fun removeByKey(key: String) {
        val editor = preferences.edit()
        editor.remove(key)
        editor.apply()
    }
}