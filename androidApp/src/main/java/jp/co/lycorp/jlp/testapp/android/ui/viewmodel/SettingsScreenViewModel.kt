package jp.co.lycorp.jlp.testapp.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import jp.co.lycorp.jlp.testapp.android.data.JlpHandlerStore
import jp.co.lycorp.jlp.testapp.android.data.TestAppPreferences
import jp.co.lycorp.jlp.testapp.android.data.TestAppSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsScreenViewModel(
    private val testAppSettings: TestAppSettings,
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T = SettingsScreenViewModel(
                testAppSettings = TestAppPreferences.settings,
            ) as T
        }
    }

    private val _uiState = MutableStateFlow(SettingsScreenUiState(editingAppId = testAppSettings.appId, savedAppId = testAppSettings.appId, isSaveEnabled = false))
    val uiState = _uiState.asStateFlow()

    fun onEditValueChanged(text: String) {
        val isSaveEnabled = text.isNotBlank() && text != uiState.value.savedAppId
        _uiState.update { it.copy(editingAppId = text, isSaveEnabled = isSaveEnabled) }
    }

    fun onSaveAppId() {
        val newAppId = uiState.value.editingAppId.trim()
        testAppSettings.appId = newAppId
        _uiState.update { it.copy(savedAppId = testAppSettings.appId, isSaveEnabled = false) }
        JlpHandlerStore.updateAppId()
    }
}
