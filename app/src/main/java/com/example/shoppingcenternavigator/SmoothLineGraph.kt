package com.example.shoppingcenternavigator

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

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
                    val path = generatePath(shops, points, coordinateSystem, size)

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

fun distance(x1: Float, x2: Float, y1: Float, y2: Float): Float {
    return sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
}

fun closestPoints(x: Float, y: Float, list:List<Point>): MutableList<Float> {
    var closest1 = 1414.2136f
    var closestPoint1 = Coordinate(0f,0f)
    var closest2 = 1414.2136f
    var closestPoint2 = Coordinate(0f,0f)

    list.forEachIndexed{ _, points ->
        if ((distance(x, points.x, y, points.y) < closest1) and (distance(x, points.x, y, points.y) < closest2) and (distance(x, points.x, y, points.y) != 0f)) {
            closest2 = closest1
            closestPoint2 = closestPoint1
            closest1 = distance(x, points.x, y, points.y)
            closestPoint1 = Coordinate(points.x, points.y)
        }
        else if ((distance(x, points.x, y, points.y) > closest1) and (distance(x, points.x, y, points.y) < closest2) and (distance(x, points.x, y, points.y) != 0f)){
            closest2 = (distance(x, points.x, y, points.y))
            closestPoint2 = Coordinate(points.x, points.y)
        }
    }
    val returnList = mutableListOf<Float>()
    returnList.add(closestPoint1.x)
    returnList.add(closestPoint1.y)
    returnList.add(closestPoint2.x)
    returnList.add(closestPoint2.y)
    returnList.add(closest1)
    returnList.add(closest2)

    return returnList
}

fun generatePath(shop: List<Shops>, point: List<Point>, data: List<Coordinate>, size: Size): Path {
    val path = Path()

    val xMax = data.maxBy { it.x }
    val xMin = data.minBy { it.x }
    val yMax = data.maxBy { it.y }
    val yMin = data.minBy { it.y }
    val rangeX = xMax.x - xMin.x
    val rangeY = yMax.y - yMin.y
    val widthPerCoordinate = size.width / rangeX
    val heightPerCoordinate = size.height / rangeY

    /*
    shop.forEachIndexed { i, coordinate ->
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


    }
     */
    path.moveTo(shops[0].x * widthPerCoordinate, shops[0].y * heightPerCoordinate)
    path.lineTo(prime[0].x * widthPerCoordinate, prime[0].y * heightPerCoordinate)

    var distance = 1414.2136f
    var nextPoint = Coordinate(0f,0f)
    var lastPoint = Coordinate(0f,0f)

    distance = distance(prime[1].x, closestPoints(prime[0].x,prime[0].y,points)[0] ,prime[1].y, closestPoints(prime[0].x,prime[0].y,points)[1])
    lastPoint = Coordinate(prime[0].x, prime[0].y)
    nextPoint = Coordinate(closestPoints(prime[0].x,prime[0].y,points)[0], closestPoints(prime[0].x,prime[0].y,points)[1])

    if (distance < distance(prime[1].x, closestPoints(prime[0].x,prime[0].y,points)[2] ,prime[1].y, closestPoints(prime[0].x,prime[0].y,points)[3])){
        distance = distance(prime[1].x, closestPoints(prime[0].x,prime[0].y,points)[2] ,prime[1].y, closestPoints(prime[0].x,prime[0].y,points)[3])
        nextPoint = Coordinate(closestPoints(prime[0].x,prime[0].y,points)[2], closestPoints(prime[0].x,prime[0].y,points)[3])
    }
    path.lineTo(nextPoint.x * widthPerCoordinate,nextPoint.y * heightPerCoordinate)

    while (!((nextPoint.x == prime[1].x) and (nextPoint.y == prime[1].y))){
        var isItLastPoint : Boolean = false

        distance = distance(prime[1].x, closestPoints(nextPoint.x,nextPoint.y,points)[0] ,prime[1].y, closestPoints(nextPoint.x,nextPoint.y,points)[1])
        nextPoint = Coordinate(closestPoints(nextPoint.x,nextPoint.y,points)[0], closestPoints(nextPoint.x,nextPoint.y,points)[1])

        if (distance < distance(prime[1].x, closestPoints(nextPoint.x,nextPoint.y,points)[2] ,prime[1].y, closestPoints(nextPoint.x,nextPoint.y,points)[3])){
            distance = distance(prime[1].x, closestPoints(nextPoint.x,nextPoint.y,points)[2] ,prime[1].y, closestPoints(nextPoint.x,nextPoint.y,points)[3])
            nextPoint = Coordinate(closestPoints(nextPoint.x,nextPoint.y,points)[2], closestPoints(nextPoint.x,nextPoint.y,points)[3])
            isItLastPoint = true
        }

        if (lastPoint != nextPoint){
            Log.d("aa", "if")
            path.lineTo(nextPoint.x * widthPerCoordinate,nextPoint.y * heightPerCoordinate)
            Log.d("aa","${nextPoint.x} ${nextPoint.y}")
            lastPoint = Coordinate(nextPoint.x, nextPoint.y)
        }
        else{
            Log.d("aa","ilk else")
            if (isItLastPoint){
                Log.d("aa","ilk if")
                nextPoint = Coordinate(closestPoints(nextPoint.x,nextPoint.y,points)[0],
                    closestPoints(nextPoint.x,nextPoint.y,points)[1])

                path.lineTo(nextPoint.x * widthPerCoordinate,nextPoint.y * heightPerCoordinate)

                lastPoint = Coordinate(nextPoint.x, nextPoint.y)
            }
            else{
                nextPoint = Coordinate(closestPoints(nextPoint.x,nextPoint.y,points)[2],
                    closestPoints(nextPoint.x,nextPoint.y,points)[3])

                path.lineTo(nextPoint.x * widthPerCoordinate,nextPoint.y * heightPerCoordinate)
                Log.d("aa","${nextPoint.x} ${nextPoint.y}")

                lastPoint = Coordinate(nextPoint.x, nextPoint.y)
            }
        }
        break
    }
    path.lineTo(prime[1].x * widthPerCoordinate, prime[1].y * heightPerCoordinate)
    path.lineTo(shops[1].x * widthPerCoordinate, shops[1].y * heightPerCoordinate)

    return path
}

val coordinateSystem = listOf(
    Coordinate(0f,0f),
    Coordinate(1000f,1000f),
)

val points = listOf(
    Point(0,169f,365f),
    Point(0,333f,467f),
    Point(0,333f,646f),
    Point(0,632f,467f),
    Point(0,632f,646f),
    Point(0,830f,345f),
    Point(0,798f,744f),
    // primes
    Point(0,333f,512f),
    Point(0,632f,605f),
    Point(0,394f,467f)
)

val shops = listOf(
    //Shops("Starbucks", 0, 836f,772f),
    Shops("Mavi", 0,303f,512f),
    Shops("Kiğılı",0,661f,605f),
    Shops("Polo",0,394f,450f)

)

val prime = listOf(
    //Shops("Starbucks", 0, 836f,772f),
    Shops("MaviPrime", 0,333f,512f),
    Shops("KiğılıPrime",0,632f,605f),
    Shops("PoloPrime",0,394f,467f)

)


data class Coordinate(val x:Float, val y: Float)
data class Point(val Floor: Int, val x:Float, val y: Float)
data class Shops(val Name:String, val Floor: Int, val x:Float, val y: Float)

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
