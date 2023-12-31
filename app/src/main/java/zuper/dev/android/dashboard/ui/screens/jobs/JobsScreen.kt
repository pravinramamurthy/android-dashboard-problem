package zuper.dev.android.dashboard.ui.screens.jobs

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.data.model.ChartData
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobScreenData
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.ui.navigation.Screen
import zuper.dev.android.dashboard.ui.screens.dashboard.Chart
import zuper.dev.android.dashboard.ui.screens.dashboard.DashBoardViewModel
import zuper.dev.android.dashboard.ui.screens.dashboard.InvoiceStatsCard
import zuper.dev.android.dashboard.ui.screens.dashboard.ProfileCard
import zuper.dev.android.dashboard.ui.theme.AppTheme
import kotlin.math.log

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
            }


        }
    }


}


@Composable
fun JobStatsCard(
    dashBoardViewModel: DashBoardViewModel
) {
    val jobsState = dashBoardViewModel.jobsChartData.collectAsState(initial = emptyList())
    Log.d("jobsScreen", "JobStatsCard: ${jobsState.value}")
    if (jobsState.value.isEmpty()) {
        Text(text = "Loading...")
    } else {
        Chart(
            completionText = jobsState.value[0].completion,
            data = jobsState.value.sortedByDescending { it.value },
            showRect = false
        )
        TabScreen(jobsState.value[0].jobList)

    }
}

@Composable
fun TabScreen(jobs: List<JobApiModel>) {
    val groupedJobs = jobs.groupBy { it.status }

    val tabTitles = groupedJobs.keys.toList()

    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.Black,
            edgePadding = 0.dp,

            ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title.name) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        JobsList(groupedJobs[tabTitles[selectedTabIndex]] ?: emptyList())
    }
}

@Composable
fun JobsList(jobs: List<JobApiModel>) {
    LazyColumn {
        items(jobs) { job ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp, 8.dp)
                    .border(.2.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "#${job.jobNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = " ${job.startTime}- ${job.endTime}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray


                    )
                }
            }
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
//            TabScreen()
        }
    }
}