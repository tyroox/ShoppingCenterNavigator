import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingcenternavigator.R
import com.example.shoppingcenternavigator.ui.theme.caribbeanCurrent
import com.example.shoppingcenternavigator.ui.theme.wineBerry

@Composable
fun SearchBar(
    variables: List<String>,
    onSearch: (String) -> Unit,
    onBoxClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // Define the map associating text with background image resources
    val backgroundImages = mapOf(
        "carousel" to R.drawable.carousel_avm,
        "capacity" to R.drawable.capacity_avm,
        // Add more mappings for other text and corresponding image resources
    )

    Column(Modifier.fillMaxWidth()) {
        // Search bar
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                onSearch(query)
            },
            placeholder = { Text("Ara", color = wineBerry) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = wineBerry,
                unfocusedIndicatorColor = wineBerry,
                focusedIndicatorColor = wineBerry,
                textColor = wineBerry
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Box-shaped items
        val screenWidth = with(LocalDensity.current) {
            LocalConfiguration.current.screenWidthDp.dp
        }
        val boxSize = with(LocalDensity.current) {
            screenWidth / 2 - 16.dp // Adjust margin as needed
        }

        val filteredVariables = if (searchQuery.isEmpty()) {
            variables
        } else {
            variables.filter { variable ->
                variable.toLowerCase().contains(searchQuery.toLowerCase())
            }
        }

        val numRows = (filteredVariables.size + 1) / 2

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(numRows) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (col in 0 until 2) {
                        val index = row * 2 + col
                        if (index < filteredVariables.size) {
                            val variable = filteredVariables[index]
                            val backgroundResource =
                                backgroundImages[variable.toLowerCase()]
                            val backgroundPainter = backgroundResource?.let { painterResource(it) }

                            Box(
                                modifier = Modifier
                                    .size(boxSize)
                                    .background(Color.Transparent)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable {
                                        onBoxClick(variable)
                                    }
                            ) {
                                if (backgroundPainter != null) {
                                    Image(
                                        painter = backgroundPainter,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                            .alpha(0.675f)
                                    )
                                }
                                Text(
                                    text = variable,
                                    color = Color.Black,
                                    modifier = Modifier.align(Alignment.Center),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Spacer(Modifier.size(boxSize))
                        }
                    }
                }
            }
        }
    }
}
