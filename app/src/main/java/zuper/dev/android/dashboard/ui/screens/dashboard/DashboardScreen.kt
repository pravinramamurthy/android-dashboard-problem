package zuper.dev.android.dashboard.ui.screens.dashboard

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import zuper.dev.android.dashboard.data.model.ChartData
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.ui.navigation.Screen
import zuper.dev.android.dashboard.ui.theme.Blue
import zuper.dev.android.dashboard.ui.theme.Green
import zuper.dev.android.dashboard.ui.theme.Purple
import zuper.dev.android.dashboard.ui.theme.Red
import zuper.dev.android.dashboard.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    navHostController: NavHostController,
    viewModel: DashBoardViewModel
) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBar(title = {
                    Text(
                        text = "Dashboard",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge

                    )
                })
                Divider(
                    color = Color.Gray, modifier = Modifier
                        .fillMaxWidth()
                        .height(.5.dp)
                )
                ProfileCard()
                JobStatsCard(onClickNav = {
                    viewModel.setList(it)
                    navHostController.navigate(Screen.Jobs.name)

                })
                InvoiceStatsCard()

            }


        }
    }

}


@Composable
fun RowWithRectangles(items: List<List<ChartData>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items.forEach { pair ->
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp)
            ) {
                pair.forEach { _item ->
                    Row(
                        modifier = Modifier

                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(
                                    color = when (_item.status) {
                                        JobStatus.Completed.name, InvoiceStatus.Paid.name -> Green
                                        JobStatus.YetToStart.name -> Purple
                                        JobStatus.InProgress.name, InvoiceStatus.Pending.name -> Blue
                                        JobStatus.Canceled.name, InvoiceStatus.Draft.name -> Yellow
                                        JobStatus.Incomplete.name, InvoiceStatus.BadDebt.name -> Red
                                        else -> {}
                                    } as Color
                                ), contentAlignment = Alignment.Center
                        ) {
                            // Small rectangle
                        }
                        Text(
                            modifier = Modifier.padding(6.dp, 0.dp),
                            text = _item.status + "(${_item.count})",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Gray

                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Chart(completionText: String, data: List<ChartData>, showRect: Boolean) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,


        ) {
        Text(
            text = data[0].total,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray

        )
        Text(
            text = completionText,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray

        )
    }

    Row(
        modifier = Modifier
            .padding(8.dp, 0.dp)
            .wrapContentSize(),
    ) {


        data.forEachIndexed { index, item ->
            val isFirst = index == 0
            val isLast = index == data.size - 1
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(item.value),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(15.dp)  // Adjust the height as needed
                        .fillMaxWidth()
                        .clip(
                            when {
                                isFirst -> RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                                isLast -> RoundedCornerShape(bottomEnd = 12.dp, topEnd = 12.dp)
                                else -> RoundedCornerShape(0.dp)
                            }
                        )
                        .background(
                            color = when (item.status) {
                                JobStatus.Completed.name, InvoiceStatus.Paid.name -> Green
                                JobStatus.YetToStart.name -> Purple
                                JobStatus.InProgress.name, InvoiceStatus.Pending.name -> Blue
                                JobStatus.Canceled.name, InvoiceStatus.Draft.name -> Yellow
                                JobStatus.Incomplete.name, InvoiceStatus.BadDebt.name -> Red
                                else -> {}
                            } as Color
                        )
                )

            }
        }

    }
    if (showRect) {
        val pairedItems = data.chunked(2)
        RowWithRectangles(pairedItems)
    }


}


@Composable
fun ProfileCard() {
    val unicode = 0x1F44B
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp, 16.dp)
            .border(.2.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 12.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {

            Column(
                modifier = Modifier.wrapContentSize(),


                ) {
                Text(
                    text = "Hello, Zuper ! ${getEmojiByUnicode(unicode)}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
                getData()
                Text(
                    text = getData(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray

                )

            }
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.ic_launcher_background),

                contentDescription = "profile"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobStatsCard(
    onClickNav: (jobsChartData: List<ChartData>) -> Unit,
    viewModel: DashBoardViewModel = hiltViewModel()
) {
    val jobsState = viewModel.jobsStateFlow.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = jobsState) {
        viewModel.observeJobs()
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(8.dp, 16.dp)
        .clickable { onClickNav(jobsState.value) }
        .border(.2.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ), shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Jobs Stats",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        Divider(
            color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .height(.5.dp)
        )
        if (jobsState.value.isEmpty()) {
            Text(text = "Loading...")
        } else {
            Chart(
                completionText = jobsState.value[0].completion,
                data = jobsState.value.sortedByDescending { it.value },
                showRect = true
            )
        }

    }
}

@Composable
fun InvoiceStatsCard(viewModel: DashBoardViewModel = hiltViewModel()) {
    val invoiceState = viewModel.invoiceStateFlow.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = invoiceState) {
        viewModel.observeInvoice()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp, 16.dp)
            .border(.2.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Invoice Stats",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        Divider(
            color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .height(.5.dp)
        )
        if (invoiceState.value.isEmpty()) {
            Text(text = "Loading...")
        } else {
            Chart(
                completionText = invoiceState.value[0].completion,
                data = invoiceState.value.sortedByDescending { it.value },
                showRect = true
            )
        }

    }
}


@SuppressLint("SimpleDateFormat")
fun getData(): String {
    val sdf = SimpleDateFormat("EEEE, MMM dd yyyy")
    return sdf.format(Date())

}

//Convert unicode to emoji
fun getEmojiByUnicode(unicode: Int): String {
    return String(Character.toChars(unicode))
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.wrapContentSize(), color = MaterialTheme.colorScheme.background
        ) {}
    }
}