package jp.co.lycorp.jlp.testapp.android

import android.app.Application
import jp.co.lycorp.jlp.testapp.android.data.TestAppPreferences

class TestAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TestAppPreferences.init(this)
    }
}
