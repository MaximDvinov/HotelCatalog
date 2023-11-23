package com.hotel.hotel.model

import com.hotel.network.model.NetworkAboutTheHotel
import com.hotel.network.model.NetworkHotel

data class Hotel(
    val aboutTheHotel: AboutTheHotel? = null,
    val address: String? = null,
    val id: Int? = null,
    val imageUrls: List<String> = listOf(),
    val minimalPrice: Int? = null,
    val name: String? = null,
    val priceForIt: String? = null,
    val rating: Int? = null,
    val ratingName: String? = null,
)

internal fun NetworkHotel.toHotel() = Hotel(
    aboutTheHotel = aboutTheHotel?.toAboutTheHotel(),
    address = address,
    id = id,
    imageUrls = imageUrls?.mapNotNull { it }.orEmpty(),
    minimalPrice = minimalPrice,
    name = name,
    priceForIt = priceForIt,
    rating = rating,
    ratingName = ratingName
)

data class AboutTheHotel(
    val description: String? = null,
    val peculiarities: List<String>? = null,
)

internal fun NetworkAboutTheHotel.toAboutTheHotel() = AboutTheHotel(
    description = description,
    peculiarities = peculiarities?.mapNotNull { it }
)