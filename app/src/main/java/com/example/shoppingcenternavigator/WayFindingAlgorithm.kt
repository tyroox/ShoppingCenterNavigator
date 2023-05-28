package com.example.shoppingcenternavigator

import android.graphics.PathMeasure
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.shoppingcenternavigator.ui.theme.caribbeanCurrent
import com.example.shoppingcenternavigator.ui.theme.isabelline
import com.example.shoppingcenternavigator.ui.theme.moonstone
import java.lang.Math.PI
import java.lang.Math.atan2
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.*


@RequiresApi(Build.VERSION_CODES.N)
fun dijkstraAlgorithm(start: Point, destination: Point): List<Point> {
    val distances = HashMap<Point, Float>()
    val previous = HashMap<Point, Point>()
    val queue = PriorityQueue<Point>(compareBy { distances.getOrDefault(it, Float.MAX_VALUE) })

    // Initialize distances with infinity except for the start point (0 distance)
    carouselPoints.forEach { point ->
        distances[point] = if (point == start) 0f else Float.MAX_VALUE
    }

    queue.add(start)

    while (queue.isNotEmpty()) {
        val current = queue.poll()

        if (current == destination) {
            break
        }

        current.connections.forEach { (neighbor, distance) ->
            val altDistance = distances[current]!! + distance

            if (altDistance < distances[neighbor]!!) {
                distances[neighbor] = altDistance
                previous[neighbor] = current
                queue.remove(neighbor)
                queue.add(neighbor)
            }
        }
    }

    return buildPath(start, destination, previous)
}

fun buildPath(start: Point, destination: Point, previous: HashMap<Point, Point>): List<Point> {
    val path = mutableListOf<Point>()
    var current: Point? = destination

    while (current != null) {
        path.add(current)
        current = previous[current]
    }

    return path.reversed()
}

fun generateSize(data: List<Coordinate>, size: Size): MutableList<Float> {
    val xMax = data.maxBy { it.x }
    val xMin = data.minBy { it.x }
    val yMax = data.maxBy { it.y }
    val yMin = data.minBy { it.y }
    val rangeX = xMax.x - xMin.x
    val rangeY = yMax.y - yMin.y
    val widthPerCoordinate = size.width / rangeX
    val heightPerCoordinate = size.height / rangeY

    val returnList = mutableListOf<Float>()
    returnList.add(widthPerCoordinate)
    returnList.add(heightPerCoordinate)

    return returnList
}

