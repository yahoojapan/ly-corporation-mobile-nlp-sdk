package jp.co.lycorp.jlp.testapp.android.data

import jp.co.lycorp.jlp.mobilenlp.kit.JlpHandlerType
import jp.co.lycorp.jlp.mobilenlp.kit.MobileJlpHandler
import jp.co.lycorp.jlp.mobilenlp.kit.MobileJlpHandlerFactory

object JlpHandlerStore {
    @Volatile
    private var _jlpHandler = createOnlineHandler()

    val jlpHandler: MobileJlpHandler
        get() = _jlpHandler

    fun updateAppId() {
        _jlpHandler = createOnlineHandler()
    }

    private fun createOnlineHandler() = MobileJlpHandlerFactory.create(JlpHandlerType.Online(TestAppPreferences.settings.appId))
}
