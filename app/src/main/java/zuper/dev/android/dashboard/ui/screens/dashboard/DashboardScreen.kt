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
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel


val slices = listOf(
    Slice(value = 14.6f, color = Color(0XFFe31a1a), text = "55"),
    Slice(value = 61.8f, color = Color(0XFF377eb8), text = "233"),
    Slice(value = 23.6f, color = Color(0XFF49a345), text = "89")
)
val items = listOf(
    Item(Color.Blue, "Item 1"),
    Item(Color.Green, "Item 2"),
    Item(Color.Red, "Item 3"),
    // Add more items as needed
)
val pairedItems = items.chunked(2)

data class Slice(val value: Float, val color: Color, val text: String)
data class Item(val color: Color, val text: String)

@Composable
fun DashBoardScreen(
    navHostController: NavHostController,
    viewModel: DashBoardViewModel = hiltViewModel()
) {
    val jobs = viewModel.jobsStateFlow.collectAsState(initial = emptyList())
    Log.d("DashBoardScreen", "jobs: ${jobs.value} ")
    TopBar()
}


@Composable
fun RowWithRectangles(items: List<List<Item>>) {
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
                pair.forEach { item ->
                    Row(
                        modifier = Modifier

                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(
                                    color = item.color, shape = RoundedCornerShape(2.dp)
                                ), contentAlignment = Alignment.Center
                        ) {
                            // Small rectangle
                        }
                        Text(
                            modifier = Modifier.padding(6.dp, 0.dp),
                            text = "yet to start(12)",
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
fun ChartCard() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,


        ) {
        Text(
            text = "60 Jobs",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray

        )
        Text(
            text = "20 out of 60 completed",
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


        slices.forEachIndexed { index, slice ->
            val isFirst = index == 0
            val isLast = index == slices.size - 1
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(slice.value),
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
                        .background(color = slice.color),

                    )

            }
        }

    }

    RowWithRectangles(pairedItems)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Column {
        TopAppBar(title = {
            Text(
                text = "Dashboard", color = Color.Black, style = MaterialTheme.typography.titleLarge

            )
        })
        Divider(
            color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .height(.5.dp)
        )

        ProfileCard()
        JobStatsCard()
        InvoiceStatsCard()
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

@Composable
fun JobStatsCard() {
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
            text = "Jobs",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        Divider(
            color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .height(.5.dp)
        )
        ChartCard()
    }
}

@Composable
fun InvoiceStatsCard() {
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
            text = "Jobs",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        Divider(
            color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .height(.5.dp)
        )
        ChartCard()
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
        ) {
            TopBar()
        }
    }
}