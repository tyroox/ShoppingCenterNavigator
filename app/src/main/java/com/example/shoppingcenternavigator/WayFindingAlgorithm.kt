package com.example.shoppingcenternavigator

import android.os.Build
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

        points[0].addConnection(points[1], calculateDistance(points[0], points[1]))
        points[1].addConnection(points[2], calculateDistance(points[1], points[2]))
        points[1].addConnection(points[3], calculateDistance(points[1], points[3]))
        points[2].addConnection(points[4], calculateDistance(points[2], points[4]))
        points[3].addConnection(points[4], calculateDistance(points[3], points[4]))
        points[3].addConnection(points[5], calculateDistance(points[3], points[5]))
        points[4].addConnection(points[6], calculateDistance(points[4], points[6]))

        val start = points[1]
        val destination = points[6]
        val path = Path()
        val fromIndex = SelectedShops.selectedOptionFromIndex
        val toIndex = SelectedShops.selectedOptionToIndex

        val shortestPath = dijkstraAlgorithm(start, destination)

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .drawWithCache {

                    onDrawBehind {

                        shortestPath.forEachIndexed { i, point ->
                            if (i == 0){
                                path.moveTo(point.x * generateSize(coordinateSystem, size)[0],point.y * generateSize(coordinateSystem, size)[1])
                            }
                            path.lineTo(point.x * generateSize(coordinateSystem, size)[0],point.y * generateSize(coordinateSystem, size)[1])
                        }
                        // drawing the line
                        drawPath(path, Color.Black, style = Stroke(3.dp.toPx()))

                    }
                })
    }
}