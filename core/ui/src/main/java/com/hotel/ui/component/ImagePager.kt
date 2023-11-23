package com.hotel.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(modifier: Modifier, images: List<String>) {
    val size by remember { derivedStateOf { images.size } }

    val pagerState = rememberPagerState(pageCount = {
        size
    })

    Box(modifier = modifier) {
        HorizontalPager(modifier = Modifier, state = pagerState) { page ->
            Image(images, page)
        }

        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
                .padding(vertical = 5.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color by animateColorAsState(
                    targetValue = getColorIndicatorItem(
                        iteration,
                        pagerState.currentPage
                    ), label = ""
                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color)
                        .size(7.dp)
                )
            }
        }
    }

}

@Composable
private fun Image(images: List<String>, page: Int) {
    AsyncImage(
        model = images[page],
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

fun getColorIndicatorItem(position: Int, currentPosition: Int): Color {
    val distance = max(position, currentPosition) - min(position, currentPosition)
    return when (distance) {
        0 -> Color.Black
        1 -> Color.Black.copy(alpha = 0.22f)
        2 -> Color.Black.copy(alpha = 0.17f)
        3 -> Color.Black.copy(alpha = 0.10f)
        else -> Color.Black.copy(alpha = 0.05f)
    }

}

@Preview
@Composable
fun ImagePagerPreview() {
    ImagePager(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        images = listOf("", "", "", "", "", "", "")
    )
}