@Composable
@RequiresApi(Build.VERSION_CODES.N)
fun WayFindingAlgorithm() {
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.isabelline))
            .fillMaxSize()
    ) {

        // basement
        carouselPoints[31].addConnection(carouselPoints[0], calculateDistance(carouselPoints[31], carouselPoints[0]))
        carouselPoints[10].addConnection(carouselPoints[0], calculateDistance(carouselPoints[10], carouselPoints[0]))
        carouselPoints[0].addConnection(carouselPoints[28], calculateDistance(carouselPoints[0], carouselPoints[28]))
        carouselPoints[28].addConnection(carouselPoints[20], calculateDistance(carouselPoints[28], carouselPoints[20]))
        carouselPoints[20].addConnection(carouselPoints[17], calculateDistance(carouselPoints[20], carouselPoints[17]))
        carouselPoints[17].addConnection(carouselPoints[29], calculateDistance(carouselPoints[17], carouselPoints[29]))
        carouselPoints[29].addConnection(carouselPoints[1], calculateDistance(carouselPoints[29], carouselPoints[1]))
        carouselPoints[1].addConnection(carouselPoints[25], calculateDistance(carouselPoints[1], carouselPoints[25]))
        carouselPoints[25].addConnection(carouselPoints[26], calculateDistance(carouselPoints[25], carouselPoints[26]))
        carouselPoints[26].addConnection(carouselPoints[36], calculateDistance(carouselPoints[26], carouselPoints[36]))
        carouselPoints[36].addConnection(carouselPoints[9], calculateDistance(carouselPoints[36], carouselPoints[9]))
        carouselPoints[9].addConnection(carouselPoints[2], calculateDistance(carouselPoints[9], carouselPoints[2]))
        carouselPoints[2].addConnection(carouselPoints[7], calculateDistance(carouselPoints[2], carouselPoints[7]))
        carouselPoints[7].addConnection(carouselPoints[24], calculateDistance(carouselPoints[7], carouselPoints[24]))
        carouselPoints[24].addConnection(carouselPoints[18], calculateDistance(carouselPoints[24], carouselPoints[18]))
        carouselPoints[18].addConnection(carouselPoints[4], calculateDistance(carouselPoints[18], carouselPoints[4]))
        carouselPoints[1].addConnection(carouselPoints[27], calculateDistance(carouselPoints[1], carouselPoints[27]))
        carouselPoints[27].addConnection(carouselPoints[15], calculateDistance(carouselPoints[27], carouselPoints[15]))
        carouselPoints[15].addConnection(carouselPoints[3], calculateDistance(carouselPoints[15], carouselPoints[3]))
        carouselPoints[3].addConnection(carouselPoints[21], calculateDistance(carouselPoints[3], carouselPoints[21]))
        carouselPoints[21].addConnection(carouselPoints[33], calculateDistance(carouselPoints[21], carouselPoints[33]))
        carouselPoints[33].addConnection(carouselPoints[19], calculateDistance(carouselPoints[33], carouselPoints[19]))
        carouselPoints[19].addConnection(carouselPoints[35], calculateDistance(carouselPoints[19], carouselPoints[35]))
        carouselPoints[35].addConnection(carouselPoints[8], calculateDistance(carouselPoints[35], carouselPoints[8]))
        carouselPoints[8].addConnection(carouselPoints[23], calculateDistance(carouselPoints[8], carouselPoints[23]))
        carouselPoints[3].addConnection(carouselPoints[34], calculateDistance(carouselPoints[3], carouselPoints[34]))
        carouselPoints[34].addConnection(carouselPoints[14], calculateDistance(carouselPoints[34], carouselPoints[14]))
        carouselPoints[14].addConnection(carouselPoints[22], calculateDistance(carouselPoints[14], carouselPoints[22]))
        carouselPoints[22].addConnection(carouselPoints[4], calculateDistance(carouselPoints[22], carouselPoints[4]))
        carouselPoints[4].addConnection(carouselPoints[11], calculateDistance(carouselPoints[4], carouselPoints[11]))
        carouselPoints[11].addConnection(carouselPoints[12], calculateDistance(carouselPoints[11], carouselPoints[12]))
        carouselPoints[12].addConnection(carouselPoints[37], calculateDistance(carouselPoints[12], carouselPoints[37]))
        carouselPoints[37].addConnection(carouselPoints[32], calculateDistance(carouselPoints[37], carouselPoints[32]))
        carouselPoints[32].addConnection(carouselPoints[13], calculateDistance(carouselPoints[26], carouselPoints[13]))
        carouselPoints[13].addConnection(carouselPoints[6], calculateDistance(carouselPoints[13], carouselPoints[6]))
        carouselPoints[6].addConnection(carouselPoints[30], calculateDistance(carouselPoints[6], carouselPoints[30]))
        carouselPoints[30].addConnection(carouselPoints[16], calculateDistance(carouselPoints[30], carouselPoints[16]))
        // basement stairs
        carouselPoints[38].addConnection(carouselPoints[26], calculateDistance(carouselPoints[38], carouselPoints[26]))
        carouselPoints[39].addConnection(carouselPoints[24], calculateDistance(carouselPoints[39], carouselPoints[24]))
        carouselPoints[40].addConnection(carouselPoints[27], calculateDistance(carouselPoints[40], carouselPoints[27]))
        carouselPoints[40].addConnection(carouselPoints[15], calculateDistance(carouselPoints[40], carouselPoints[15]))
        carouselPoints[41].addConnection(carouselPoints[14], calculateDistance(carouselPoints[41], carouselPoints[14]))


        // kat 1
        carouselPoints[59].addConnection(carouselPoints[56], calculateDistance(carouselPoints[59], carouselPoints[56]))
        carouselPoints[56].addConnection(carouselPoints[46], calculateDistance(carouselPoints[56], carouselPoints[46]))
        carouselPoints[46].addConnection(carouselPoints[42], calculateDistance(carouselPoints[46], carouselPoints[42]))
        carouselPoints[42].addConnection(carouselPoints[57], calculateDistance(carouselPoints[42], carouselPoints[57]))
        carouselPoints[57].addConnection(carouselPoints[48], calculateDistance(carouselPoints[57], carouselPoints[48]))
        carouselPoints[48].addConnection(carouselPoints[55], calculateDistance(carouselPoints[48], carouselPoints[55]))
        carouselPoints[55].addConnection(carouselPoints[60], calculateDistance(carouselPoints[55], carouselPoints[60]))
        carouselPoints[60].addConnection(carouselPoints[43], calculateDistance(carouselPoints[60], carouselPoints[43]))
        carouselPoints[58].addConnection(carouselPoints[43], calculateDistance(carouselPoints[58], carouselPoints[43]))
        carouselPoints[43].addConnection(carouselPoints[47], calculateDistance(carouselPoints[43], carouselPoints[47]))
        carouselPoints[47].addConnection(carouselPoints[61], calculateDistance(carouselPoints[47], carouselPoints[61]))
        carouselPoints[61].addConnection(carouselPoints[54], calculateDistance(carouselPoints[61], carouselPoints[54]))
        carouselPoints[54].addConnection(carouselPoints[44], calculateDistance(carouselPoints[54], carouselPoints[44]))
        carouselPoints[50].addConnection(carouselPoints[53], calculateDistance(carouselPoints[50], carouselPoints[53]))
        carouselPoints[53].addConnection(carouselPoints[44], calculateDistance(carouselPoints[53], carouselPoints[44]))
        carouselPoints[44].addConnection(carouselPoints[49], calculateDistance(carouselPoints[44], carouselPoints[49]))
        carouselPoints[49].addConnection(carouselPoints[45], calculateDistance(carouselPoints[49], carouselPoints[45]))
        carouselPoints[42].addConnection(carouselPoints[51], calculateDistance(carouselPoints[42], carouselPoints[51]))
        carouselPoints[51].addConnection(carouselPoints[52], calculateDistance(carouselPoints[51], carouselPoints[52]))
        carouselPoints[52].addConnection(carouselPoints[45], calculateDistance(carouselPoints[52], carouselPoints[45]))
        // kat 1 merdiven
        carouselPoints[48].addConnection(carouselPoints[62], calculateDistance(carouselPoints[48], carouselPoints[62]))
        carouselPoints[61].addConnection(carouselPoints[63], calculateDistance(carouselPoints[61], carouselPoints[63]))
        carouselPoints[44].addConnection(carouselPoints[64], calculateDistance(carouselPoints[44], carouselPoints[64]))
        carouselPoints[49].addConnection(carouselPoints[64], calculateDistance(carouselPoints[49], carouselPoints[64]))
        carouselPoints[52].addConnection(carouselPoints[65], calculateDistance(carouselPoints[52], carouselPoints[65]))
        carouselPoints[51].addConnection(carouselPoints[65], calculateDistance(carouselPoints[51], carouselPoints[65]))

        //Basement Minus One Floor
        carouselPoints[78].addConnection(carouselPoints[77], calculateDistance(carouselPoints[78], carouselPoints[77]))
        carouselPoints[77].addConnection(carouselPoints[92], calculateDistance(carouselPoints[77], carouselPoints[92]))
        carouselPoints[92].addConnection(carouselPoints[83], calculateDistance(carouselPoints[92], carouselPoints[83]))
        carouselPoints[83].addConnection(carouselPoints[89], calculateDistance(carouselPoints[83], carouselPoints[89]))
        carouselPoints[89].addConnection(carouselPoints[87], calculateDistance(carouselPoints[89], carouselPoints[87]))
        carouselPoints[87].addConnection(carouselPoints[82], calculateDistance(carouselPoints[87], carouselPoints[82]))
        carouselPoints[82].addConnection(carouselPoints[68], calculateDistance(carouselPoints[82], carouselPoints[68]))
        carouselPoints[68].addConnection(carouselPoints[73], calculateDistance(carouselPoints[68], carouselPoints[73]))
        carouselPoints[73].addConnection(carouselPoints[81], calculateDistance(carouselPoints[73], carouselPoints[81]))
        carouselPoints[81].addConnection(carouselPoints[90], calculateDistance(carouselPoints[81], carouselPoints[90]))
        carouselPoints[90].addConnection(carouselPoints[74], calculateDistance(carouselPoints[90], carouselPoints[74]))
        carouselPoints[74].addConnection(carouselPoints[69], calculateDistance(carouselPoints[74], carouselPoints[69]))
        carouselPoints[69].addConnection(carouselPoints[91], calculateDistance(carouselPoints[69], carouselPoints[91]))
        carouselPoints[91].addConnection(carouselPoints[86], calculateDistance(carouselPoints[91], carouselPoints[86]))
        carouselPoints[86].addConnection(carouselPoints[85], calculateDistance(carouselPoints[86], carouselPoints[85]))
        carouselPoints[85].addConnection(carouselPoints[66], calculateDistance(carouselPoints[85], carouselPoints[66]))
        carouselPoints[75].addConnection(carouselPoints[66], calculateDistance(carouselPoints[75], carouselPoints[66]))
        carouselPoints[66].addConnection(carouselPoints[71], calculateDistance(carouselPoints[66], carouselPoints[71]))
        carouselPoints[71].addConnection(carouselPoints[88], calculateDistance(carouselPoints[71], carouselPoints[88]))
        carouselPoints[88].addConnection(carouselPoints[80], calculateDistance(carouselPoints[88], carouselPoints[80]))
        carouselPoints[80].addConnection(carouselPoints[79], calculateDistance(carouselPoints[80], carouselPoints[79]))
        carouselPoints[79].addConnection(carouselPoints[76], calculateDistance(carouselPoints[79], carouselPoints[76]))
        carouselPoints[76].addConnection(carouselPoints[67], calculateDistance(carouselPoints[76], carouselPoints[67]))
        carouselPoints[84].addConnection(carouselPoints[67], calculateDistance(carouselPoints[84], carouselPoints[67]))
        carouselPoints[67].addConnection(carouselPoints[72], calculateDistance(carouselPoints[67], carouselPoints[72]))
        carouselPoints[70].addConnection(carouselPoints[68], calculateDistance(carouselPoints[70], carouselPoints[68]))
        carouselPoints[70].addConnection(carouselPoints[96], calculateDistance(carouselPoints[70], carouselPoints[96]))
        carouselPoints[72].addConnection(carouselPoints[96], calculateDistance(carouselPoints[70], carouselPoints[68]))
        // -1 merdiven
        carouselPoints[93].addConnection(carouselPoints[96], calculateDistance(carouselPoints[93], carouselPoints[96]))
        carouselPoints[94].addConnection(carouselPoints[86], calculateDistance(carouselPoints[94], carouselPoints[86]))
        carouselPoints[95].addConnection(carouselPoints[88], calculateDistance(carouselPoints[95], carouselPoints[88]))
        carouselPoints[95].addConnection(carouselPoints[66], calculateDistance(carouselPoints[95], carouselPoints[66]))
        carouselPoints[95].addConnection(carouselPoints[85], calculateDistance(carouselPoints[95], carouselPoints[85]))

        //second floor
        carouselPoints[97].addConnection(carouselPoints[116], calculateDistance(carouselPoints[97], carouselPoints[116]))
        carouselPoints[116].addConnection(carouselPoints[105], calculateDistance(carouselPoints[116], carouselPoints[105]))
        carouselPoints[105].addConnection(carouselPoints[104], calculateDistance(carouselPoints[105], carouselPoints[104]))
        carouselPoints[104].addConnection(carouselPoints[99], calculateDistance(carouselPoints[104], carouselPoints[99]))
        carouselPoints[99].addConnection(carouselPoints[115], calculateDistance(carouselPoints[99], carouselPoints[115]))
        carouselPoints[115].addConnection(carouselPoints[100], calculateDistance(carouselPoints[115], carouselPoints[100]))
        carouselPoints[97].addConnection(carouselPoints[106], calculateDistance(carouselPoints[97], carouselPoints[106]))
        carouselPoints[106].addConnection(carouselPoints[101], calculateDistance(carouselPoints[106], carouselPoints[101]))
        carouselPoints[101].addConnection(carouselPoints[103], calculateDistance(carouselPoints[101], carouselPoints[103]))
        carouselPoints[101].addConnection(carouselPoints[111], calculateDistance(carouselPoints[101], carouselPoints[111]))
        carouselPoints[111].addConnection(carouselPoints[107], calculateDistance(carouselPoints[111], carouselPoints[107]))
        carouselPoints[107].addConnection(carouselPoints[98], calculateDistance(carouselPoints[107], carouselPoints[98]))
        carouselPoints[98].addConnection(carouselPoints[102], calculateDistance(carouselPoints[98], carouselPoints[102]))
        carouselPoints[102].addConnection(carouselPoints[117], calculateDistance(carouselPoints[102], carouselPoints[117]))
        carouselPoints[102].addConnection(carouselPoints[113], calculateDistance(carouselPoints[102], carouselPoints[113]))
        carouselPoints[102].addConnection(carouselPoints[108], calculateDistance(carouselPoints[102], carouselPoints[108]))
        carouselPoints[108].addConnection(carouselPoints[114], calculateDistance(carouselPoints[108], carouselPoints[114]))
        carouselPoints[98].addConnection(carouselPoints[112], calculateDistance(carouselPoints[98], carouselPoints[112]))
        carouselPoints[112].addConnection(carouselPoints[109], calculateDistance(carouselPoints[112], carouselPoints[109]))
        carouselPoints[109].addConnection(carouselPoints[110], calculateDistance(carouselPoints[109], carouselPoints[110]))
        carouselPoints[110].addConnection(carouselPoints[100], calculateDistance(carouselPoints[110], carouselPoints[100]))
        // kat 2 merdiven
        carouselPoints[118].addConnection(carouselPoints[105], calculateDistance(carouselPoints[118], carouselPoints[105]))
        carouselPoints[119].addConnection(carouselPoints[109], calculateDistance(carouselPoints[119], carouselPoints[109]))

        // -2 tokmak koneksiyonu
        carouselPoints[124].addConnection(carouselPoints[121], calculateDistance(carouselPoints[124], carouselPoints[121]))
        carouselPoints[121].addConnection(carouselPoints[123], calculateDistance(carouselPoints[121], carouselPoints[123]))
        carouselPoints[123].addConnection(carouselPoints[120], calculateDistance(carouselPoints[123], carouselPoints[120]))
        carouselPoints[120].addConnection(carouselPoints[122], calculateDistance(carouselPoints[120], carouselPoints[122]))
        carouselPoints[122].addConnection(carouselPoints[125], calculateDistance(carouselPoints[122], carouselPoints[125]))
        // -2 tokmak merdivenleri
        carouselPoints[125].addConnection(carouselPoints[121], calculateDistance(carouselPoints[125], carouselPoints[121]))
        carouselPoints[125].addConnection(carouselPoints[123], calculateDistance(carouselPoints[125], carouselPoints[123]))

        val path = Path()
        val fromIndex = SelectedShops.selectedOptionFromIndex
        val toIndex = SelectedShops.selectedOptionToIndex
        val fromFloor = carouselShops[fromIndex].Floor
        val toFloor = carouselShops[toIndex].Floor
        val context = LocalContext.current
        val selectedItem = remember { mutableStateOf(fromFloor) }
        var path1Visibility by remember { mutableStateOf(true) }
        var path2Visibility by remember { mutableStateOf(false) }
        var path3Visibility by remember { mutableStateOf(true) }
        var path4Visibility by remember { mutableStateOf(false) }

        Log.d("kat bilgisi", "$fromFloor")
        Log.d("kat bilgisi 2", "$toFloor")

        val pointList: MutableList<Coordinate> = mutableListOf()
        for (point in carouselPoints) {
            pointList.add(Coordinate(point.x, point.y))
        }

        val primeFromIndex = pointList.indexOf(Coordinate(carouselPrime[fromIndex].x, carouselPrime[fromIndex].y))
        val primeToIndex = pointList.indexOf(Coordinate(carouselPrime[toIndex].x, carouselPrime[toIndex].y))

        val start = carouselPoints[primeFromIndex]
        val destination = carouselPoints[primeToIndex]

        Log.d("st","$primeFromIndex")
        Log.d("cs","$primeToIndex")

        val shortestPath = dijkstraAlgorithm(start, destination)

        when (fromFloor) {
            -2 -> {
                Image(painter = painterResource(id = R.drawable.carousel_b2),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }
            -1 -> {
                Image(painter = painterResource(id = R.drawable.carousel_b1),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }
            0 -> {
                Image(painter = painterResource(id = R.drawable.carousel_0),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }
            1 -> {
                Image(painter = painterResource(id = R.drawable.carousel_1),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }
            2 -> {
                Image(painter = painterResource(id = R.drawable.carousel_2),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }

        }

        if (fromFloor == toFloor){
            val animatedPathProgress = remember { Animatable(0f) }

            LaunchedEffect(Unit) {
                animatedPathProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(tween(durationMillis = 5000), RepeatMode.Restart)
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .drawWithCache {
                        val iconSize = 15.dp.toPx()
                        val startX = carouselShops[fromIndex].x * generateSize(coordinateSystem, size)[0]
                        val startY = carouselShops[fromIndex].y * generateSize(coordinateSystem, size)[1]
                        val endX = carouselShops[toIndex].x * generateSize(coordinateSystem, size)[0]
                        val endY = carouselShops[toIndex].y * generateSize(coordinateSystem, size)[1]

                        onDrawBehind {
                            val currentPathProgress = animatedPathProgress.value
                            path.reset()

                            path.moveTo(startX, startY)

                            val pathLength = shortestPath.size
                            val pathProgress = currentPathProgress * pathLength
                            Log.d("aa","${carouselShops.size}")

                            val initialPathProgress = 1f / pathLength
                            if (initialPathProgress <= pathProgress) {
                                path.lineTo(
                                    carouselPrime[fromIndex].x * generateSize(coordinateSystem, size)[0],
                                    carouselPrime[fromIndex].y * generateSize(coordinateSystem, size)[1]
                                )
                            }

                            shortestPath.forEachIndexed { i, point ->
                                val pointProgress = i / pathLength.toFloat()

                                if (pointProgress <= pathProgress) {
                                    path.lineTo(
                                        point.x * generateSize(coordinateSystem, size)[0],
                                        point.y * generateSize(coordinateSystem, size)[1]
                                    )
                                }
                            }

                            if (pathProgress >= 1f) {
                                path.lineTo(endX, endY)
                            }

                            drawPath(path, caribbeanCurrent, style = Stroke(4.dp.toPx()))


                            // drawing the line


                            drawCircle(
                                moonstone,
                                radius = iconSize / 4,
                                center = Offset(startX, startY)
                            )
                            drawCircle(
                                Color.Red,
                                radius = iconSize / 4,
                                center = Offset(endX, endY)
                            )
                            /*
                            drawIntoCanvas { canvas ->
                                val icon = ContextCompat.getDrawable(context, R.drawable.marker)
                                icon?.setBounds(
                                    endX.toInt(),
                                    endY.toInt() - (iconSize).toInt()/3,
                                    endX.toInt() + (iconSize).toInt(),
                                    endY.toInt() + (iconSize).toInt()/3
                                )
                                icon?.draw(canvas.nativeCanvas)
                            }
                             */
                        }
                    }
            )
        }
        else{
            when (selectedItem.value) {
                -2 -> {
                    Image(painter = painterResource(id = R.drawable.carousel_b2),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize())
                }
                -1 -> {
                    Image(painter = painterResource(id = R.drawable.carousel_b1),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize())
                }
                0 -> {
                    Image(painter = painterResource(id = R.drawable.carousel_0),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize())
                }
                1 -> {
                    Image(painter = painterResource(id = R.drawable.carousel_1),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize())
                }
                2 -> {
                    Image(painter = painterResource(id = R.drawable.carousel_2),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize())
                }
            }
            if(fromFloor > toFloor){
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .drawWithCache {

                            onDrawBehind {
                                val iconSize = 15.dp.toPx()
                                val startX =
                                    carouselShops[fromIndex].x * generateSize(coordinateSystem, size)[0]
                                val startY =
                                    carouselShops[fromIndex].y * generateSize(coordinateSystem, size)[1]
                                val startPoint = carouselPoints[primeFromIndex]
                                var endStair = Point(0, 0f, 0f)

                                val endX =
                                    carouselShops[toIndex].x * generateSize(coordinateSystem, size)[0]
                                val endY =
                                    carouselShops[toIndex].y * generateSize(coordinateSystem, size)[1]
                                var startStair = Point(0, 0f, 0f)
                                val endPoint = carouselPoints[primeToIndex]

                                when (fromFloor) {
                                    2 -> {
                                        endStair = carouselPoints[119]
                                    }

                                    1 -> {
                                        endStair = carouselPoints[65]
                                    }

                                    0 -> {
                                        endStair = carouselPoints[41]
                                    }

                                    -1 -> {
                                        endStair = carouselPoints[95]
                                    }
                                }

                                val shortestWayToStairs = dijkstraAlgorithm(startPoint, endStair)

                                if (path1Visibility) {
                                    path.moveTo(startX, startY)
                                    path.lineTo(
                                        carouselPrime[fromIndex].x * generateSize(
                                            coordinateSystem,
                                            size
                                        )[0],
                                        carouselPrime[fromIndex].y * generateSize(coordinateSystem, size)[1]
                                    )

                                    shortestWayToStairs.forEach { point ->
                                        path.lineTo(
                                            point.x * generateSize(
                                                coordinateSystem,
                                                size
                                            )[0], point.y * generateSize(coordinateSystem, size)[1]
                                        )
                                    }
                                    // drawing the line

                                    drawPath(path, caribbeanCurrent, style = Stroke(4.dp.toPx()))

                                    drawCircle(
                                        moonstone,
                                        radius = iconSize / 4,
                                        center = Offset(startX, startY)
                                    )
                                    drawCircle(
                                        Color.Red, radius = iconSize / 4, center = Offset(
                                            endStair.x * generateSize(coordinateSystem, size)[0],
                                            endStair.y * generateSize(coordinateSystem, size)[1]
                                        )
                                    )
                                }

                                when (toFloor) {
                                    1 -> {
                                        startStair = carouselPoints[65]
                                    }

                                    0 -> {
                                        startStair = carouselPoints[39]
                                    }

                                    -1 -> {
                                        startStair = carouselPoints[93]
                                    }

                                    -2 -> {
                                        startStair = carouselPoints[126]
                                    }
                                }

                                val shortestWayFromStairs = dijkstraAlgorithm(startStair, endPoint)

                                if (path2Visibility) {
                                    path.moveTo(
                                        startStair.x * generateSize(coordinateSystem, size)[0],
                                        startStair.y * generateSize(coordinateSystem, size)[1]
                                    )

                                    shortestWayFromStairs.forEach { point ->
                                        path.lineTo(
                                            point.x * generateSize(
                                                coordinateSystem,
                                                size
                                            )[0], point.y * generateSize(coordinateSystem, size)[1]
                                        )
                                    }
                                    path.lineTo(endX, endY)


                                    // drawing the line
                                    drawPath(path, caribbeanCurrent, style = Stroke(4.dp.toPx()))

                                    drawCircle(
                                        moonstone,
                                        radius = iconSize / 4,
                                        center = Offset(
                                            startStair.x * generateSize(coordinateSystem, size)[0],
                                            startStair.y * generateSize(coordinateSystem, size)[1]
                                        )
                                    )
                                    drawCircle(
                                        Color.Red, radius = iconSize / 4, center = Offset(
                                            endX, endY
                                        )
                                    )
                                }
                            }
                        }
                )
            }
            else if (fromFloor < toFloor) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .drawWithCache {

                            onDrawBehind {
                                val iconSize = 15.dp.toPx()
                                val startX =
                                    carouselShops[fromIndex].x * generateSize(coordinateSystem, size)[0]
                                val startY =
                                    carouselShops[fromIndex].y * generateSize(coordinateSystem, size)[1]
                                val startPoint = carouselPoints[primeFromIndex]
                                var endStair = Point(0, 0f, 0f)

                                val endX =
                                    carouselShops[toIndex].x * generateSize(coordinateSystem, size)[0]
                                val endY =
                                    carouselShops[toIndex].y * generateSize(coordinateSystem, size)[1]
                                var startStair = Point(0, 0f, 0f)
                                val endPoint = carouselPoints[primeToIndex]

                                when (fromFloor) {
                                    1 -> {
                                        endStair = carouselPoints[63]
                                    }

                                    0 -> {
                                        endStair = carouselPoints[40]
                                    }

                                    -1 -> {
                                        endStair = carouselPoints[94]
                                    }

                                    -2 -> {
                                        endStair = carouselPoints[126]
                                    }
                                }

                                val shortestWayToStairs = dijkstraAlgorithm(startPoint, endStair)

                                if (path1Visibility) {
                                    path.moveTo(startX, startY)
                                    path.lineTo(
                                        carouselPrime[fromIndex].x * generateSize(
                                            coordinateSystem,
                                            size
                                        )[0],
                                        carouselPrime[fromIndex].y * generateSize(coordinateSystem, size)[1]
                                    )

                                    shortestWayToStairs.forEach { point ->
                                        path.lineTo(
                                            point.x * generateSize(
                                                coordinateSystem,
                                                size
                                            )[0], point.y * generateSize(coordinateSystem, size)[1]
                                        )
                                    }
                                    // drawing the line

                                    drawPath(path, caribbeanCurrent, style = Stroke(4.dp.toPx()))

                                    drawCircle(
                                        moonstone,
                                        radius = iconSize / 4,
                                        center = Offset(startX, startY)
                                    )
                                    drawCircle(
                                        Color.Red, radius = iconSize / 4, center = Offset(
                                            endStair.x * generateSize(coordinateSystem, size)[0],
                                            endStair.y * generateSize(coordinateSystem, size)[1]
                                        )
                                    )
                                }

                                when (toFloor) {
                                    2 -> {
                                        startStair = carouselPoints[118]
                                    }

                                    1 -> {
                                        startStair = carouselPoints[64]
                                    }

                                    0 -> {
                                        startStair = carouselPoints[38]
                                    }

                                    -1 -> {
                                        startStair = carouselPoints[95]
                                    }
                                }

                                val shortestWayFromStairs = dijkstraAlgorithm(startStair, endPoint)

                                if (path2Visibility) {
                                    path.moveTo(
                                        startStair.x * generateSize(coordinateSystem, size)[0],
                                        startStair.y * generateSize(coordinateSystem, size)[1]
                                    )

                                    shortestWayFromStairs.forEach { point ->
                                        path.lineTo(
                                            point.x * generateSize(
                                                coordinateSystem,
                                                size
                                            )[0], point.y * generateSize(coordinateSystem, size)[1]
                                        )
                                    }
                                    path.lineTo(endX, endY)


                                    // drawing the line
                                    drawPath(path, caribbeanCurrent, style = Stroke(4.dp.toPx()))

                                    drawCircle(
                                        moonstone,
                                        radius = iconSize / 4,
                                        center = Offset(
                                            startStair.x * generateSize(coordinateSystem, size)[0],
                                            startStair.y * generateSize(coordinateSystem, size)[1]
                                        )
                                    )
                                    drawCircle(
                                        Color.Red, radius = iconSize / 4, center = Offset(
                                            endX, endY
                                        )
                                    )
                                }


                            }
                        }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { selectedItem.value = fromFloor
                    path1Visibility = true
                    path2Visibility = false
                    path3Visibility = true
                    path4Visibility = false
                    }
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Arrow Left",
                        modifier = Modifier.size(50.dp),
                        tint = caribbeanCurrent
                        // You can customize the appearance of the icon here
                    )
                }

                IconButton(
                    onClick = {
                        selectedItem.value = toFloor
                        path1Visibility = false
                        path2Visibility = true
                        path3Visibility = false
                        path4Visibility = true
                    }
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = "Arrow Right",
                        modifier = Modifier.size(50.dp),
                        tint = caribbeanCurrent
                        // You can customize the appearance of the icon here
                    )
                }
            }
        }

    }

}