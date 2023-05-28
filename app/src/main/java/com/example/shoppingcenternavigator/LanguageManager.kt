package com.example.shoppingcenternavigator

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.core.content.edit
import java.util.Locale

object LanguageManager {
    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}