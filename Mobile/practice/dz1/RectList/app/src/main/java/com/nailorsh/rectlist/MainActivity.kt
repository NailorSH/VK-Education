package com.nailorsh.rectlist

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nailorsh.rectlist.ui.theme.RectListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RectListTheme {
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
    var num by rememberSaveable { mutableStateOf(1) }
    val weightOfList: Float
    val weightOfButton: Float
    val columnNumber: Int

    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            weightOfList = 4f
            weightOfButton = 1f
            columnNumber = 4
        }

        Configuration.ORIENTATION_PORTRAIT -> {
            weightOfList = 11f
            weightOfButton = 1f
            columnNumber = 3
        }

        else -> {
            TODO()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        RectList(modifier = Modifier.weight(weightOfList), num = num, columnNumber = columnNumber)
        Spacer(modifier = Modifier.height(5.dp))
        MyButton(
            modifier = Modifier.weight(weightOfButton),
            onButtonClicked = { num++ },
            text = R.string.add_button
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RectList(modifier: Modifier = Modifier, num: Int = 3, columnNumber: Int = 3) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnNumber),
        contentPadding = PaddingValues(2.dp),
        modifier = modifier
    ) {
        items(num) {
//            Rectangle(
//                modifier = Modifier
//                    .padding(2.dp)
//                    .aspectRatio(2f)
//                    .animateItemPlacement(),
//                index = it
//            )
            Square(
                modifier = Modifier
                    .padding(2.dp)
                    .animateItemPlacement(),
                index = it + 1
            )
        }
    }
}

@Composable
fun Rectangle(modifier: Modifier = Modifier, index: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = if (index % 2 == 0) Color.Red else Color.Blue)
    ) {
        Text(index.toString())
    }
}

@Composable
fun Square(modifier: Modifier = Modifier, index: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .background(color = if (index % 2 == 0) Color.Red else Color.Blue)
    ) {
        Text(index.toString())
    }
}

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    onButtonClicked: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .background(color = Color.Black, shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
            .clickable { onButtonClicked() }
    ) {
        Text(
            text = stringResource(text),
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 32.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.TABLET
)
@Composable
fun ListTabletPreview() {
    RectListTheme {
        RectListApp()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ListPhonePreview() {
    RectListTheme {
        RectListApp()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RectPreview() {
    RectListTheme {
        RectList(num = 5, columnNumber = 4)
    }
}