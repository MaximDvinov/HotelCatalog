package com.hotel.network.model
import com.google.gson.annotations.SerializedName


data class NetworkBookingData(
    @SerializedName("arrival_country")
    val arrivalCountry: String? = null,
    @SerializedName("departure")
    val departure: String? = null,
    @SerializedName("fuel_charge")
    val fuelCharge: Int? = null,
    @SerializedName("horating")
    val horating: Int? = null,
    @SerializedName("hotel_adress")
    val hotelAddress: String? = null,
    @SerializedName("hotel_name")
    val hotelName: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("number_of_nights")
    val numberOfNights: Int? = null,
    @SerializedName("nutrition")
    val nutrition: String? = null,
    @SerializedName("rating_name")
    val ratingName: String? = null,
    @SerializedName("room")
    val room: String? = null,
    @SerializedName("service_charge")
    val serviceCharge: Int? = null,
    @SerializedName("tour_date_start")
    val tourDateStart: String? = null,
    @SerializedName("tour_date_stop")
    val tourDateStop: String? = null,
    @SerializedName("tour_price")
    val tourPrice: Int? = null
)