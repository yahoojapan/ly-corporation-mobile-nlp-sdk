package jp.co.lycorp.jlp.testapp.android.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.co.lycorp.jlp.testapp.android.ui.screen.MainScreen
import jp.co.lycorp.jlp.testapp.android.ui.screen.SettingsScreen
import jp.co.lycorp.jlp.testapp.android.ui.viewmodel.MainScreenViewModel
import jp.co.lycorp.jlp.testapp.android.ui.viewmodel.SettingsScreenViewModel
import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object Main : NavigationRoute

    @Serializable
    data object Settings : NavigationRoute
}

@Composable
fun JlpTestApp() {
    val navController = rememberNavController()

    Scaffold {
        NavHost(
            navController = navController,
            modifier =
                Modifier
                    .padding(it)
                    .consumeWindowInsets(it)
                    .fillMaxSize(),
            enterTransition = {
                fadeIn(animationSpec = tween(350))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(350))
            },
            startDestination = NavigationRoute.Main,
        ) {
            composable<NavigationRoute.Main> {
                MainScreen(
                    viewModel = viewModel(factory = MainScreenViewModel.Factory),
                    toSettingsScreen = { navController.navigate(route = NavigationRoute.Settings) },
                )
            }
            composable<NavigationRoute.Settings> {
                SettingsScreen(
                    viewModel = viewModel(factory = SettingsScreenViewModel.Factory),
                    onClickBack = { navController.popBackStack() },
                )
            }
        }
    }
}
