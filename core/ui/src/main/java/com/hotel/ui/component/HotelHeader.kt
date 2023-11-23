package com.hotel.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hotel.ui.R
import com.hotel.ui.theme.body
import com.hotel.ui.theme.subtitle
import com.hotel.ui.theme.title

@Composable
fun HotelHeader(
    modifier: Modifier,
    rating: Int?,
    ratingName: String?,
    hotelName: String,
    hotelAddress: String,
) {
    Column(
        modifier
    ) {
        if (rating != null && ratingName != null) {
            RatingLabel(rating, ratingName)
        }


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = hotelName, style = title, modifier = Modifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = hotelAddress,
            style = body,
            color = Color(0xFF0D72FF),
            modifier = Modifier
        )
    }

}

@Composable
fun RatingLabel(rating: Int, ratingName: String) {
    Row(
        Modifier
            .background(
                color = Color(0x33FFC700), shape = RoundedCornerShape(size = 5.dp)
            )
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(id = R.drawable.star), contentDescription = "", tint = Color(0xFFFFA800)
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = "$rating $ratingName", style = subtitle, color = Color(0xFFFFA800)
        )
    }
}
