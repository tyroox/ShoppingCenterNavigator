package com.example.shoppingcenternavigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.shoppingcenternavigator.ui.theme.wineBerry

@Composable
fun FAQPage(selectedItem: MutableState<Int>) {
    BackHandler(onBack = {selectedItem.value = 4})
    val scrollState = rememberScrollState()
    val faqList = listOf(
        stringResource(id = R.string.FAQQuestion1) to stringResource(id = R.string.FAQAnswer1),
        "What features does the app offer?" to "\nThe MallPS app offers features such as: navigation inside the shopping center by providing directions between desired stores, locations of the shopping centers, shops in the selected shopping centers and their floor plans.",
        "Can I use the app in multiple shopping centers?" to "\nYes, you can choose the shopping center from the 'Home Page'.",
        "How can I get directions to the shopping centers?" to "\nYou can view the locations of the shopping centers in the application from the 'GPS' page. This page can direct you to Google Maps and provide directions from your location.",
        "Is there a map of the shopping center available within the app?" to "\nYes, after the shopping center is selected, you can view the floor plans from the 'Stores' page by taping the 'Floor Plans' option and switch between the floors as you wish"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier.padding(16.dp).verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 2.dp, end = 4.dp)
                        .clickable { selectedItem.value = 4 },
                    tint = Color.White
                )
                Text(
                    text = "Help",
                    style = MaterialTheme.typography.h5,
                    color = wineBerry
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {

                Column(modifier = Modifier.padding(4.dp)) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = "Frequently Asked Questions",
                            style = MaterialTheme.typography.h5,
                            color = wineBerry
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    faqList.forEachIndexed { index, (question, answer) ->
                        FAQItem(index + 1, question, answer)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun FAQItem(index: Int, question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.Transparent,
        contentColor = wineBerry
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable { expanded = !expanded }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$index. ",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.width(32.dp),
                    color = Color.White
                )
                Text(
                    text = question,
                    style = MaterialTheme.typography.h6,
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (expanded) {
                Text(
                    text = answer,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White
                )
            }
        }
    }
}
