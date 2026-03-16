package jp.co.lycorp.jlp.testapp.android.data

import android.content.SharedPreferences
import androidx.core.content.edit
import jp.co.lycorp.jlp.testapp.AppSecrets

class TestAppSettings(private val preferences: SharedPreferences) {
    companion object {
        private const val APP_ID = "app_id"
    }

    var appId: String
        get() = preferences.getString(APP_ID, AppSecrets.APPID) ?: ""
        set(value) = preferences.edit { putString(APP_ID, value) }
}
