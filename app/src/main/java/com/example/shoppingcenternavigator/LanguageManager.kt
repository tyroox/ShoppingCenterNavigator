package com.example.shoppingcenternavigator

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.Locale

object LanguageManager {
    private const val PREF_SELECTED_LANGUAGE = "pref_selected_language"
    private const val DEFAULT_LANGUAGE = "en" // Default language code

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            "LanguagePreferences",
            Context.MODE_PRIVATE
        )
    }

    fun getSelectedLanguage(): String {
        return sharedPreferences.getString(PREF_SELECTED_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }

    fun setSelectedLanguage(languageCode: String) {
        sharedPreferences.edit {
            putString(PREF_SELECTED_LANGUAGE, languageCode)
            apply()
        }
    }

    fun getLocale(): Locale {
        val selectedLanguage = getSelectedLanguage()
        return Locale(selectedLanguage)
    }
}