package com.example.shoppingcenternavigator

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingcenternavigator.ui.theme.caribbeanCurrent
import com.example.shoppingcenternavigator.ui.theme.skyBlue

@Composable
fun StoreSearchBar(
    variables: List<Shops>,
    onSearch: (String) -> Unit,
    onBoxClick: (Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // Define the map associating text with background image resources
    val backgroundImages = mapOf(
        "acıbadem hastanesi" to R.drawable.acibadem_logo,
        "adabella pizza" to R.drawable.adabella_pizza_logo,
        "colin's" to R.drawable.colins_logo,
        "hummel" to R.drawable.hummel_logo


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
            placeholder = { Text("Ara", color = caribbeanCurrent) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.isabelline),
                disabledTextColor = Color.Black,
                cursorColor = colorResource(id = R.color.caribbeanCurrent),
                unfocusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent),
                focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent)
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
                variable.Name.toLowerCase().contains(searchQuery.toLowerCase())
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
                                backgroundImages[variable.Name.toLowerCase()]
                            val backgroundPainter = backgroundResource?.let { painterResource(it) }

                            Box(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .size(boxSize)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(skyBlue.copy(alpha = 0.25f))
                                    .clickable {
                                        onBoxClick(index)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(8.dp),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (backgroundPainter != null) {
                                        Image(
                                            painter = backgroundPainter,
                                            contentDescription = null,
                                            contentScale = ContentScale.Inside,
                                            modifier = Modifier
                                                .size(100.dp)
                                                .clip(RectangleShape)
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 16.dp),
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = variable.Name,
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                    if (variable.Floor == 0) {
                                        Text(
                                            text = "Zemin Kat",
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    } else {
                                        Text(
                                            text = variable.Floor.toString() + ". Kat",
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                    }
                        else {
                            Spacer(Modifier.size(boxSize))
                        }
                }
            }
        }
    }
    }
}