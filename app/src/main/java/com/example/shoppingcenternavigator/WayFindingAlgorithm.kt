package com.example.shoppingcenternavigator

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
        Image(painter = painterResource(id = R.drawable.carousel_zemin_kat),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize())

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


        val path = Path()
        val fromIndex = SelectedShops.selectedOptionFromIndex
        val toIndex = SelectedShops.selectedOptionToIndex

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

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .drawWithCache {

                    onDrawBehind {
                        path.moveTo(shops[fromIndex].x * generateSize(coordinateSystem, size)[0],
                            shops[fromIndex].y * generateSize(coordinateSystem, size)[1])
                        path.lineTo(prime[fromIndex].x * generateSize(coordinateSystem, size)[0],
                            prime[fromIndex].y * generateSize(coordinateSystem, size)[1])

                        shortestPath.forEach {point ->
                            path.lineTo(point.x * generateSize(coordinateSystem, size)[0],point.y * generateSize(coordinateSystem, size)[1])
                        }
                        path.lineTo(
                            shops[toIndex].x * generateSize(coordinateSystem, size)[0],
                            shops[toIndex].y * generateSize(coordinateSystem, size)[1])

                        // drawing the line
                        drawPath(path, Color.Black, style = Stroke(3.dp.toPx()))

                    }
                })
    }
}