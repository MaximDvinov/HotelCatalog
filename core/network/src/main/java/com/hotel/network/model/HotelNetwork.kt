package com.hotel.network.model
import com.google.gson.annotations.SerializedName

data class NetworkHotel(
    @SerializedName("about_the_hotel")
    val aboutTheHotel: NetworkAboutTheHotel? = null,
    @SerializedName("adress")
    val address: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image_urls")
    val imageUrls: List<String?>? = null,
    @SerializedName("minimal_price")
    val minimalPrice: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price_for_it")
    val priceForIt: String? = null,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("rating_name")
    val ratingName: String? = null
)

data class NetworkAboutTheHotel(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("peculiarities")
    val peculiarities: List<String?>? = null
)