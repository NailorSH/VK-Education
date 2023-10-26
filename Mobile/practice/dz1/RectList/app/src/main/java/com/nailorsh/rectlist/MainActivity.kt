package com.nailorsh.rectlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nailorsh.rectlist.ui.theme.RectListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RectListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RectListApp()
                }
            }
        }
    }
}

@Composable
fun RectListApp() {
    var num by remember { mutableStateOf(1) }
    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        RectList(num)
        Spacer(modifier = Modifier.weight(1f))
        MyButton(onButtonClicked = { num++ })
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun RectList(num: Int) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
    )
    { items((1..num).toList()) { Square(index = it) } }
}



@Composable
fun Square(index: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .fillMaxSize()
            .background(color = if (index % 2 == 0) Color.Red else Color.Blue)
    ) {
        Text(index.toString())
    }
}

@Composable
fun MyButton(onButtonClicked: () -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(342.dp)
            .height(52.dp)
            .background(color = Color.Black, shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
            .clickable { onButtonClicked() }

    ) {
        Text(
            text = stringResource(R.string.add_button),
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 32.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            ),
        )
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GreetingPreview() {
    RectListTheme {
        Row {
            Square(1)
            Square(2)
            Square(3)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ListPreview() {
    RectListTheme {
        RectListApp()
    }
}