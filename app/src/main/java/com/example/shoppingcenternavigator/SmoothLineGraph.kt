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
import androidx.compose.ui.graphics.drawscope.clipRect
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
                        clipRect(right = size.width * animationProgress.value) {

                        }

                         */



                    }
                })
    }
}

fun distance(x1: Float, x2: Float, y1: Float, y2: Float): Float {
    return sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
}

fun closestPoints(x: Float, y: Float, list:MutableList<Coordinate>): MutableList<Float> {
    var closest1 = 1414.2136f
    var closestPoint1 = Coordinate(0f,0f)
    var closest2 = 1414.2136f
    var closestPoint2 = Coordinate(0f,0f)


    list.forEachIndexed{ i, points ->
        if ((distance(x, points.x, y, points.y) < closest1) and (distance(x, points.x, y, points.y) < closest2)) {
            closest2 = closest1
            closestPoint2 = closestPoint1
            closest1 = distance(x, points.x, y, points.y)
            closestPoint1 = Coordinate(points.x, points.y)

        }
        else if ((distance(x, points.x, y, points.y) > closest1) and (distance(x, points.x, y, points.y) < closest2)){
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

    var distance = 1414.2136f
    var nextPoint = Coordinate(0f,0f)
    var lastPoint = Coordinate(0f,0f)
    var index = 0

    var pointList: MutableList<Coordinate> = mutableListOf<Coordinate>()
    for (point in points) {
        pointList.add(Coordinate(point.x, point.y))
    }

    path.moveTo(shops[0].x * widthPerCoordinate, shops[0].y * heightPerCoordinate)
    path.lineTo(prime[0].x * widthPerCoordinate, prime[0].y * heightPerCoordinate)
    index = pointList.indexOf(Coordinate(prime[0].x, prime[0].y))
    pointList.removeAt(index)

    distance = distance(shops[1].x, closestPoints(prime[0].x,prime[0].y,pointList)[0] ,shops[1].y, closestPoints(prime[0].x,prime[0].y,pointList)[1])
    nextPoint = Coordinate(closestPoints(prime[0].x,prime[0].y,pointList)[0], closestPoints(prime[0].x,prime[0].y,pointList)[1])

    if (distance > (distance(shops[1].x, closestPoints(prime[0].x,prime[0].y,pointList)[2] ,shops[1].y, closestPoints(prime[0].x,prime[0].y,pointList)[3]))){
        distance = distance(shops[1].x, closestPoints(prime[0].x,prime[0].y,pointList)[2] ,shops[1].y, closestPoints(prime[0].x,prime[0].y,pointList)[3])
        nextPoint = Coordinate(closestPoints(prime[0].x,prime[0].y,pointList)[2], closestPoints(prime[0].x,prime[0].y,pointList)[3])
    }

    path.lineTo(nextPoint.x * widthPerCoordinate,nextPoint.y * heightPerCoordinate)
    index = pointList.indexOf(Coordinate(nextPoint.x, nextPoint.y))
    pointList.removeAt(index)

    while (!((nextPoint.x == prime[1].x) and (nextPoint.y == prime[1].y))){
        lastPoint = nextPoint

        distance = distance(shops[1].x, closestPoints(nextPoint.x,nextPoint.y,pointList)[0] ,shops[1].y, closestPoints(nextPoint.x,nextPoint.y,pointList)[1])
        nextPoint = Coordinate(closestPoints(nextPoint.x,nextPoint.y,pointList)[0], closestPoints(nextPoint.x,nextPoint.y,pointList)[1])
        Log.d("bulduğu nokta 1","${nextPoint}]")

        if (distance > (distance(shops[1].x, closestPoints(lastPoint.x,lastPoint.y,pointList)[2] ,shops[1].y, closestPoints(lastPoint.x,lastPoint.y,pointList)[3]))){
            distance = distance(shops[1].x, closestPoints(lastPoint.x,lastPoint.y,pointList)[2] ,shops[1].y, closestPoints(lastPoint.x,lastPoint.y,pointList)[3])
            nextPoint = Coordinate(closestPoints(lastPoint.x,lastPoint.y,pointList)[2], closestPoints(lastPoint.x,lastPoint.y,pointList)[3])
            Log.d("bulduğu nokta 2","${nextPoint}]")
        }
        path.lineTo(nextPoint.x * widthPerCoordinate, nextPoint.y * heightPerCoordinate)
        index = pointList.indexOf(Coordinate(nextPoint.x, nextPoint.y))
        Log.d("gittiği nokta","${pointList[index]}]")
        pointList.removeAt(index)
    }
    path.lineTo(shops[1].x * widthPerCoordinate, shops[1].y * heightPerCoordinate)

    return path
}

val coordinateSystem = listOf(
    Coordinate(0f,0f),
    Coordinate(1000f,1000f),
)

val points = listOf(
    // corners
    Point(0,169f,365f),
    Point(0,333f,467f),
    Point(0,333f,646f),
    Point(0,632f,467f),
    Point(0,632f,646f),
    Point(0,830f,345f),
    Point(0,798f,744f),
    Point(0,450f,467f),
    Point(0,504f,467f),
    // primes
    Point(0,333f,512f),
    Point(0,632f,605f),
    Point(0,394f,467f),
    Point(0,333f,558f),
    Point(0,333f,605f),
    Point(0,333f,646f),
    Point(0,403f,646f),
    Point(0,482f,646f),
    Point(0,559f,646f),
    Point(0,671f,673f),
    Point(0,561f,467f),
    Point(0,632f,557f),
    Point(0,632f,512f),
    Point(0,730f,710f),
    Point(0,836f,744f),
    Point(0,679f,439f),
    Point(0,778f,740f),
    Point(0,285f,443f),
    Point(0,333f,467f),
    Point(0,237f,411f),
    Point(0,207f,396f),
    Point(0,735f,405f),
    Point(0,757f,393f),
    Point(0,845f,336f),
    Point(0,807f,361f),
    Point(0,185f,278f),
    Point(0,165f,361f),
    Point(0,684f,681f),
    Point(0,754f,724f),
    Point(0,855f,744f)
)

val shops = listOf(
    Shops("Polo",0,394f,450f),
    Shops("Love My Body", 0,482f,665f),

    Shops("Starbucks", 0, 836f,772f),

    Shops("The Cookshop", 0,202f,278f),

    Shops("Watson's", 0,303f,605f),
    Shops("Jument", 0,237f,388f),

    Shops("Atasun Optik", 0,303f,645f),

    Shops("W Collection Men", 0,661f,512f),
    Shops("Sevil", 0,315f,435f),

    Shops("Hummels", 0,559f,665f),
    Shops("Derimod", 0,696f,667f),

    Shops("adL", 0,403f,665f),
    Shops("Cafe Vienna", 0,154f,353f),

    Shops("Watson's", 0,303f,605f),
    Shops("Leman Kültür", 0,845f,306f),
    Shops("Jument", 0,237f,388f),
    Shops("Starbucks", 0, 836f,772f),
    Shops("Love My Body", 0,482f,665f),

    Shops("W Collection Men", 0,661f,512f),
    Shops("W Collection Women", 0,774f,400f),
    Shops("Mavi", 0,303f,512f),
    Shops("Kiğılı",0,661f,605f),
    Shops("Polo",0,394f,450f),
    Shops("Pierre Cardin", 0,303f,558f),

    Shops("Colin's", 0,654f,684f),
    Shops("Efor", 0,561f,450f),
    Shops("Ecrou", 0,661f,557f),
    Shops("Yves Rocher", 0,714f,721f),
    Shops("Vakko", 0,698f,448f),
    Shops("Desa", 0,762f,749f),
    Shops("Kemal Tanca", 0,658f,429f),
    Shops("Hotiç", 0,265f,449f),


    Shops("Saat&Saat", 0,187f,401f),
    Shops("Jepublic", 0,735f,383f),

    Shops("Alaçatı Muhallebicisi", 0,846f,381f),
    Shops("The Cookshop", 0,202f,278f),

    Shops("Turkcell", 0,766f,710f),
    Shops("Hayal Kahvesi", 0,855f,716f)

)

val prime = listOf(
    Shops("Polo Prime",0,394f,467f),
    Shops("Love My Body Prime", 0,482f,646f),

    Shops("Starbucks Prime", 0, 836f,744f),

    Shops("The Cookshop Prime", 0,185f,278f),
    Shops("Watson's Prime", 0,333f,605f),
    Shops("Jument Prime", 0,237f,411f),

    Shops("Atasun Optik Prime", 0,333f,646f),

    Shops("W Collection Men Prime", 0,632f,512f),
    Shops("Sevil Prime", 0,333f,467f),

    Shops("Derimod Prime", 0,684f,681f),
    Shops("Hummels Prime", 0,559f,646f),
    Shops("adL Prime", 0,403f,646f),
    Shops("Cafe Vienna Prime", 0,165f,361f),
    Shops("Watson's Prime", 0,333f,605f),
    Shops("Leman Kültür Prime", 0,845f,336f),
    Shops("Jument Prime", 0,237f,411f),
    Shops("Starbucks Prime", 0, 836f,744f),
    Shops("Love My Body Prime", 0,482f,646f),

    Shops("W Collection Women Prime", 0,757f,393f),
    Shops("Mavi Prime", 0,333f,512f),
    Shops("Kiğılı Prime",0,632f,605f),

    Shops("Pierre Cardin Prime", 0,333f,558f),


    Shops("Colin's Prime", 0,671f,673f),
    Shops("Efor Prime", 0,561f,467f),
    Shops("Ecrou Prime", 0,632f,557f),
    Shops("Yves Rocher Prime", 0,730f,710f),
    Shops("Vakko Prime", 0,679f,439f),
    Shops("Desa Prime", 0,778f,740f),
    Shops("Kemal Tanca Prime", 0,658f,451f),
    Shops("Hotiç Prime", 0,285f,443f),

    Shops("Saat&Saat Prime", 0,207f,396f),
    Shops("Jepublic Prime", 0,735f,405f),

    Shops("Alaçatı Muhallebicisi Prime", 0,807f,361f),
    Shops("The Cookshop Prime", 0,185f,278f),


    Shops("Turkcell Prime", 0,754f,724f),
    Shops("Hayal Kahvesi Prime", 0,855f,744f)
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
