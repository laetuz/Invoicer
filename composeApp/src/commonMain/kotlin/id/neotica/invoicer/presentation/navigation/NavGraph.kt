package id.neotica.invoicer.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import id.neotica.invoicer.presentation.screen.MainView

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None },
        startDestination = Screen.MainScreen
    ) {
        composable<Screen.MainScreen> { MainView(navController) }
    }
}