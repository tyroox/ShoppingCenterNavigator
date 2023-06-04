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
        stringResource(id = R.string.FAQQuestion2) to stringResource(id = R.string.FAQAnswer2),
        stringResource(id = R.string.FAQQuestion3) to stringResource(id = R.string.FAQAnswer3),
        stringResource(id = R.string.FAQQuestion4) to stringResource(id = R.string.FAQAnswer4),
        stringResource(id = R.string.FAQQuestion5) to stringResource(id = R.string.FAQAnswer5)
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(scrollState),
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
                    text = stringResource(id = R.string.helpButton),
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
                            text = stringResource(id = R.string.FAQButton),
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
