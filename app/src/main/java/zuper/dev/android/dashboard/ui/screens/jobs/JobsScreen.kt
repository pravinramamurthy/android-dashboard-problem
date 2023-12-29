package zuper.dev.android.dashboard.ui.screens.jobs

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.data.model.ChartData
import zuper.dev.android.dashboard.ui.navigation.Screen
import zuper.dev.android.dashboard.ui.screens.dashboard.Chart
import zuper.dev.android.dashboard.ui.screens.dashboard.DashBoardViewModel
import zuper.dev.android.dashboard.ui.screens.dashboard.InvoiceStatsCard
import zuper.dev.android.dashboard.ui.screens.dashboard.ProfileCard
import zuper.dev.android.dashboard.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(
    navHostController: NavHostController,
    dashBoardViewModel: DashBoardViewModel
) {

    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBar(title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            navHostController.navigateUp()
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "",
                                tint = Color.Black
                            )
                        }

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Jobs",
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge,


                            )
                    }

                })
                Divider(
                    color = Color.Gray, modifier = Modifier
                        .fillMaxWidth()
                        .height(.5.dp)
                )
                JobStatsCard(dashBoardViewModel)
                TabScreen()
            }


        }
    }


}

@Composable
fun JobStatsCard(
    dashBoardViewModel: DashBoardViewModel
) {
    val jobsState = dashBoardViewModel.jobsChartData.collectAsState(initial = emptyList())
    if (jobsState.value.isEmpty()) {
        Text(text = "Loading...")
    } else {
        Chart(
            completionText = jobsState.value[0].completion,
            data = jobsState.value.sortedByDescending { it.value },
            showRect = false
        )
    }
}

@Composable
fun TabScreen() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Home", "About", "Settings", "More", "Something", "Everything")

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            edgePadding = 0.dp,
            modifier = Modifier.fillMaxWidth().padding(start = 0.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },

                    )
            }
        }
        when (tabIndex) {
//            0 -> HomeScreen()
//            1 -> AboutScreen()
//            2 -> SettingsScreen()
//            3 -> MoreScreen()
//            4 -> SomethingScreen()
//            5 -> EverythingScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.wrapContentSize(), color = MaterialTheme.colorScheme.background
        ) {
            TabScreen()
        }
    }
}