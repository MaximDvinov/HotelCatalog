package com.hotel.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotel.ui.theme.bigTitle
import com.hotel.ui.theme.subtitleLight

@Composable
fun Int.format() =
    String.format("%,d", this).replace(",", " ")

@Composable
fun PriceInfo(modifier: Modifier = Modifier, price: String, priceForIt: String) {
    Row(modifier, verticalAlignment = Alignment.Bottom) {
        Text(
            text = price,
            style = bigTitle,
        )
        Text(
            text = priceForIt,
            style = subtitleLight,
            modifier = Modifier.padding(start = 11.dp, bottom = 3.dp)
        )
    }
}
