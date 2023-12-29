package zuper.dev.android.dashboard.ui.screens

import android.app.slice.Slice
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.Greeting
import zuper.dev.android.dashboard.ui.theme.AppTheme

val slices = listOf(
    Slice(value = 14.6f, color = Color(0XFFe31a1a), text = "55"),
    Slice(value = 61.8f, color = Color(0XFF377eb8), text = "233"),
    Slice(value = 23.6f, color = Color(0XFF49a345), text = "89")
)
data class Slice(val value: Float, val color: Color, val text:String)

@Composable
fun DashBorard() {
    Row(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxSize(),
    ) {
        slices.forEach {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(it.value),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = it.value.toString(),
                    color = Color.Black,
                    modifier = Modifier.padding(4.dp)
                )
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()

                        .background(it.color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = it.text, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ProfileCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Content inside the card
            // Add your content here
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DashBorard()
        }
    }
}