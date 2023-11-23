package com.hotel.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRooms(
    @SerializedName("rooms")
    val rooms: List<NetworkRoom?>? = null,
)

data class NetworkRoom(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image_urls")
    val imageUrls: List<String?>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("peculiarities")
    val peculiarities: List<String?>? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("price_per")
    val pricePer: String? = null,
)