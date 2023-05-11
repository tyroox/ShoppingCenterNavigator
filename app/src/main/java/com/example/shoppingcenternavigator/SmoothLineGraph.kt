package com.example.shoppingcenternavigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SmoothLineGraph() {
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.isabelline))
            .fillMaxSize()
    ) {
        // !!! telefon boyutuna göre resim aynı kalmalı !!!
        Image(painter = painterResource(id = R.drawable.carousel_zemin_kat),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize())
        // animation starting time
        val animationProgress = remember {
            Animatable(0f)
        }
        // animation speed
        LaunchedEffect(key1 = coordinateSystem, block = {
            animationProgress.animateTo(1f, tween(3000))
        })
        val coroutineScope = rememberCoroutineScope()
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clickable {
                    coroutineScope.launch {
                        animationProgress.snapTo(0f)
                        animationProgress.animateTo(1f, tween(3000))
                    }
                }
                .drawWithCache {
                    val path = generatePath(points, coordinateSystem, size)

                    onDrawBehind {
                        // drawing the line
                        drawPath(path, Color.Black, style = Stroke(3.dp.toPx()))
                        /*
                        clipRect(left = size.width * animationProgress.value) {

                        }
                         */


                    }
                })
    }
}

fun generatePath(point: List<Coordinate>, data: List<Coordinate>, size: Size): Path {
    val path = Path()


    val xMax = data.maxBy { it.x }
    val xMin = data.minBy { it.x }
    val yMax = data.maxBy { it.y }
    val yMin = data.minBy { it.y }
    val rangeX = xMax.x - xMin.x
    val rangeY = yMax.y - yMin.y
    val widthPerCoordinate = size.width / rangeX
    val heightPerCoordinate = size.height / rangeY


    point.forEachIndexed { i, coordinate ->
        if (i == 0) {
            path.moveTo(
                (coordinate.x - xMin.x) *
                        widthPerCoordinate,
                (coordinate.y - yMin.y) *
                        heightPerCoordinate
            )
        }

        val coordinateX = (coordinate.x - xMin.x) *
                widthPerCoordinate

        val coordinateY = (coordinate.y - yMin.y) *
                heightPerCoordinate
        path.lineTo(coordinateX, coordinateY)
    }
    return path
}

@RequiresApi(Build.VERSION_CODES.O)
val coordinateSystem = listOf(
    Coordinate(0f,0f),
    Coordinate(1000f,1000f),
    // dönme noktaları
    /*
    Coordinate(169f,365f),
    Coordinate(333f,467f),
    Coordinate(333f,646f),
    Coordinate(632f,467f),
    Coordinate(632f,646f),
    Coordinate(830f,345f),
    Coordinate(798f,744f)
    *
     */
)

@RequiresApi(Build.VERSION_CODES.O)
val points = listOf(
    Coordinate(836f,772f),
    Coordinate(798f,744f),
    Coordinate(632f,646f),
    Coordinate(333f,646f),
    Coordinate(303f,512f)
)
data class Coordinate(val x:Float, val y: Float)

// val PurpleBackgroundColor = Color(0xff322049)
val BarColor = Color.White.copy(alpha = 0.3f)

@Preview
@Composable
fun CoordinateSystem(){
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            val barWidthPx = 1.dp.toPx()

            drawRect(Color.White)
            val verticalLines = size.width / 80.dp.toPx()
            val verticalSize = size.width / (verticalLines + 1)
            repeat(verticalLines.roundToInt()) { i ->
                val startX = verticalSize * (i + 1)
                drawLine(
                    Color.Gray,
                    start = Offset(startX, 0f),
                    end = Offset(startX, size.height),
                    strokeWidth = barWidthPx
                )
            }

            val horizontalLines = size.height / 80.dp.toPx()
            val sectionSize = size.height / (horizontalLines + 1)
            repeat(horizontalLines.roundToInt()) { i ->
                val startY = sectionSize * (i + 1)
                drawLine(
                    BarColor,
                    start = Offset(0f, startY),
                    end = Offset(size.width, startY),
                    strokeWidth = barWidthPx
                )
            }
        })
}
/*
fun generateSmoothPath(data: List<Balance>, size: Size): Path {
    val path = Path()
    val numberEntries = data.size - 1
    val weekWidth = size.width / numberEntries

    val max = data.maxBy { it.y }
    val min = data.minBy { it.y } // will map to x= 0, y = height
    val range = max.y - min.y
    val heightPxPerAmount = size.height / range

    var previousBalanceX = 0f
    var previousBalanceY = size.height
    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance.y - min.y) *
                        heightPxPerAmount
            )
        }

        val balanceX = i * weekWidth
        val balanceY = size.height - (balance.y - min.y) *
                heightPxPerAmount


        // smoothing the curve
        val controlPoint1 = PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
        val controlPoint2 = PointF((balanceX + previousBalanceX) / 2f, balanceY)
        path.cubicTo(
            controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
            balanceX, balanceY
        )
        previousBalanceX = balanceX
        previousBalanceY = balanceY
    }
    return path
}
*/
