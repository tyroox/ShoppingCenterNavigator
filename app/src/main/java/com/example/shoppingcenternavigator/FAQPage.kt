package com.example.shoppingcenternavigator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun FAQPage() {
    val faqList = listOf(
        stringResource(id = R.string.FAQQuestion1) to stringResource(id = R.string.FAQAnswer1),
        "Can I track my order?" to "Yes, you can track your order by...",
        "What is the return policy?" to "Our return policy allows...",
        "How do I contact customer support?" to "You can contact our customer support team...",
        "Do you offer international shipping?" to "Yes, we offer international shipping to..."
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Frequently Asked Questions",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        faqList.forEachIndexed { index, (question, answer) ->
            FAQItem(index + 1, question, answer)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun FAQItem(index: Int, question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable { expanded = !expanded }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$index. ",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.width(32.dp)
                )
                Text(
                    text = question,
                    style = MaterialTheme.typography.body1,
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (expanded) {
                Text(
                    text = answer,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
