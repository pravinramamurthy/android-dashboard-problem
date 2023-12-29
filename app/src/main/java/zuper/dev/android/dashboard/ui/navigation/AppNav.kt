package zuper.dev.android.dashboard.ui.navigation


enum class Screen {
    Dashboard,
    Jobs,
}

sealed class NavigationItem(val route: String) {
    object Dashboard : NavigationItem(Screen.Dashboard.name)
    object Jobs : NavigationItem(Screen.Jobs.name)
}