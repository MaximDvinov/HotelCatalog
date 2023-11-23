package com.hotel.catalog.screens.rooms

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hotel.catalog.R
import com.hotel.hotel.model.Room
import com.hotel.ui.component.ErrorScreen
import com.hotel.ui.component.ImagePager
import com.hotel.ui.component.LoadScreen
import com.hotel.ui.component.Peculiarities
import com.hotel.ui.component.PriceInfo
import com.hotel.ui.component.PrimaryButton
import com.hotel.ui.component.format
import com.hotel.ui.theme.Headline1
import com.hotel.ui.theme.subtitle
import com.hotel.ui.theme.title

@Composable
fun RoomsScreen(
    viewModel: RoomsViewModel,
    hotelName: String,
    onClickRoom: (Room) -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()


    Column {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painterResource(id = R.drawable.arrow_left),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )

            }
            Text(
                text = hotelName,
                style = Headline1,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp),
                textAlign = TextAlign.Center
            )
        }
        AnimatedContent(targetState = state, label = "") {
            when (it) {
                is RoomsState.Success -> {
                    SuccessRoomScreen(it, onClickRoom)
                }

                is RoomsState.Failure -> {
                    ErrorScreen(modifier = Modifier.fillMaxSize(), errorMessage = it.message)
                }

                RoomsState.Loading -> {
                    LoadScreen(modifier = Modifier.fillMaxSize())
                }

                RoomsState.Init -> {
                    Box(modifier = Modifier.fillMaxSize()) {}
                }
            }
        }
    }


}

@Composable
private fun SuccessRoomScreen(
    state: RoomsState.Success,
    onClickRoom: (Room) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(state.rooms) {
            RoomItem(it, onClick = {
                onClickRoom(it)
            })
        }
    }
}

@Composable
fun RoomItem(room: Room, onClick: () -> Unit) {
    Column(
        Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        ImagePager(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .height(257.dp),
            images = room.imageUrls.orEmpty()
        )
        Text(
            text = room.name ?: "", style = title
        )
        Spacer(modifier = Modifier.height(8.dp))
        Peculiarities(room.peculiarities.orEmpty())
        Spacer(modifier = Modifier.height(8.dp))
        DetailButton()
        Spacer(modifier = Modifier.height(16.dp))
        PriceInfo(price = "${room.price?.format()} ₽", priceForIt = room.pricePer ?: "")
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.select_room), onClick = onClick)
    }
}

@Composable
fun DetailButton() {
    Row(
        Modifier
            .padding()
            .background(
                color = Color(0x1A0D72FF), shape = RoundedCornerShape(size = 5.dp)
            )
            .padding(2.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.about_the_room),
            style = subtitle,
            color = Color(0xFF0D72FF),
            modifier = Modifier.padding(start = 8.dp, end = 2.dp)
        )

        Spacer(modifier = Modifier.width(2.dp))

        Icon(
            painterResource(id = R.drawable.arrow),
            contentDescription = "",
            tint = Color(0xFF0D72FF),
            modifier = Modifier.size(24.dp)
        )
    }
}
