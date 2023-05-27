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
    points.forEach { point ->
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
        points[31].addConnection(points[0], calculateDistance(points[31], points[0]))
        points[10].addConnection(points[0], calculateDistance(points[10], points[0]))
        points[0].addConnection(points[28], calculateDistance(points[0], points[28]))
        points[28].addConnection(points[20], calculateDistance(points[28], points[20]))
        points[20].addConnection(points[17], calculateDistance(points[20], points[17]))
        points[17].addConnection(points[29], calculateDistance(points[17], points[29]))
        points[29].addConnection(points[1], calculateDistance(points[29], points[1]))
        points[1].addConnection(points[25], calculateDistance(points[1], points[25]))
        points[25].addConnection(points[26], calculateDistance(points[25], points[26]))
        points[26].addConnection(points[36], calculateDistance(points[26], points[36]))
        points[36].addConnection(points[9], calculateDistance(points[36], points[9]))
        points[9].addConnection(points[2], calculateDistance(points[9], points[2]))
        points[2].addConnection(points[7], calculateDistance(points[2], points[7]))
        points[7].addConnection(points[24], calculateDistance(points[7], points[24]))
        points[24].addConnection(points[18], calculateDistance(points[24], points[18]))
        points[18].addConnection(points[4], calculateDistance(points[18], points[4]))
        points[1].addConnection(points[27], calculateDistance(points[1], points[27]))
        points[27].addConnection(points[15], calculateDistance(points[27], points[15]))
        points[15].addConnection(points[3], calculateDistance(points[15], points[3]))
        points[3].addConnection(points[21], calculateDistance(points[3], points[21]))
        points[21].addConnection(points[33], calculateDistance(points[21], points[33]))
        points[33].addConnection(points[19], calculateDistance(points[33], points[19]))
        points[19].addConnection(points[35], calculateDistance(points[19], points[35]))
        points[35].addConnection(points[8], calculateDistance(points[35], points[8]))
        points[8].addConnection(points[23], calculateDistance(points[8], points[23]))
        points[3].addConnection(points[34], calculateDistance(points[3], points[34]))
        points[34].addConnection(points[14], calculateDistance(points[34], points[14]))
        points[14].addConnection(points[22], calculateDistance(points[14], points[22]))
        points[22].addConnection(points[4], calculateDistance(points[22], points[4]))
        points[4].addConnection(points[11], calculateDistance(points[4], points[11]))
        points[11].addConnection(points[12], calculateDistance(points[11], points[12]))
        points[12].addConnection(points[37], calculateDistance(points[12], points[37]))
        points[37].addConnection(points[32], calculateDistance(points[37], points[32]))
        points[32].addConnection(points[13], calculateDistance(points[26], points[13]))
        points[13].addConnection(points[6], calculateDistance(points[13], points[6]))
        points[6].addConnection(points[30], calculateDistance(points[6], points[30]))
        points[30].addConnection(points[16], calculateDistance(points[30], points[16]))
        // basement stairs
        points[38].addConnection(points[26], calculateDistance(points[38], points[26]))
        points[39].addConnection(points[24], calculateDistance(points[39], points[24]))
        points[40].addConnection(points[27], calculateDistance(points[40], points[27]))
        points[40].addConnection(points[15], calculateDistance(points[40], points[15]))
        points[41].addConnection(points[14], calculateDistance(points[41], points[14]))


        // kat 1
        points[59].addConnection(points[56], calculateDistance(points[59], points[56]))
        points[56].addConnection(points[46], calculateDistance(points[56], points[46]))
        points[46].addConnection(points[42], calculateDistance(points[46], points[42]))
        points[42].addConnection(points[57], calculateDistance(points[42], points[57]))
        points[57].addConnection(points[48], calculateDistance(points[57], points[48]))
        points[48].addConnection(points[55], calculateDistance(points[48], points[55]))
        points[55].addConnection(points[60], calculateDistance(points[55], points[60]))
        points[60].addConnection(points[43], calculateDistance(points[60], points[43]))
        points[58].addConnection(points[43], calculateDistance(points[58], points[43]))
        points[43].addConnection(points[47], calculateDistance(points[43], points[47]))
        points[47].addConnection(points[61], calculateDistance(points[47], points[61]))
        points[61].addConnection(points[54], calculateDistance(points[61], points[54]))
        points[54].addConnection(points[44], calculateDistance(points[54], points[44]))
        points[50].addConnection(points[53], calculateDistance(points[50], points[53]))
        points[53].addConnection(points[44], calculateDistance(points[53], points[44]))
        points[44].addConnection(points[49], calculateDistance(points[44], points[49]))
        points[49].addConnection(points[45], calculateDistance(points[49], points[45]))
        points[42].addConnection(points[51], calculateDistance(points[42], points[51]))
        points[51].addConnection(points[52], calculateDistance(points[51], points[52]))
        points[52].addConnection(points[45], calculateDistance(points[52], points[45]))
        // kat 1 merdiven
        points[48].addConnection(points[62], calculateDistance(points[48], points[62]))
        points[61].addConnection(points[63], calculateDistance(points[61], points[63]))
        points[44].addConnection(points[64], calculateDistance(points[44], points[64]))
        points[49].addConnection(points[64], calculateDistance(points[49], points[64]))
        points[52].addConnection(points[65], calculateDistance(points[52], points[65]))
        points[51].addConnection(points[65], calculateDistance(points[51], points[65]))

        //Basement Minus One Floor
        points[78].addConnection(points[77], calculateDistance(points[78], points[77]))
        points[77].addConnection(points[92], calculateDistance(points[77], points[92]))
        points[92].addConnection(points[83], calculateDistance(points[92], points[83]))
        points[83].addConnection(points[89], calculateDistance(points[83], points[89]))
        points[89].addConnection(points[87], calculateDistance(points[89], points[87]))
        points[87].addConnection(points[82], calculateDistance(points[87], points[82]))
        points[82].addConnection(points[68], calculateDistance(points[82], points[68]))
        points[68].addConnection(points[73], calculateDistance(points[68], points[73]))
        points[73].addConnection(points[81], calculateDistance(points[73], points[81]))
        points[81].addConnection(points[90], calculateDistance(points[81], points[90]))
        points[90].addConnection(points[74], calculateDistance(points[90], points[74]))
        points[74].addConnection(points[69], calculateDistance(points[74], points[69]))
        points[69].addConnection(points[91], calculateDistance(points[69], points[91]))
        points[91].addConnection(points[86], calculateDistance(points[91], points[86]))
        points[86].addConnection(points[85], calculateDistance(points[86], points[85]))
        points[85].addConnection(points[66], calculateDistance(points[85], points[66]))
        points[75].addConnection(points[66], calculateDistance(points[75], points[66]))
        points[66].addConnection(points[71], calculateDistance(points[66], points[71]))
        points[71].addConnection(points[88], calculateDistance(points[71], points[88]))
        points[88].addConnection(points[80], calculateDistance(points[88], points[80]))
        points[80].addConnection(points[79], calculateDistance(points[80], points[79]))
        points[79].addConnection(points[76], calculateDistance(points[79], points[76]))
        points[76].addConnection(points[67], calculateDistance(points[76], points[67]))
        points[84].addConnection(points[67], calculateDistance(points[84], points[67]))
        points[67].addConnection(points[72], calculateDistance(points[67], points[72]))
        points[72].addConnection(points[70], calculateDistance(points[72], points[70]))
        points[70].addConnection(points[68], calculateDistance(points[70], points[68]))
        // -1 merdiven
        points[93].addConnection(points[96], calculateDistance(points[93], points[96]))
        points[94].addConnection(points[86], calculateDistance(points[94], points[86]))
        points[95].addConnection(points[88], calculateDistance(points[95], points[88]))
        points[95].addConnection(points[66], calculateDistance(points[95], points[66]))
        points[95].addConnection(points[85], calculateDistance(points[95], points[85]))

        //second floor
        points[97].addConnection(points[116], calculateDistance(points[97], points[116]))
        points[116].addConnection(points[105], calculateDistance(points[116], points[105]))
        points[105].addConnection(points[104], calculateDistance(points[105], points[104]))
        points[104].addConnection(points[99], calculateDistance(points[104], points[99]))
        points[99].addConnection(points[115], calculateDistance(points[99], points[115]))
        points[115].addConnection(points[100], calculateDistance(points[115], points[100]))
        points[97].addConnection(points[106], calculateDistance(points[97], points[106]))
        points[106].addConnection(points[101], calculateDistance(points[106], points[101]))
        points[101].addConnection(points[103], calculateDistance(points[101], points[103]))
        points[101].addConnection(points[111], calculateDistance(points[101], points[111]))
        points[111].addConnection(points[107], calculateDistance(points[111], points[107]))
        points[107].addConnection(points[98], calculateDistance(points[107], points[98]))
        points[98].addConnection(points[102], calculateDistance(points[98], points[102]))
        points[102].addConnection(points[117], calculateDistance(points[102], points[117]))
        points[102].addConnection(points[113], calculateDistance(points[102], points[113]))
        points[102].addConnection(points[108], calculateDistance(points[102], points[108]))
        points[108].addConnection(points[114], calculateDistance(points[108], points[114]))
        points[98].addConnection(points[112], calculateDistance(points[98], points[112]))
        points[112].addConnection(points[109], calculateDistance(points[112], points[109]))
        points[109].addConnection(points[110], calculateDistance(points[109], points[110]))
        points[110].addConnection(points[100], calculateDistance(points[110], points[100]))
        // kat 2 merdiven
        points[118].addConnection(points[105], calculateDistance(points[118], points[105]))
        points[119].addConnection(points[109], calculateDistance(points[119], points[109]))

        // -2 tokmak koneksiyonu
        points[124].addConnection(points[121], calculateDistance(points[124], points[121]))
        points[121].addConnection(points[123], calculateDistance(points[121], points[123]))
        points[123].addConnection(points[120], calculateDistance(points[123], points[120]))
        points[120].addConnection(points[122], calculateDistance(points[120], points[122]))
        points[122].addConnection(points[125], calculateDistance(points[122], points[125]))
        // -2 tokmak merdivenleri
        points[125].addConnection(points[121], calculateDistance(points[125], points[121]))
        points[125].addConnection(points[123], calculateDistance(points[125], points[123]))

        val path = Path()
        val fromIndex = SelectedShops.selectedOptionFromIndex
        val toIndex = SelectedShops.selectedOptionToIndex
        val fromFloor = shops[fromIndex].Floor
        val toFloor = shops[toIndex].Floor
        val context = LocalContext.current
        val selectedItem = remember { mutableStateOf(fromFloor) }
        var path1Visibility by remember { mutableStateOf(true) }
        var path2Visibility by remember { mutableStateOf(false) }
        var path3Visibility by remember { mutableStateOf(true) }
        var path4Visibility by remember { mutableStateOf(false) }

        Log.d("kat bilgisi", "$fromFloor")
        Log.d("kat bilgisi 2", "$toFloor")

        val pointList: MutableList<Coordinate> = mutableListOf()
        for (point in points) {
            pointList.add(Coordinate(point.x, point.y))
        }

        val primeFromIndex = pointList.indexOf(Coordinate(prime[fromIndex].x, prime[fromIndex].y))
        val primeToIndex = pointList.indexOf(Coordinate(prime[toIndex].x, prime[toIndex].y))

        val start = points[primeFromIndex]
        val destination = points[primeToIndex]

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
            var animationSpeed = 2000
            LaunchedEffect(Unit) {
                animatedPathProgress.animateTo(1f, animationSpec = tween(durationMillis = animationSpeed))
            }
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .drawWithCache {
                        val iconSize = 15.dp.toPx()
                        val startX = shops[fromIndex].x * generateSize(coordinateSystem, size)[0]
                        val startY = shops[fromIndex].y * generateSize(coordinateSystem, size)[1]
                        val endX = shops[toIndex].x * generateSize(coordinateSystem, size)[0]
                        val endY = shops[toIndex].y * generateSize(coordinateSystem, size)[1]

                        onDrawBehind {
                            val currentPathProgress = animatedPathProgress.value

                            path.moveTo(startX, startY)
                            path.lineTo(
                                prime[fromIndex].x * generateSize(coordinateSystem, size)[0],
                                prime[fromIndex].y * generateSize(coordinateSystem, size)[1]
                            )

                            shortestPath.forEachIndexed { i, point ->
                                val pathProgress = (i + 1) / shortestPath.size.toFloat() // Calculate path progress for each point
                                if (pathProgress <= currentPathProgress) {
                                    path.lineTo(
                                        point.x * generateSize(coordinateSystem, size)[0],
                                        point.y * generateSize(coordinateSystem, size)[1]
                                    )
                                }
                            }

                            if (currentPathProgress >= 1f) {
                                path.lineTo(endX, endY)
                            }

                            drawPath(path, caribbeanCurrent, style = Stroke(3.dp.toPx()))


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
                                    shops[fromIndex].x * generateSize(coordinateSystem, size)[0]
                                val startY =
                                    shops[fromIndex].y * generateSize(coordinateSystem, size)[1]
                                val startPoint = points[primeFromIndex]
                                var endStair = Point(0, 0f, 0f)

                                val endX =
                                    shops[toIndex].x * generateSize(coordinateSystem, size)[0]
                                val endY =
                                    shops[toIndex].y * generateSize(coordinateSystem, size)[1]
                                var startStair = Point(0, 0f, 0f)
                                val endPoint = points[primeToIndex]

                                when (fromFloor) {
                                    2 -> {
                                        endStair = points[118]
                                    }

                                    1 -> {
                                        endStair = points[64]
                                    }

                                    0 -> {
                                        endStair = points[38]
                                    }

                                    -1 -> {
                                        endStair = points[95]
                                    }
                                }

                                val shortestWayToStairs = dijkstraAlgorithm(startPoint, endStair)

                                if (path1Visibility) {
                                    path.moveTo(startX, startY)
                                    path.lineTo(
                                        prime[fromIndex].x * generateSize(
                                            coordinateSystem,
                                            size
                                        )[0],
                                        prime[fromIndex].y * generateSize(coordinateSystem, size)[1]
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

                                    drawPath(path, caribbeanCurrent, style = Stroke(3.dp.toPx()))

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
                                        startStair = points[63]
                                    }

                                    0 -> {
                                        startStair = points[40]
                                    }

                                    -1 -> {
                                        startStair = points[94]
                                    }

                                    -2 -> {
                                        startStair = points[126]
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
                                    drawPath(path, caribbeanCurrent, style = Stroke(3.dp.toPx()))

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
                                    shops[fromIndex].x * generateSize(coordinateSystem, size)[0]
                                val startY =
                                    shops[fromIndex].y * generateSize(coordinateSystem, size)[1]
                                val startPoint = points[primeFromIndex]
                                var endStair = Point(0, 0f, 0f)

                                val endX =
                                    shops[toIndex].x * generateSize(coordinateSystem, size)[0]
                                val endY =
                                    shops[toIndex].y * generateSize(coordinateSystem, size)[1]
                                var startStair = Point(0, 0f, 0f)
                                val endPoint = points[primeToIndex]

                                when (fromFloor) {
                                    1 -> {
                                        endStair = points[65]
                                    }

                                    0 -> {
                                        endStair = points[39]
                                    }

                                    -1 -> {
                                        endStair = points[93]
                                    }

                                    -2 -> {
                                        endStair = points[126]
                                    }
                                }

                                val shortestWayToStairs = dijkstraAlgorithm(startPoint, endStair)

                                if (path1Visibility) {
                                    path.moveTo(startX, startY)
                                    path.lineTo(
                                        prime[fromIndex].x * generateSize(
                                            coordinateSystem,
                                            size
                                        )[0],
                                        prime[fromIndex].y * generateSize(coordinateSystem, size)[1]
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

                                    drawPath(path, caribbeanCurrent, style = Stroke(3.dp.toPx()))

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
                                        startStair = points[119]
                                    }

                                    1 -> {
                                        startStair = points[62]
                                    }

                                    0 -> {
                                        startStair = points[41]
                                    }

                                    -1 -> {
                                        startStair = points[95]
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
                                    drawPath(path, caribbeanCurrent, style = Stroke(3.dp.toPx()))

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
                OutlinedButton(onClick = { selectedItem.value = fromFloor
                    path1Visibility = true
                    path2Visibility = false
                    path3Visibility = true
                    path4Visibility = false
                },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                    Icons.Default.KeyboardArrowLeft
                }
                OutlinedButton(onClick = { selectedItem.value = toFloor
                    path1Visibility = false
                    path2Visibility = true
                    path3Visibility = false
                    path4Visibility = true},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                    Icons.Default.KeyboardArrowRight
                }
            }
        }

    }

}