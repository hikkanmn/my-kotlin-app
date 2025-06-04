package ru.hspm.vikas_recipe_app

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import android.content.res.Configuration
import android.content.Intent
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val languageGroup: RadioGroup = findViewById(R.id.languageGroup)
        val themeGroup: RadioGroup = findViewById(R.id.themeGroup)

        // Получаем текущие предпочтения
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val currentLanguage = preferences.getString("language", "en")
        val currentTheme = preferences.getString("theme", "light")

        // Устанавливаем начальные значения
        if (currentLanguage == "ru") {
            languageGroup.check(R.id.lang_ru)
        } else {
            languageGroup.check(R.id.lang_en)
        }

        if (currentTheme == "dark") {
            themeGroup.check(R.id.theme_dark)
        } else {
            themeGroup.check(R.id.theme_light)
        }

        // Обработчики переключений
        languageGroup.setOnCheckedChangeListener { _, checkedId ->
            val language = when (checkedId) {
                R.id.lang_ru -> "ru"
                else -> "en"
            }
            changeLanguage(language)
        }

        themeGroup.setOnCheckedChangeListener { _, checkedId ->
            val theme = when (checkedId) {
                R.id.theme_dark -> "dark"
                else -> "light"
            }
            changeTheme(theme)
        }
    }

    private fun changeLanguage(language: String) {
        val locale = if (language == "ru") {
            Locale("ru")
        } else {
            Locale("en")
        }

        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.edit().putString("language", language).apply()

        // Перезапуск активности для применения изменений
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun changeTheme(theme: String) {
        if (theme == "dark") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.edit().putString("theme", theme).apply()
    }
    fun goBack(view: View) {
        finish() // Закрывает вторую активность и возвращает на первую
    }
}