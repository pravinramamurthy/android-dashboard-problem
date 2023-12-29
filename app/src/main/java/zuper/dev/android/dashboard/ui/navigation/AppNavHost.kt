package zuper.dev.android.dashboard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import zuper.dev.android.dashboard.ui.screens.dashboard.DashBoardScreen
import zuper.dev.android.dashboard.ui.screens.JobsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDest: String = NavigationItem.Dashboard.route,

    ) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDest
    ) {
        composable(NavigationItem.Dashboard.route) {
            DashBoardScreen(navController)
        }
        composable(NavigationItem.Jobs.route) {
            JobsScreen(navController)
        }
    }
}