package com.example.shoppingcenternavigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.shoppingcenternavigator.ui.theme.wineBerry

@Composable
fun FAQPage(selectedItem: MutableState<Int>) {
    BackHandler(onBack = {selectedItem.value = 4})
    val faqList = listOf(
        stringResource(id = R.string.FAQQuestion1) to stringResource(id = R.string.FAQAnswer1),
        "Can I track my order?" to "Yes, you can track your order by...",
        "What is the return policy?" to "Our return policy allows...",
        "How do I contact customer support?" to "You can contact our customer support team...",
        "Do you offer international shipping?" to "Yes, we offer international shipping to..."
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically){
            Icon(Icons.Default.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 0.dp, end = 4.dp)
                    .size(40.dp)
                    .clickable {
                        selectedItem.value = 4
                    }, tint = Color.White)
            Text(
                text = "Frequently Asked Questions",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 2.dp),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        faqList.forEachIndexed { index, (question, answer) ->
            FAQItem(index + 1, question, answer)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}}


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
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.width(32.dp)
                )
                Text(
                    text = question,
                    style = MaterialTheme.typography.h5,
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (expanded) {
                Text(
                    text = answer,
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }
        }
    }
}
