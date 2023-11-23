package com.hotel.catalog.screens.hotel

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hotel.catalog.R
import com.hotel.hotel.model.Hotel
import com.hotel.ui.component.ErrorScreen
import com.hotel.ui.component.HotelHeader
import com.hotel.ui.component.ImagePager
import com.hotel.ui.component.LoadScreen
import com.hotel.ui.component.Peculiarities
import com.hotel.ui.component.PriceInfo
import com.hotel.ui.component.PrimaryButton
import com.hotel.ui.component.format
import com.hotel.ui.theme.Headline1
import com.hotel.ui.theme.body
import com.hotel.ui.theme.subtitle
import com.hotel.ui.theme.subtitleLight
import com.hotel.ui.theme.title

@Composable
fun HotelScreen(viewModel: HotelViewModel, onClickSelectRoom: (String) -> Unit) {
    val state by viewModel.state.collectAsState()

    AnimatedContent(targetState = state, label = "") {
        when (it) {
            is HotelState.Success -> {
                SuccessHotelScreen(it.hotel, onClickSelectRoom)
            }

            is HotelState.Failure -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    errorMessage = it.message
                )
            }

            is HotelState.Loading -> {
                LoadScreen(modifier = Modifier.fillMaxSize())
            }

            is HotelState.Init -> {
                Box(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun SuccessHotelScreen(hotel: Hotel, onClickSelectRoom: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        HotelBaseInfo(hotel)


        HotelDetailedInfo(hotel)


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.to_select_room),
                onClick = {
                    onClickSelectRoom(hotel.name ?: "")
                }
            )
        }
    }
}

@Composable
fun HotelBaseInfo(hotel: Hotel) {
    Column(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomStart = 12.dp, bottomEnd = 12.dp
                )
            )
            .background(Color.White)
            .padding(bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.hotel), style = Headline1, modifier = Modifier.align(Alignment.Center)
            )
        }

        ImagePager(
            modifier = Modifier
                .fillMaxWidth()
                .height(257.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(15.dp)), images = hotel.imageUrls
        )

        HotelHeader(
            modifier = Modifier.padding(16.dp),
            rating = hotel.rating,
            ratingName = hotel.ratingName,
            hotelName = hotel.name ?: "",
            hotelAddress = hotel.address ?: ""
        )

        PriceInfo(
            modifier = Modifier.padding(horizontal = 16.dp),
            price = "от ${hotel.minimalPrice?.format()} ₽",
            priceForIt = hotel.priceForIt?.lowercase() ?: ""
        )
    }
}

@Composable
fun HotelDetailedInfo(hotel: Hotel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = stringResource(id = R.string.hotel_info), modifier = Modifier, style = title)
        Spacer(modifier = Modifier.height(16.dp))
        Peculiarities(hotel.aboutTheHotel?.peculiarities.orEmpty())
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = hotel.aboutTheHotel?.description ?: "",
            style = subtitleLight,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            modifier = Modifier
                .background(
                    color = Color(0xFFFBFBFC), shape = RoundedCornerShape(size = 15.dp)
                )
                .padding(15.dp)
        ) {
            Futures(
                icon = R.drawable.emoji_happy,
                text = stringResource(id = R.string.conveniences),
                text2 = stringResource(id = R.string.necessary),
            )
            Divider(
                color = Color(0x26828796),
                thickness = 1.dp,
                modifier = Modifier.padding(start = 36.dp, top = 10.dp, bottom = 10.dp)
            )
            Futures(
                icon = R.drawable.emoji_happy,
                text = stringResource(id = R.string.included),
                text2 = stringResource(id = R.string.necessary),
            )
            Divider(
                color = Color(0x26828796),
                thickness = 1.dp,
                modifier = Modifier.padding(start = 36.dp, top = 10.dp, bottom = 10.dp)
            )
            Futures(
                icon = R.drawable.emoji_happy,
                text = stringResource(id = R.string.not_included),
                text2 = stringResource(id = R.string.necessary),
            )
        }
    }
}

@Composable
private fun Futures(icon: Int, text: String, text2: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { }) {
        Icon(
            painterResource(id = icon), contentDescription = null, modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = text,
                style = subtitle,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = text2,
                style = body,
                color = Color(0xFF828796),
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            painterResource(id = R.drawable.arrow),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

