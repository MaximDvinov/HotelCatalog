package com.hotel.hotel.model

import com.hotel.network.model.NetworkRoom

data class Room(
    val id: Int? = null,
    val imageUrls: List<String>? = null,
    val name: String? = null,
    val peculiarities: List<String>? = null,
    val price: Int? = null,
    val pricePer: String? = null,
)

internal fun NetworkRoom.toRoom() = Room(
    id = id,
    imageUrls = imageUrls?.mapNotNull { it },
    name = name,
    peculiarities = peculiarities?.mapNotNull { it },
    price = price,
    pricePer = pricePer
)