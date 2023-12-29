package zuper.dev.android.dashboard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import zuper.dev.android.dashboard.ui.screens.dashboard.DashBoardScreen
import zuper.dev.android.dashboard.ui.screens.dashboard.DashBoardViewModel
import zuper.dev.android.dashboard.ui.screens.jobs.JobsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDest: String = NavigationItem.Dashboard.route,

    ) {
    val viewModel: DashBoardViewModel = viewModel()

    NavHost(
        modifier = modifier, navController = navController, startDestination = startDest
    ) {
        composable(NavigationItem.Dashboard.route) {
            DashBoardScreen(navController, viewModel = viewModel)
        }
        composable(NavigationItem.Jobs.route) {
            JobsScreen(navController, dashBoardViewModel = viewModel)
        }
    }
}