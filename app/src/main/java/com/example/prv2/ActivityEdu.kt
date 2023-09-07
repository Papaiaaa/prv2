package com.example.prv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prv2.ui.theme.PrvTheme
import kotlin.math.roundToInt


class ActivityEdu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PrvTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Заголовок
                    Text(
                        text = "Заголовок",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Текст меньшего размера
                    Text(
                        text = "Текст меньшего размера",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Слайдер
                    SliderWithSquares()
                }
            }
        }
    }
}

@Composable
fun SliderWithSquares() {
    val squareItems = listOf("ТУСУР", "ТГУ", "Нетология")

    // Количество квадратов, которое должно помещаться на экране (2.0)
    val itemsPerScreen = 2.0f
    val squareWidth = (screenWidth / itemsPerScreen).dp

    // Ширина экрана
    val screenWidth = LocalDensity.current.run {
        (LocalConfiguration.current.screenWidthDp * density).roundToInt().dp
    }

    // Вычисляем ширину квадратов на экране


    // Текущий индекс видимой группы квадратов
    var visibleGroup by remember { mutableStateOf(0) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary) // Цвет фона слайдера
            .horizontalScroll(state = rememberScrollState()),
        contentPadding = PaddingValues(16.dp),
    ) {
        itemsIndexed(squareItems.chunked(3)) { groupIndex, group ->
            if (groupIndex == visibleGroup) {
                group.forEach { squareName ->
                    Square(squareName, squareWidth)
                }
            }
        }
    }
}

@Composable
fun Square(name: String, width: Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .height(100.dp)
            .background(MaterialTheme.colorScheme.secondary) // Цвет квадрата
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
