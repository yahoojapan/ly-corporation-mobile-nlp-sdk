package jp.co.lycorp.jlp.testapp.android.data

import android.content.Context

object TestAppPreferences {
    private const val SETTINGS = "settings"

    lateinit var settings: TestAppSettings
        private set

    @JvmStatic
    fun init(context: Context) {
        settings = TestAppSettings(context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE))
    }
}
