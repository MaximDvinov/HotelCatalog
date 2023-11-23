package com.hotel.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hotel.ui.theme.body

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun Peculiarities(list: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        list.forEach {
            Peculiarity(it)
        }
    }
}

@Composable
fun Peculiarity(it: String?) {
    Text(
        text = it ?: "",
        modifier = Modifier
            .background(
                color = Color(0xFFFBFBFC), shape = RoundedCornerShape(size = 5.dp)
            )
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
        style = body,
        color = Color(0xFF828796)
    )
}

