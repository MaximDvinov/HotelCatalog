package com.hotel.booking.screens.paymentresult

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hotel.booking.R
import com.hotel.ui.component.PrimaryButton
import com.hotel.ui.theme.Headline1
import com.hotel.ui.theme.subtitleLight
import com.hotel.ui.theme.title

@Composable
fun PaymentResultScreen(onConfirmClick: () -> Unit, onBackClick: () -> Unit) {
    Column(Modifier.background(Color.White)) {
        Box(
            modifier = Modifier
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
                text = "Заказ оплачен",
                style = Headline1,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(94.dp)
                    .height(94.dp)
                    .background(
                        color = Color(0xFFF6F6F9),
                        shape = RoundedCornerShape(size = 1000.dp)
                    )
                    .padding(start = 25.dp, top = 25.dp, end = 25.dp, bottom = 25.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.party_popper),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Ваш заказ принят в работу",
                style = title,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Подтверждение заказа №104893 может занять некоторое время (от 1 часа до суток). Как только мы получим ответ от туроператора, вам на почту придет уведомление.",
                style = subtitleLight,
                textAlign = TextAlign.Center,
                color = Color(0xFF828796),
            )
        }

        Box(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            PrimaryButton(modifier = Modifier.fillMaxWidth(), text = "Супер!") {
                onConfirmClick()
            }
        }
    }
}