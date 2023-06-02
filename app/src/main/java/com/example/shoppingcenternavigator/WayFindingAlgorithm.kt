package com.example.shoppingcenternavigator

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppingcenternavigator.ui.theme.caribbeanCurrent
import com.example.shoppingcenternavigator.ui.theme.moonstone
import kotlinx.coroutines.delay
import java.util.*


@RequiresApi(Build.VERSION_CODES.N)
fun dijkstraAlgorithm(start: Point, destination: Point): List<Point> {
    val distances = HashMap<Point, Float>()
    val previous = HashMap<Point, Point>()
    val queue = PriorityQueue<Point>(compareBy { distances.getOrDefault(it, Float.MAX_VALUE) })

    if (SelectedShops.selectedMall == 0){
        // Initialize distances with infinity except for the start point (0 distance)
        carouselPoints.forEach { point ->
            distances[point] = if (point == start) 0f else Float.MAX_VALUE
        }
    }
    else if (SelectedShops.selectedMall == 1){
        // Initialize distances with infinity except for the start point (0 distance)
        capacityPoints.forEach { point ->
            distances[point] = if (point == start) 0f else Float.MAX_VALUE
        }
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

        // Capacity First Floor
        capacityPoints[18].addConnection(capacityPoints[68], calculateDistance(capacityPoints[18], capacityPoints[68]))
        capacityPoints[68].addConnection(capacityPoints[19], calculateDistance(capacityPoints[68], capacityPoints[19]))
        capacityPoints[19].addConnection(capacityPoints[14], calculateDistance(capacityPoints[19], capacityPoints[14]))
        capacityPoints[14].addConnection(capacityPoints[20], calculateDistance(capacityPoints[14], capacityPoints[20]))
        capacityPoints[20].addConnection(capacityPoints[21], calculateDistance(capacityPoints[20], capacityPoints[21]))
        capacityPoints[21].addConnection(capacityPoints[48], calculateDistance(capacityPoints[21], capacityPoints[48]))
        capacityPoints[48].addConnection(capacityPoints[22], calculateDistance(capacityPoints[48], capacityPoints[22]))
        capacityPoints[22].addConnection(capacityPoints[49], calculateDistance(capacityPoints[22], capacityPoints[49]))
        capacityPoints[49].addConnection(capacityPoints[15], calculateDistance(capacityPoints[49], capacityPoints[15]))
        capacityPoints[15].addConnection(capacityPoints[23], calculateDistance(capacityPoints[15], capacityPoints[23]))
        capacityPoints[23].addConnection(capacityPoints[24], calculateDistance(capacityPoints[23], capacityPoints[24]))
        capacityPoints[24].addConnection(capacityPoints[16], calculateDistance(capacityPoints[24], capacityPoints[16]))
        capacityPoints[16].addConnection(capacityPoints[25], calculateDistance(capacityPoints[16], capacityPoints[25]))
        capacityPoints[25].addConnection(capacityPoints[17], calculateDistance(capacityPoints[25], capacityPoints[17]))
        capacityPoints[17].addConnection(capacityPoints[66], calculateDistance(capacityPoints[17], capacityPoints[66]))
        capacityPoints[66].addConnection(capacityPoints[50], calculateDistance(capacityPoints[66], capacityPoints[50]))
        capacityPoints[50].addConnection(capacityPoints[15], calculateDistance(capacityPoints[50], capacityPoints[15]))
        capacityPoints[66].addConnection(capacityPoints[26], calculateDistance(capacityPoints[66], capacityPoints[26]))
        capacityPoints[26].addConnection(capacityPoints[51], calculateDistance(capacityPoints[26], capacityPoints[51]))
        capacityPoints[51].addConnection(capacityPoints[27], calculateDistance(capacityPoints[51], capacityPoints[27]))
        capacityPoints[27].addConnection(capacityPoints[52], calculateDistance(capacityPoints[27], capacityPoints[52]))
        capacityPoints[52].addConnection(capacityPoints[28], calculateDistance(capacityPoints[52], capacityPoints[28]))
        capacityPoints[28].addConnection(capacityPoints[65], calculateDistance(capacityPoints[28], capacityPoints[65]))
        capacityPoints[65].addConnection(capacityPoints[1], calculateDistance(capacityPoints[65], capacityPoints[1]))
        capacityPoints[1].addConnection(capacityPoints[29], calculateDistance(capacityPoints[1], capacityPoints[29]))
        capacityPoints[29].addConnection(capacityPoints[0], calculateDistance(capacityPoints[29], capacityPoints[0]))
        capacityPoints[0].addConnection(capacityPoints[30], calculateDistance(capacityPoints[0], capacityPoints[30]))
        capacityPoints[30].addConnection(capacityPoints[64], calculateDistance(capacityPoints[30], capacityPoints[64]))
        capacityPoints[64].addConnection(capacityPoints[31], calculateDistance(capacityPoints[64], capacityPoints[31]))
        capacityPoints[31].addConnection(capacityPoints[32], calculateDistance(capacityPoints[31], capacityPoints[32]))
        capacityPoints[32].addConnection(capacityPoints[3], calculateDistance(capacityPoints[32], capacityPoints[3]))
        capacityPoints[3].addConnection(capacityPoints[58], calculateDistance(capacityPoints[3], capacityPoints[58]))
        capacityPoints[58].addConnection(capacityPoints[2], calculateDistance(capacityPoints[58], capacityPoints[2]))
        capacityPoints[2].addConnection(capacityPoints[65], calculateDistance(capacityPoints[2], capacityPoints[65]))
        capacityPoints[3].addConnection(capacityPoints[33], calculateDistance(capacityPoints[3], capacityPoints[33]))
        capacityPoints[33].addConnection(capacityPoints[34], calculateDistance(capacityPoints[33], capacityPoints[34]))
        capacityPoints[34].addConnection(capacityPoints[5], calculateDistance(capacityPoints[34], capacityPoints[5]))
        capacityPoints[5].addConnection(capacityPoints[59], calculateDistance(capacityPoints[5], capacityPoints[59]))
        capacityPoints[59].addConnection(capacityPoints[60], calculateDistance(capacityPoints[59], capacityPoints[60]))
        capacityPoints[60].addConnection(capacityPoints[4], calculateDistance(capacityPoints[60], capacityPoints[4]))
        capacityPoints[4].addConnection(capacityPoints[54], calculateDistance(capacityPoints[4], capacityPoints[54]))
        capacityPoints[54].addConnection(capacityPoints[53], calculateDistance(capacityPoints[54], capacityPoints[53]))
        capacityPoints[53].addConnection(capacityPoints[2], calculateDistance(capacityPoints[53], capacityPoints[2]))
        capacityPoints[5].addConnection(capacityPoints[35], calculateDistance(capacityPoints[5], capacityPoints[35]))
        capacityPoints[35].addConnection(capacityPoints[36], calculateDistance(capacityPoints[35], capacityPoints[36]))
        capacityPoints[36].addConnection(capacityPoints[37], calculateDistance(capacityPoints[36], capacityPoints[37]))
        capacityPoints[37].addConnection(capacityPoints[8], calculateDistance(capacityPoints[37], capacityPoints[8]))
        capacityPoints[8].addConnection(capacityPoints[38], calculateDistance(capacityPoints[8], capacityPoints[38]))
        capacityPoints[38].addConnection(capacityPoints[39], calculateDistance(capacityPoints[38], capacityPoints[39]))
        capacityPoints[39].addConnection(capacityPoints[9], calculateDistance(capacityPoints[39], capacityPoints[9]))
        capacityPoints[9].addConnection(capacityPoints[40], calculateDistance(capacityPoints[9], capacityPoints[40]))
        capacityPoints[40].addConnection(capacityPoints[41], calculateDistance(capacityPoints[40], capacityPoints[41]))
        capacityPoints[41].addConnection(capacityPoints[42], calculateDistance(capacityPoints[41], capacityPoints[42]))
        capacityPoints[42].addConnection(capacityPoints[10], calculateDistance(capacityPoints[42], capacityPoints[10]))
        capacityPoints[10].addConnection(capacityPoints[61], calculateDistance(capacityPoints[10], capacityPoints[61]))
        capacityPoints[61].addConnection(capacityPoints[62], calculateDistance(capacityPoints[61], capacityPoints[62]))
        capacityPoints[62].addConnection(capacityPoints[11], calculateDistance(capacityPoints[62], capacityPoints[11]))
        capacityPoints[11].addConnection(capacityPoints[56], calculateDistance(capacityPoints[11], capacityPoints[56]))
        capacityPoints[56].addConnection(capacityPoints[7], calculateDistance(capacityPoints[56], capacityPoints[7]))
        capacityPoints[7].addConnection(capacityPoints[55], calculateDistance(capacityPoints[7], capacityPoints[55]))
        capacityPoints[55].addConnection(capacityPoints[6], calculateDistance(capacityPoints[55], capacityPoints[6]))
        capacityPoints[6].addConnection(capacityPoints[4], calculateDistance(capacityPoints[6], capacityPoints[4]))
        capacityPoints[10].addConnection(capacityPoints[43], calculateDistance(capacityPoints[10], capacityPoints[43]))
        capacityPoints[43].addConnection(capacityPoints[44], calculateDistance(capacityPoints[43], capacityPoints[44]))
        capacityPoints[44].addConnection(capacityPoints[12], calculateDistance(capacityPoints[44], capacityPoints[12]))
        capacityPoints[12].addConnection(capacityPoints[63], calculateDistance(capacityPoints[12], capacityPoints[63]))
        capacityPoints[63].addConnection(capacityPoints[67], calculateDistance(capacityPoints[63], capacityPoints[67]))
        capacityPoints[67].addConnection(capacityPoints[13], calculateDistance(capacityPoints[67], capacityPoints[13]))
        capacityPoints[13].addConnection(capacityPoints[47], calculateDistance(capacityPoints[13], capacityPoints[47]))
        capacityPoints[47].addConnection(capacityPoints[57], calculateDistance(capacityPoints[47], capacityPoints[57]))
        capacityPoints[57].addConnection(capacityPoints[11], calculateDistance(capacityPoints[57], capacityPoints[11]))
        capacityPoints[12].addConnection(capacityPoints[45], calculateDistance(capacityPoints[12], capacityPoints[45]))
        capacityPoints[45].addConnection(capacityPoints[46], calculateDistance(capacityPoints[45], capacityPoints[46]))
        capacityPoints[46].addConnection(capacityPoints[18], calculateDistance(capacityPoints[46], capacityPoints[18]))
        capacityPoints[13].addConnection(capacityPoints[14], calculateDistance(capacityPoints[13], capacityPoints[14]))

        // Second Floor
        capacityPoints[69].addConnection(capacityPoints[89], calculateDistance(capacityPoints[69], capacityPoints[89]))
        capacityPoints[89].addConnection(capacityPoints[116], calculateDistance(capacityPoints[89], capacityPoints[116]))
        capacityPoints[116].addConnection(capacityPoints[88], calculateDistance(capacityPoints[116], capacityPoints[88]))
        capacityPoints[88].addConnection(capacityPoints[92], calculateDistance(capacityPoints[88], capacityPoints[92]))
        capacityPoints[92].addConnection(capacityPoints[93], calculateDistance(capacityPoints[92], capacityPoints[93]))
        capacityPoints[92].addConnection(capacityPoints[79], calculateDistance(capacityPoints[92], capacityPoints[79]))
        capacityPoints[93].addConnection(capacityPoints[79], calculateDistance(capacityPoints[93], capacityPoints[79]))
        capacityPoints[79].addConnection(capacityPoints[94], calculateDistance(capacityPoints[79], capacityPoints[94]))
        capacityPoints[94].addConnection(capacityPoints[70], calculateDistance(capacityPoints[94], capacityPoints[70]))
        capacityPoints[69].addConnection(capacityPoints[119], calculateDistance(capacityPoints[70], capacityPoints[119]))
        capacityPoints[70].addConnection(capacityPoints[119], calculateDistance(capacityPoints[69], capacityPoints[119]))
        capacityPoints[70].addConnection(capacityPoints[91], calculateDistance(capacityPoints[70], capacityPoints[91]))
        capacityPoints[91].addConnection(capacityPoints[117], calculateDistance(capacityPoints[91], capacityPoints[117]))
        capacityPoints[117].addConnection(capacityPoints[90], calculateDistance(capacityPoints[117], capacityPoints[90]))
        capacityPoints[91].addConnection(capacityPoints[81], calculateDistance(capacityPoints[91], capacityPoints[81]))
        capacityPoints[81].addConnection(capacityPoints[115], calculateDistance(capacityPoints[0], capacityPoints[0]))
        capacityPoints[81].addConnection(capacityPoints[72], calculateDistance(capacityPoints[81], capacityPoints[72]))
        capacityPoints[72].addConnection(capacityPoints[95], calculateDistance(capacityPoints[72], capacityPoints[95]))
        capacityPoints[72].addConnection(capacityPoints[82], calculateDistance(capacityPoints[72], capacityPoints[82]))
        capacityPoints[95].addConnection(capacityPoints[74], calculateDistance(capacityPoints[95], capacityPoints[74]))
        capacityPoints[74].addConnection(capacityPoints[83], calculateDistance(capacityPoints[74], capacityPoints[83]))
        capacityPoints[74].addConnection(capacityPoints[96], calculateDistance(capacityPoints[74], capacityPoints[96]))
        capacityPoints[96].addConnection(capacityPoints[97], calculateDistance(capacityPoints[96], capacityPoints[97]))
        capacityPoints[97].addConnection(capacityPoints[98], calculateDistance(capacityPoints[97], capacityPoints[98]))
        capacityPoints[98].addConnection(capacityPoints[99], calculateDistance(capacityPoints[98], capacityPoints[99]))
        capacityPoints[99].addConnection(capacityPoints[76], calculateDistance(capacityPoints[99], capacityPoints[76]))
        capacityPoints[76].addConnection(capacityPoints[75], calculateDistance(capacityPoints[76], capacityPoints[75]))
        capacityPoints[76].addConnection(capacityPoints[78], calculateDistance(capacityPoints[76], capacityPoints[78]))
        capacityPoints[78].addConnection(capacityPoints[100], calculateDistance(capacityPoints[78], capacityPoints[100]))
        capacityPoints[100].addConnection(capacityPoints[84], calculateDistance(capacityPoints[100], capacityPoints[84]))
        capacityPoints[84].addConnection(capacityPoints[86], calculateDistance(capacityPoints[84], capacityPoints[86]))
        capacityPoints[86].addConnection(capacityPoints[101], calculateDistance(capacityPoints[86], capacityPoints[101]))
        capacityPoints[86].addConnection(capacityPoints[87], calculateDistance(capacityPoints[86], capacityPoints[87]))
        capacityPoints[87].addConnection(capacityPoints[104], calculateDistance(capacityPoints[87], capacityPoints[104]))
        capacityPoints[104].addConnection(capacityPoints[103], calculateDistance(capacityPoints[104], capacityPoints[103]))
        capacityPoints[103].addConnection(capacityPoints[102], calculateDistance(capacityPoints[103], capacityPoints[102]))
        capacityPoints[87].addConnection(capacityPoints[85], calculateDistance(capacityPoints[87], capacityPoints[85]))
        capacityPoints[85].addConnection(capacityPoints[80], calculateDistance(capacityPoints[85], capacityPoints[80]))
        capacityPoints[80].addConnection(capacityPoints[105], calculateDistance(capacityPoints[80], capacityPoints[105]))
        capacityPoints[105].addConnection(capacityPoints[118], calculateDistance(capacityPoints[105], capacityPoints[118]))
        capacityPoints[118].addConnection(capacityPoints[77], calculateDistance(capacityPoints[118], capacityPoints[77]))
        capacityPoints[77].addConnection(capacityPoints[75], calculateDistance(capacityPoints[77], capacityPoints[75]))
        capacityPoints[75].addConnection(capacityPoints[106], calculateDistance(capacityPoints[75], capacityPoints[106]))
        capacityPoints[106].addConnection(capacityPoints[107], calculateDistance(capacityPoints[106], capacityPoints[107]))
        capacityPoints[107].addConnection(capacityPoints[83], calculateDistance(capacityPoints[107], capacityPoints[83]))
        capacityPoints[83].addConnection(capacityPoints[108], calculateDistance(capacityPoints[83], capacityPoints[108]))
        capacityPoints[108].addConnection(capacityPoints[109], calculateDistance(capacityPoints[108], capacityPoints[109]))
        capacityPoints[109].addConnection(capacityPoints[73], calculateDistance(capacityPoints[109], capacityPoints[73]))
        capacityPoints[73].addConnection(capacityPoints[110], calculateDistance(capacityPoints[73], capacityPoints[110]))
        capacityPoints[110].addConnection(capacityPoints[111], calculateDistance(capacityPoints[110], capacityPoints[111]))
        capacityPoints[111].addConnection(capacityPoints[112], calculateDistance(capacityPoints[111], capacityPoints[112]))
        capacityPoints[112].addConnection(capacityPoints[71], calculateDistance(capacityPoints[112], capacityPoints[71]))
        capacityPoints[71].addConnection(capacityPoints[113], calculateDistance(capacityPoints[71], capacityPoints[113]))
        capacityPoints[113].addConnection(capacityPoints[114], calculateDistance(capacityPoints[113], capacityPoints[114]))
        capacityPoints[114].addConnection(capacityPoints[82], calculateDistance(capacityPoints[114], capacityPoints[82]))
        capacityPoints[82].addConnection(capacityPoints[115], calculateDistance(capacityPoints[82], capacityPoints[115]))
        capacityPoints[115].addConnection(capacityPoints[90], calculateDistance(capacityPoints[115], capacityPoints[90]))
        capacityPoints[90].addConnection(capacityPoints[69], calculateDistance(capacityPoints[90], capacityPoints[69]))

        // Capacity Ground Floor
        capacityPoints[133].addConnection(capacityPoints[178], calculateDistance(capacityPoints[133], capacityPoints[178]))
        capacityPoints[178].addConnection(capacityPoints[134], calculateDistance(capacityPoints[178], capacityPoints[134]))
        capacityPoints[134].addConnection(capacityPoints[121], calculateDistance(capacityPoints[134], capacityPoints[121]))
        capacityPoints[121].addConnection(capacityPoints[135], calculateDistance(capacityPoints[121], capacityPoints[135]))
        capacityPoints[135].addConnection(capacityPoints[166], calculateDistance(capacityPoints[135], capacityPoints[166]))
        capacityPoints[166].addConnection(capacityPoints[136], calculateDistance(capacityPoints[166], capacityPoints[136]))
        capacityPoints[136].addConnection(capacityPoints[167], calculateDistance(capacityPoints[136], capacityPoints[167]))
        capacityPoints[167].addConnection(capacityPoints[137], calculateDistance(capacityPoints[167], capacityPoints[137]))
        capacityPoints[137].addConnection(capacityPoints[138], calculateDistance(capacityPoints[137], capacityPoints[138]))
        capacityPoints[138].addConnection(capacityPoints[132], calculateDistance(capacityPoints[138], capacityPoints[132]))
        capacityPoints[132].addConnection(capacityPoints[139], calculateDistance(capacityPoints[132], capacityPoints[139]))
        capacityPoints[139].addConnection(capacityPoints[183], calculateDistance(capacityPoints[139], capacityPoints[183]))
        capacityPoints[183].addConnection(capacityPoints[140], calculateDistance(capacityPoints[183], capacityPoints[140]))
        capacityPoints[140].addConnection(capacityPoints[141], calculateDistance(capacityPoints[140], capacityPoints[141]))
        capacityPoints[141].addConnection(capacityPoints[142], calculateDistance(capacityPoints[141], capacityPoints[142]))
        capacityPoints[142].addConnection(capacityPoints[182], calculateDistance(capacityPoints[142], capacityPoints[182]))
        capacityPoints[182].addConnection(capacityPoints[168], calculateDistance(capacityPoints[182], capacityPoints[168]))
        capacityPoints[168].addConnection(capacityPoints[132], calculateDistance(capacityPoints[168], capacityPoints[132]))
        capacityPoints[182].addConnection(capacityPoints[143], calculateDistance(capacityPoints[182], capacityPoints[143]))
        capacityPoints[143].addConnection(capacityPoints[169], calculateDistance(capacityPoints[143], capacityPoints[169]))
        capacityPoints[169].addConnection(capacityPoints[170], calculateDistance(capacityPoints[169], capacityPoints[170]))
        capacityPoints[170].addConnection(capacityPoints[144], calculateDistance(capacityPoints[170], capacityPoints[144]))
        capacityPoints[144].addConnection(capacityPoints[171], calculateDistance(capacityPoints[144], capacityPoints[171]))
        capacityPoints[171].addConnection(capacityPoints[181], calculateDistance(capacityPoints[171], capacityPoints[181]))
        capacityPoints[181].addConnection(capacityPoints[145], calculateDistance(capacityPoints[181], capacityPoints[145]))
        capacityPoints[145].addConnection(capacityPoints[146], calculateDistance(capacityPoints[145], capacityPoints[146]))
        capacityPoints[146].addConnection(capacityPoints[151], calculateDistance(capacityPoints[146], capacityPoints[151]))
        capacityPoints[151].addConnection(capacityPoints[147], calculateDistance(capacityPoints[151], capacityPoints[147]))
        capacityPoints[147].addConnection(capacityPoints[148], calculateDistance(capacityPoints[147], capacityPoints[148]))
        capacityPoints[148].addConnection(capacityPoints[150], calculateDistance(capacityPoints[148], capacityPoints[150]))
        capacityPoints[150].addConnection(capacityPoints[149], calculateDistance(capacityPoints[150], capacityPoints[149]))
        capacityPoints[145].addConnection(capacityPoints[130], calculateDistance(capacityPoints[145], capacityPoints[130]))
        capacityPoints[130].addConnection(capacityPoints[153], calculateDistance(capacityPoints[130], capacityPoints[153]))
        capacityPoints[153].addConnection(capacityPoints[131], calculateDistance(capacityPoints[153], capacityPoints[131]))
        capacityPoints[131].addConnection(capacityPoints[152], calculateDistance(capacityPoints[131], capacityPoints[152]))
        capacityPoints[130].addConnection(capacityPoints[180], calculateDistance(capacityPoints[130], capacityPoints[180]))
        capacityPoints[180].addConnection(capacityPoints[154], calculateDistance(capacityPoints[180], capacityPoints[154]))
        capacityPoints[154].addConnection(capacityPoints[128], calculateDistance(capacityPoints[154], capacityPoints[128]))
        capacityPoints[128].addConnection(capacityPoints[129], calculateDistance(capacityPoints[128], capacityPoints[129]))
        capacityPoints[129].addConnection(capacityPoints[181], calculateDistance(capacityPoints[129], capacityPoints[181]))
        capacityPoints[128].addConnection(capacityPoints[155], calculateDistance(capacityPoints[128], capacityPoints[155]))
        capacityPoints[155].addConnection(capacityPoints[156], calculateDistance(capacityPoints[155], capacityPoints[156]))
        capacityPoints[156].addConnection(capacityPoints[126], calculateDistance(capacityPoints[156], capacityPoints[126]))
        capacityPoints[126].addConnection(capacityPoints[174], calculateDistance(capacityPoints[126], capacityPoints[174]))
        capacityPoints[174].addConnection(capacityPoints[173], calculateDistance(capacityPoints[174], capacityPoints[173]))
        capacityPoints[173].addConnection(capacityPoints[172], calculateDistance(capacityPoints[173], capacityPoints[172]))
        capacityPoints[172].addConnection(capacityPoints[129], calculateDistance(capacityPoints[172], capacityPoints[129]))
        capacityPoints[126].addConnection(capacityPoints[157], calculateDistance(capacityPoints[126], capacityPoints[157]))
        capacityPoints[157].addConnection(capacityPoints[158], calculateDistance(capacityPoints[157], capacityPoints[158]))
        capacityPoints[158].addConnection(capacityPoints[159], calculateDistance(capacityPoints[158], capacityPoints[159]))
        capacityPoints[159].addConnection(capacityPoints[160], calculateDistance(capacityPoints[159], capacityPoints[160]))
        capacityPoints[160].addConnection(capacityPoints[127], calculateDistance(capacityPoints[160], capacityPoints[127]))
        capacityPoints[127].addConnection(capacityPoints[161], calculateDistance(capacityPoints[127], capacityPoints[161]))
        capacityPoints[161].addConnection(capacityPoints[162], calculateDistance(capacityPoints[161], capacityPoints[162]))
        capacityPoints[162].addConnection(capacityPoints[123], calculateDistance(capacityPoints[162], capacityPoints[123]))
        capacityPoints[123].addConnection(capacityPoints[124], calculateDistance(capacityPoints[123], capacityPoints[124]))
        capacityPoints[124].addConnection(capacityPoints[176], calculateDistance(capacityPoints[124], capacityPoints[176]))
        capacityPoints[176].addConnection(capacityPoints[175], calculateDistance(capacityPoints[176], capacityPoints[175]))
        capacityPoints[175].addConnection(capacityPoints[125], calculateDistance(capacityPoints[175], capacityPoints[125]))
        capacityPoints[125].addConnection(capacityPoints[174], calculateDistance(capacityPoints[125], capacityPoints[174]))
        capacityPoints[162].addConnection(capacityPoints[163], calculateDistance(capacityPoints[162], capacityPoints[163]))
        capacityPoints[163].addConnection(capacityPoints[179], calculateDistance(capacityPoints[163], capacityPoints[179]))
        capacityPoints[179].addConnection(capacityPoints[122], calculateDistance(capacityPoints[179], capacityPoints[122]))
        capacityPoints[122].addConnection(capacityPoints[177], calculateDistance(capacityPoints[122], capacityPoints[177]))
        capacityPoints[177].addConnection(capacityPoints[123], calculateDistance(capacityPoints[177], capacityPoints[123]))
        capacityPoints[163].addConnection(capacityPoints[164], calculateDistance(capacityPoints[163], capacityPoints[164]))
        capacityPoints[164].addConnection(capacityPoints[120], calculateDistance(capacityPoints[164], capacityPoints[120]))
        capacityPoints[120].addConnection(capacityPoints[133], calculateDistance(capacityPoints[120], capacityPoints[133]))
        capacityPoints[122].addConnection(capacityPoints[165], calculateDistance(capacityPoints[122], capacityPoints[165]))
        capacityPoints[165].addConnection(capacityPoints[121], calculateDistance(capacityPoints[165], capacityPoints[121]))
        capacityPoints[130].addConnection(capacityPoints[146], calculateDistance(capacityPoints[130], capacityPoints[146]))

        // Capacity -1
        capacityPoints[184].addConnection(capacityPoints[206], calculateDistance(capacityPoints[184], capacityPoints[206]))
        capacityPoints[206].addConnection(capacityPoints[258], calculateDistance(capacityPoints[206], capacityPoints[258]))
        capacityPoints[258].addConnection(capacityPoints[207], calculateDistance(capacityPoints[258], capacityPoints[207]))
        capacityPoints[256].addConnection(capacityPoints[208], calculateDistance(capacityPoints[256], capacityPoints[208]))
        capacityPoints[208].addConnection(capacityPoints[239], calculateDistance(capacityPoints[208], capacityPoints[239]))
        capacityPoints[239].addConnection(capacityPoints[209], calculateDistance(capacityPoints[239], capacityPoints[209]))
        capacityPoints[209].addConnection(capacityPoints[240], calculateDistance(capacityPoints[209], capacityPoints[240]))
        capacityPoints[240].addConnection(capacityPoints[210], calculateDistance(capacityPoints[240], capacityPoints[210]))
        capacityPoints[210].addConnection(capacityPoints[241], calculateDistance(capacityPoints[210], capacityPoints[241]))
        capacityPoints[241].addConnection(capacityPoints[205], calculateDistance(capacityPoints[241], capacityPoints[205]))
        capacityPoints[205].addConnection(capacityPoints[201], calculateDistance(capacityPoints[205], capacityPoints[201]))
        capacityPoints[205].addConnection(capacityPoints[202], calculateDistance(capacityPoints[205], capacityPoints[202]))
        capacityPoints[202].addConnection(capacityPoints[211], calculateDistance(capacityPoints[202], capacityPoints[211]))
        capacityPoints[211].addConnection(capacityPoints[259], calculateDistance(capacityPoints[211], capacityPoints[259]))
        capacityPoints[259].addConnection(capacityPoints[212], calculateDistance(capacityPoints[259], capacityPoints[212]))
        capacityPoints[212].addConnection(capacityPoints[213], calculateDistance(capacityPoints[212], capacityPoints[213]))
        capacityPoints[213].addConnection(capacityPoints[214], calculateDistance(capacityPoints[213], capacityPoints[214]))
        capacityPoints[214].addConnection(capacityPoints[203], calculateDistance(capacityPoints[214], capacityPoints[203]))
        capacityPoints[203].addConnection(capacityPoints[257], calculateDistance(capacityPoints[203], capacityPoints[257]))
        capacityPoints[201].addConnection(capacityPoints[257], calculateDistance(capacityPoints[201], capacityPoints[257]))
        capacityPoints[257].addConnection(capacityPoints[215], calculateDistance(capacityPoints[257], capacityPoints[215]))
        capacityPoints[215].addConnection(capacityPoints[242], calculateDistance(capacityPoints[215], capacityPoints[242]))
        capacityPoints[242].addConnection(capacityPoints[216], calculateDistance(capacityPoints[242], capacityPoints[216]))
        capacityPoints[216].addConnection(capacityPoints[217], calculateDistance(capacityPoints[216], capacityPoints[217]))
        capacityPoints[217].addConnection(capacityPoints[260], calculateDistance(capacityPoints[217], capacityPoints[260]))
        capacityPoints[260].addConnection(capacityPoints[243], calculateDistance(capacityPoints[260], capacityPoints[243]))
        capacityPoints[243].addConnection(capacityPoints[198], calculateDistance(capacityPoints[243], capacityPoints[198]))
        capacityPoints[260].addConnection(capacityPoints[218], calculateDistance(capacityPoints[260], capacityPoints[218]))
        capacityPoints[218].addConnection(capacityPoints[219], calculateDistance(capacityPoints[218], capacityPoints[219]))
        capacityPoints[219].addConnection(capacityPoints[220], calculateDistance(capacityPoints[219], capacityPoints[220]))
        capacityPoints[220].addConnection(capacityPoints[200], calculateDistance(capacityPoints[220], capacityPoints[200]))
        capacityPoints[200].addConnection(capacityPoints[246], calculateDistance(capacityPoints[200], capacityPoints[246]))
        capacityPoints[246].addConnection(capacityPoints[221], calculateDistance(capacityPoints[246], capacityPoints[221]))
        capacityPoints[221].addConnection(capacityPoints[247], calculateDistance(capacityPoints[221], capacityPoints[247]))
        capacityPoints[247].addConnection(capacityPoints[199], calculateDistance(capacityPoints[247], capacityPoints[199]))
        capacityPoints[199].addConnection(capacityPoints[222], calculateDistance(capacityPoints[199], capacityPoints[222]))
        capacityPoints[222].addConnection(capacityPoints[223], calculateDistance(capacityPoints[222], capacityPoints[223]))
        capacityPoints[223].addConnection(capacityPoints[224], calculateDistance(capacityPoints[223], capacityPoints[224]))
        capacityPoints[224].addConnection(capacityPoints[248], calculateDistance(capacityPoints[224], capacityPoints[248]))
        capacityPoints[248].addConnection(capacityPoints[225], calculateDistance(capacityPoints[248], capacityPoints[225]))
        capacityPoints[225].addConnection(capacityPoints[197], calculateDistance(capacityPoints[225], capacityPoints[197]))
        capacityPoints[197].addConnection(capacityPoints[249], calculateDistance(capacityPoints[197], capacityPoints[249]))
        capacityPoints[249].addConnection(capacityPoints[198], calculateDistance(capacityPoints[249], capacityPoints[198]))
        capacityPoints[197].addConnection(capacityPoints[195], calculateDistance(capacityPoints[197], capacityPoints[195]))
        capacityPoints[195].addConnection(capacityPoints[251], calculateDistance(capacityPoints[195], capacityPoints[251]))
        capacityPoints[251].addConnection(capacityPoints[250], calculateDistance(capacityPoints[251], capacityPoints[250]))
        capacityPoints[250].addConnection(capacityPoints[196], calculateDistance(capacityPoints[250], capacityPoints[196]))
        capacityPoints[198].addConnection(capacityPoints[244], calculateDistance(capacityPoints[198], capacityPoints[244]))
        capacityPoints[244].addConnection(capacityPoints[196], calculateDistance(capacityPoints[244], capacityPoints[196]))
        capacityPoints[195].addConnection(capacityPoints[226], calculateDistance(capacityPoints[195], capacityPoints[226]))
        capacityPoints[226].addConnection(capacityPoints[193], calculateDistance(capacityPoints[226], capacityPoints[193]))
        capacityPoints[193].addConnection(capacityPoints[253], calculateDistance(capacityPoints[193], capacityPoints[253]))
        capacityPoints[253].addConnection(capacityPoints[252], calculateDistance(capacityPoints[253], capacityPoints[252]))
        capacityPoints[252].addConnection(capacityPoints[194], calculateDistance(capacityPoints[252], capacityPoints[194]))
        capacityPoints[196].addConnection(capacityPoints[245], calculateDistance(capacityPoints[196], capacityPoints[245]))
        capacityPoints[245].addConnection(capacityPoints[194], calculateDistance(capacityPoints[245], capacityPoints[194]))
        capacityPoints[193].addConnection(capacityPoints[227], calculateDistance(capacityPoints[193], capacityPoints[227]))
        capacityPoints[227].addConnection(capacityPoints[228], calculateDistance(capacityPoints[227], capacityPoints[228]))
        capacityPoints[228].addConnection(capacityPoints[191], calculateDistance(capacityPoints[228], capacityPoints[191]))
        capacityPoints[191].addConnection(capacityPoints[229], calculateDistance(capacityPoints[191], capacityPoints[229]))
        capacityPoints[229].addConnection(capacityPoints[230], calculateDistance(capacityPoints[229], capacityPoints[230]))
        capacityPoints[230].addConnection(capacityPoints[231], calculateDistance(capacityPoints[230], capacityPoints[231]))
        capacityPoints[231].addConnection(capacityPoints[187], calculateDistance(capacityPoints[231], capacityPoints[187]))
        capacityPoints[187].addConnection(capacityPoints[232], calculateDistance(capacityPoints[187], capacityPoints[232]))
        capacityPoints[232].addConnection(capacityPoints[233], calculateDistance(capacityPoints[232], capacityPoints[233]))
        capacityPoints[233].addConnection(capacityPoints[234], calculateDistance(capacityPoints[233], capacityPoints[234]))
        capacityPoints[234].addConnection(capacityPoints[189], calculateDistance(capacityPoints[234], capacityPoints[189]))
        capacityPoints[189].addConnection(capacityPoints[254], calculateDistance(capacityPoints[189], capacityPoints[254]))
        capacityPoints[254].addConnection(capacityPoints[190], calculateDistance(capacityPoints[254], capacityPoints[190]))
        capacityPoints[194].addConnection(capacityPoints[192], calculateDistance(capacityPoints[194], capacityPoints[192]))
        capacityPoints[192].addConnection(capacityPoints[238], calculateDistance(capacityPoints[192], capacityPoints[238]))
        capacityPoints[238].addConnection(capacityPoints[188], calculateDistance(capacityPoints[238], capacityPoints[188]))
        capacityPoints[188].addConnection(capacityPoints[190], calculateDistance(capacityPoints[188], capacityPoints[190]))
        capacityPoints[190].addConnection(capacityPoints[186], calculateDistance(capacityPoints[190], capacityPoints[186]))
        capacityPoints[189].addConnection(capacityPoints[235], calculateDistance(capacityPoints[189], capacityPoints[235]))
        capacityPoints[235].addConnection(capacityPoints[185], calculateDistance(capacityPoints[235], capacityPoints[185]))
        capacityPoints[185].addConnection(capacityPoints[236], calculateDistance(capacityPoints[185], capacityPoints[236]))
        capacityPoints[236].addConnection(capacityPoints[255], calculateDistance(capacityPoints[236], capacityPoints[255]))
        capacityPoints[255].addConnection(capacityPoints[237], calculateDistance(capacityPoints[255], capacityPoints[237]))
        capacityPoints[237].addConnection(capacityPoints[184], calculateDistance(capacityPoints[237], capacityPoints[184]))
        capacityPoints[207].addConnection(capacityPoints[256], calculateDistance(capacityPoints[207], capacityPoints[256]))
        capacityPoints[256].addConnection(capacityPoints[186], calculateDistance(capacityPoints[256], capacityPoints[186]))


        var xShop = carouselShops
        var xPrime = carouselPrime
        var xPoint = carouselPoints

        // carousel
        if (SelectedShops.selectedMall == 0){
            xShop = carouselShops
            xPrime = carouselPrime
            xPoint = carouselPoints
        }
        // capacity
        else if (SelectedShops.selectedMall == 1){
            xShop = capacityShops
            xPrime = capacityPrime
            xPoint = capacityPoints
        }

        val path = Path()
        val fromIndex = SelectedShops.selectedOptionFromIndex
        val toIndex = SelectedShops.selectedOptionToIndex
        val fromFloor = xShop[fromIndex].Floor
        val toFloor = xShop[toIndex].Floor
        val context = LocalContext.current
        val selectedItem = remember { mutableStateOf(fromFloor) }
        var path1Visibility by remember { mutableStateOf(true) }
        var path2Visibility by remember { mutableStateOf(false) }
        var scale by remember { mutableStateOf(1f) }
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Log.d("kat bilgisi", "$fromFloor")
        Log.d("kat bilgisi 2", "$toFloor")

        val pointList: MutableList<Coordinate> = mutableListOf()
        for (point in xPoint) {
            pointList.add(Coordinate(point.x, point.y))
        }

        val primeFromIndex = pointList.indexOf(Coordinate(xPrime[fromIndex].x, xPrime[fromIndex].y))
        val primeToIndex = pointList.indexOf(Coordinate(xPrime[toIndex].x, xPrime[toIndex].y))

        val start = xPoint[primeFromIndex]
        val destination = xPoint[primeToIndex]

        Log.d("st","$primeFromIndex")
        Log.d("cs","$primeToIndex")

        val shortestPath = dijkstraAlgorithm(start, destination)


        if (SelectedShops.selectedMall == 0){
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
                        modifier = Modifier.fillMaxSize().graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offsetX,
                            translationY = offsetY

                        ).pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale = when {
                                    scale < 1f -> 1f
                                    scale > 3f -> 3f
                                    else -> scale * zoom
                                }
                                offsetX += pan.x
                                offsetY += pan.y
                            }
                        })
                }
            }
        }
        else if (SelectedShops.selectedMall == 1){
            when (fromFloor) {
                -1 -> {
                    Image(painter = painterResource(id = R.drawable.capacity_b1),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize())
                }
                0 -> {
                    Image(painter = painterResource(id = R.drawable.capacity_0),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize())
                }
                1 -> {
                    Image(painter = painterResource(id = R.drawable.capacity_1),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize())
                }
                2 -> {
                    Image(painter = painterResource(id = R.drawable.capacity_2),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize().graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offsetX,
                            translationY = offsetY

                        ).pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale = when {
                                    scale < 1f -> 1f
                                    scale > 3f -> 3f
                                    else -> scale * zoom
                                }
                                offsetX += pan.x
                                offsetY += pan.y
                            }
                        })
                }
            }
        }


        if (fromFloor == toFloor){


            val animatedPathProgress = remember { Animatable(0f) }
            val pathLength = shortestPath.size

            LaunchedEffect(Unit) {
                while (true) {
                    animatedPathProgress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = (pathLength * 250), easing = LinearEasing)
                    )
                    delay(2000) // 2-second delay before each repetition
                    animatedPathProgress.snapTo(0f) // Reset the animation progress to the beginning
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center).graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offsetX,
                        translationY = offsetY
                    ).pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = when {
                                scale < 1f -> 1f
                                scale > 3f -> 3f
                                else -> scale * zoom
                            }
                            offsetX += pan.x
                            offsetY += pan.y
                        }
                    }
                    .drawWithCache {
                        val iconSize = 15.dp.toPx()
                        val startX = xShop[fromIndex].x * generateSize(coordinateSystem, size)[0]
                        val startY = xShop[fromIndex].y * generateSize(coordinateSystem, size)[1]
                        val endX = xShop[toIndex].x * generateSize(coordinateSystem, size)[0]
                        val endY = xShop[toIndex].y * generateSize(coordinateSystem, size)[1]

                        onDrawBehind {
                            val currentPathProgress = animatedPathProgress.value

                            path.reset()

                            val drawPathProgress = if (currentPathProgress >= 1f) 1f else currentPathProgress

                            path.moveTo(startX, startY)

                            val fromIndex = (shortestPath.size * drawPathProgress).toInt()
                            shortestPath.subList(0, fromIndex).forEachIndexed { index, point ->
                                val x = point.x * generateSize(coordinateSystem, size)[0]
                                val y = point.y * generateSize(coordinateSystem, size)[1]
                                path.lineTo(x, y)
                            }

                            if (currentPathProgress >= 1f) {
                                path.lineTo(endX, endY)
                            }

                            drawPath(path, caribbeanCurrent, style = Stroke(4.dp.toPx()))


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
                        }
                    }
            )
        }
        else{
            if (SelectedShops.selectedMall == 0){
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
            }
            else if (SelectedShops.selectedMall == 1){
                when (selectedItem.value) {
                    -1 -> {
                        Image(painter = painterResource(id = R.drawable.capacity_b1),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize())
                    }
                    0 -> {
                        Image(painter = painterResource(id = R.drawable.capacity_0),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize())
                    }
                    1 -> {
                        Image(painter = painterResource(id = R.drawable.capacity_1),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize())
                    }
                    2 -> {
                        Image(painter = painterResource(id = R.drawable.capacity_2),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize())
                    }
                }
            }

            if(fromFloor > toFloor){

                val startPoint = xPoint[primeFromIndex]
                var endStair = Point(0, 0f, 0f)


                val endPoint = xPoint[primeToIndex]
                var startStair = Point(0, 0f, 0f)


                if (SelectedShops.selectedMall == 0){
                    when (fromFloor) {
                        2 -> {
                            endStair = xPoint[119]
                        }

                        1 -> {
                            endStair = xPoint[65]
                        }

                        0 -> {
                            endStair = xPoint[41]
                        }

                        -1 -> {
                            endStair = xPoint[95]
                        }
                    }
                }
                else if (SelectedShops.selectedMall == 1){
                    when (fromFloor) {
                        2 -> {
                            val d1 = distance(xShop[fromIndex].x, xPoint[118].x, xShop[fromIndex].y, xPoint[118].y)
                            val d2 = distance(xShop[fromIndex].x, xPoint[119].x, xShop[fromIndex].y, xPoint[119].y)
                            if (d1 < d2){
                                endStair = xPoint[118]

                                if (toFloor == 1){
                                    startStair = xPoint[65]
                                }
                                if (toFloor == 0){
                                    startStair = xPoint[181]
                                }
                                if (toFloor == -1){
                                    startStair = xPoint[260]
                                }

                            }
                            else{
                                endStair = xPoint[119]
                                if (toFloor == 1){
                                    startStair = xPoint[68]
                                }
                                if (toFloor == 0){
                                    startStair = xPoint[178]
                                }
                                if (toFloor == -1){
                                    startStair = xPoint[258]
                                }
                            }
                        }
                        1 -> {
                            val d1 = distance(xShop[fromIndex].x, xPoint[64].x, xShop[fromIndex].y, xPoint[64].y)
                            val d2 = distance(xShop[fromIndex].x, xPoint[66].x, xShop[fromIndex].y, xPoint[66].y)
                            val d3 = distance(xShop[fromIndex].x, xPoint[67].x, xShop[fromIndex].y, xPoint[67].y)
                            if ((d1 < d2) and (d1 < d3)){
                                endStair = xPoint[64]
                                if (toFloor == 0){
                                    startStair = xPoint[181]
                                }
                                if (toFloor == -1){
                                    startStair = xPoint[260]
                                }
                            }
                            else if ((d2 < d1) and (d2 < d3)){
                                endStair = xPoint[66]
                                if (toFloor == 0){
                                    startStair = xPoint[183]
                                }
                                if (toFloor == -1){
                                    startStair = xPoint[259]
                                }
                            }
                            else{
                                endStair = xPoint[67]
                                if (toFloor == 0){
                                    startStair = xPoint[178]
                                }
                                if (toFloor == -1){
                                    startStair = xPoint[258]
                                }
                            }
                        }
                        0 -> {
                            val d1 = distance(xShop[fromIndex].x, xPoint[179].x, xShop[fromIndex].y, xPoint[179].y)
                            val d2 = distance(xShop[fromIndex].x, xPoint[180].x, xShop[fromIndex].y, xPoint[180].y)
                            val d3 = distance(xShop[fromIndex].x, xPoint[182].x, xShop[fromIndex].y, xPoint[182].y)
                            if ((d1 < d2) and (d1 < d3)){
                                endStair = xPoint[179]
                                startStair = xPoint[258]

                            }
                            else if ((d2 < d1) and (d2 < d3)){
                                endStair = xPoint[180]
                                startStair = xPoint[260]

                            }
                            else{
                                endStair = xPoint[182]
                                startStair = xPoint[259]

                            }
                        }
                    }
                }


                if (SelectedShops.selectedMall == 0){
                    when (toFloor) {
                        1 -> {
                            startStair = xPoint[65]
                        }

                        0 -> {
                            startStair = xPoint[39]
                        }

                        -1 -> {
                            startStair = xPoint[93]
                        }

                        -2 -> {
                            startStair = xPoint[126]
                        }
                    }
                }

                val shortestWayToStairs = dijkstraAlgorithm(startPoint, endStair)
                val shortestWayFromStairs = dijkstraAlgorithm(startStair, endPoint)

                val pathLength = shortestWayToStairs.size
                val pathLength2 = shortestWayFromStairs.size


                val animatedPathProgress = remember { Animatable(0f) }

                LaunchedEffect(Unit) {
                    while (true) {
                        animatedPathProgress.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(durationMillis = (pathLength * 250), easing = LinearEasing)
                        )
                        delay(2000) // 2-second delay before each repetition
                        animatedPathProgress.snapTo(0f) // Reset the animation progress to the beginning
                    }
                }

                val animatedPathProgress2 = remember { Animatable(0f) }

                LaunchedEffect(Unit) {
                    while (true) {
                        animatedPathProgress2.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(durationMillis = (pathLength2 * 250), easing = LinearEasing)
                        )
                        delay(2000) // 2-second delay before each repetition
                        animatedPathProgress2.snapTo(0f) // Reset the animation progress to the beginning
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .drawWithCache {

                            onDrawBehind {
                                val iconSize = 15.dp.toPx()
                                val startX =
                                    xShop[fromIndex].x * generateSize(coordinateSystem, size)[0]
                                val startY =
                                    xShop[fromIndex].y * generateSize(coordinateSystem, size)[1]



                                val endX =
                                    xShop[toIndex].x * generateSize(coordinateSystem, size)[0]
                                val endY =
                                    xShop[toIndex].y * generateSize(coordinateSystem, size)[1]


                                if (path1Visibility) {
                                    val currentPathProgress = animatedPathProgress.value
                                    val drawPathProgress = if (currentPathProgress >= 1f) 1f else currentPathProgress

                                    path.reset()


                                    path.moveTo(startX, startY)

                                    val fromIndex = (shortestWayToStairs.size * drawPathProgress).toInt()
                                    shortestWayToStairs.subList(0, fromIndex).forEachIndexed { index, point ->
                                        val x = point.x * generateSize(coordinateSystem, size)[0]
                                        val y = point.y * generateSize(coordinateSystem, size)[1]
                                        path.lineTo(x, y)
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


                                if (path2Visibility) {
                                    val currentPathProgress = animatedPathProgress2.value
                                    val drawPathProgress = if (currentPathProgress >= 1f) 1f else currentPathProgress

                                    path.reset()

                                    path.moveTo(
                                        startStair.x * generateSize(coordinateSystem, size)[0],
                                        startStair.y * generateSize(coordinateSystem, size)[1]
                                    )

                                    val fromIndex = (shortestWayFromStairs.size * drawPathProgress).toInt()
                                    shortestWayFromStairs.subList(0, fromIndex).forEachIndexed { index, point ->
                                        val x = point.x * generateSize(coordinateSystem, size)[0]
                                        val y = point.y * generateSize(coordinateSystem, size)[1]
                                        path.lineTo(x, y)
                                    }

                                    if (currentPathProgress >= 1f) {
                                        path.lineTo(endX, endY)
                                    }


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
                val startPoint = xPoint[primeFromIndex]
                var endStair = Point(0, 0f, 0f)


                val endPoint = xPoint[primeToIndex]
                var startStair = Point(0, 0f, 0f)

                if (SelectedShops.selectedMall == 0){
                    when (fromFloor) {
                        1 -> {
                            endStair = xPoint[63]
                        }

                        0 -> {
                            endStair = xPoint[40]
                        }

                        -1 -> {
                            endStair = xPoint[94]
                        }

                        -2 -> {
                            endStair = xPoint[126]
                        }
                    }
                }
                else if (SelectedShops.selectedMall == 1){
                    when (fromFloor) {
                        1 -> {
                            val d1 = distance(xShop[fromIndex].x, xPoint[65].x, xShop[fromIndex].y, xPoint[65].y)
                            val d2 = distance(xShop[fromIndex].x, xPoint[68].x, xShop[fromIndex].y, xPoint[68].y)
                            if (d1 < d2){
                                endStair = xPoint[65]
                                startStair = xPoint[118]

                            }
                            else{
                                endStair = xPoint[68]
                                startStair = xPoint[119]
                            }
                        }
                        0 -> {
                            val d1 = distance(xShop[fromIndex].x, xPoint[181].x, xShop[fromIndex].y, xPoint[181].y)
                            val d2 = distance(xShop[fromIndex].x, xPoint[183].x, xShop[fromIndex].y, xPoint[183].y)
                            val d3 = distance(xShop[fromIndex].x, xPoint[178].x, xShop[fromIndex].y, xPoint[178].y)
                            if ((d1 < d2) and (d1 < d3)){
                                endStair = xPoint[181]
                                if (toFloor == 1){
                                    startStair = xPoint[64]
                                }
                                if (toFloor == 2){
                                    startStair = xPoint[118]
                                }
                            }
                            else if ((d2 < d1) and (d2 < d3)){
                                endStair = xPoint[183]
                                if (toFloor == 1){
                                    startStair = xPoint[66]
                                }
                                if (toFloor == 2){
                                    val d1 = distance(xShop[toIndex].x, xPoint[118].x, xShop[toIndex].y, xPoint[118].y)
                                    val d2 = distance(xShop[toIndex].x, xPoint[119].x, xShop[toIndex].y, xPoint[119].y)
                                    if (d1 <d2){
                                        startStair = xPoint[118]
                                    }
                                    else{
                                        startStair = xPoint[119]
                                    }
                                }
                            }
                            else{
                                endStair = xPoint[178]
                                if (toFloor == 1){
                                    startStair = xPoint[67]
                                }
                                if (toFloor == 2){
                                    startStair = xPoint[119]
                                }
                            }
                        }
                        -1 -> {
                            val d1 = distance(xShop[fromIndex].x, xPoint[260].x, xShop[fromIndex].y, xPoint[260].y)
                            val d2 = distance(xShop[fromIndex].x, xPoint[259].x, xShop[fromIndex].y, xPoint[259].y)
                            val d3 = distance(xShop[fromIndex].x, xPoint[258].x, xShop[fromIndex].y, xPoint[258].y)
                            if ((d1 < d2) and (d1 < d3)){
                                endStair = xPoint[260]
                                if (toFloor == 0){
                                    startStair = xPoint[180]
                                }
                                if (toFloor == 1){
                                    startStair = xPoint[64]
                                }
                                if (toFloor == 2){
                                    val d1 = distance(xShop[toIndex].x, xPoint[118].x, xShop[toIndex].y, xPoint[118].y)
                                    val d2 = distance(xShop[toIndex].x, xPoint[119].x, xShop[toIndex].y, xPoint[119].y)
                                    if (d1 <d2){
                                        startStair = xPoint[118]
                                    }
                                    else{
                                        startStair = xPoint[119]
                                    }
                                }
                            }
                            else if ((d2 < d1) and (d2 < d3)){
                                endStair = xPoint[259]
                                if (toFloor == 0){
                                    startStair = xPoint[182]
                                }
                                if (toFloor == 1){
                                    startStair = xPoint[66]
                                }
                                if (toFloor == 2){
                                    val d1 = distance(xShop[toIndex].x, xPoint[118].x, xShop[toIndex].y, xPoint[118].y)
                                    val d2 = distance(xShop[toIndex].x, xPoint[119].x, xShop[toIndex].y, xPoint[119].y)
                                    if (d1 <d2){
                                        startStair = xPoint[118]
                                    }
                                    else{
                                        startStair = xPoint[119]
                                    }
                                }
                            }
                            else{
                                endStair = xPoint[258]
                                if (toFloor == 0){
                                    startStair = xPoint[179]
                                }
                                if (toFloor == 1){
                                    startStair = xPoint[67]
                                }
                                if (toFloor == 2){
                                    val d1 = distance(xShop[toIndex].x, xPoint[118].x, xShop[toIndex].y, xPoint[118].y)
                                    val d2 = distance(xShop[toIndex].x, xPoint[119].x, xShop[toIndex].y, xPoint[119].y)
                                    if (d1 <d2){
                                        startStair = xPoint[118]
                                    }
                                    else{
                                        startStair = xPoint[119]
                                    }
                                }
                            }
                        }

                    }

                }

                if (SelectedShops.selectedMall == 0){
                    when (toFloor) {
                        2 -> {
                            startStair = xPoint[118]
                        }

                        1 -> {
                            startStair = xPoint[64]
                        }

                        0 -> {
                            startStair = xPoint[38]
                        }

                        -1 -> {
                            startStair = xPoint[95]
                        }
                    }
                }


                val shortestWayToStairs = dijkstraAlgorithm(startPoint, endStair)
                val shortestWayFromStairs = dijkstraAlgorithm(startStair, endPoint)

                val pathLength = shortestWayToStairs.size
                val pathLength2 = shortestWayFromStairs.size


                val animatedPathProgress = remember { Animatable(0f) }

                LaunchedEffect(Unit) {
                    while (true) {
                        animatedPathProgress.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(durationMillis = (pathLength * 250), easing = LinearEasing)
                        )
                        delay(2000) // 2-second delay before each repetition
                        animatedPathProgress.snapTo(0f) // Reset the animation progress to the beginning
                    }
                }

                val animatedPathProgress2 = remember { Animatable(0f) }

                LaunchedEffect(Unit) {
                    while (true) {
                        animatedPathProgress2.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(durationMillis = (pathLength2 * 250), easing = LinearEasing)
                        )
                        delay(2000) // 2-second delay before each repetition
                        animatedPathProgress2.snapTo(0f) // Reset the animation progress to the beginning
                    }
                }


                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .drawWithCache {

                            onDrawBehind {
                                val iconSize = 15.dp.toPx()
                                val startX =
                                    xShop[fromIndex].x * generateSize(coordinateSystem, size)[0]
                                val startY =
                                    xShop[fromIndex].y * generateSize(coordinateSystem, size)[1]

                                val endX =
                                    xShop[toIndex].x * generateSize(coordinateSystem, size)[0]
                                val endY =
                                    xShop[toIndex].y * generateSize(coordinateSystem, size)[1]


                                if (path1Visibility) {
                                    val currentPathProgress = animatedPathProgress.value
                                    val drawPathProgress = if (currentPathProgress >= 1f) 1f else currentPathProgress

                                    path.reset()

                                    path.moveTo(startX, startY)

                                    val fromIndex = (shortestWayToStairs.size * drawPathProgress).toInt()
                                    shortestWayToStairs.subList(0, fromIndex).forEachIndexed { index, point ->
                                        val x = point.x * generateSize(coordinateSystem, size)[0]
                                        val y = point.y * generateSize(coordinateSystem, size)[1]
                                        path.lineTo(x, y)
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

                                if (path2Visibility) {
                                    val currentPathProgress = animatedPathProgress2.value
                                    val drawPathProgress = if (currentPathProgress >= 1f) 1f else currentPathProgress

                                    path.reset()

                                    path.moveTo(
                                        startStair.x * generateSize(coordinateSystem, size)[0],
                                        startStair.y * generateSize(coordinateSystem, size)[1]
                                    )

                                    val fromIndex = (shortestWayFromStairs.size * drawPathProgress).toInt()
                                    shortestWayFromStairs.subList(0, fromIndex).forEachIndexed { index, point ->
                                        val x = point.x * generateSize(coordinateSystem, size)[0]
                                        val y = point.y * generateSize(coordinateSystem, size)[1]
                                        path.lineTo(x, y)
                                    }

                                    if (currentPathProgress >= 1f) {
                                        path.lineTo(endX, endY)
                                    }


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