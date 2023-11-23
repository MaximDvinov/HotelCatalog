package com.hotel.hotel.model

import com.hotel.network.model.NetworkBookingData

data class BookingData(
    val arrivalCountry: String? = null,
    val departure: String? = null,
    val fuelCharge: Int? = null,
    val horating: Int? = null,
    val hotelAddress: String? = null,
    val hotelName: String? = null,
    val id: Int? = null,
    val numberOfNights: Int? = null,
    val nutrition: String? = null,
    val ratingName: String? = null,
    val room: String? = null,
    val serviceCharge: Int? = null,
    val tourDateStart: String? = null,
    val tourDateStop: String? = null,
    val tourPrice: Int? = null,
)

internal fun NetworkBookingData.toBookingData() = BookingData(
    arrivalCountry = arrivalCountry,
    departure = departure,
    fuelCharge = fuelCharge,
    horating = horating,
    hotelAddress = hotelAddress,
    hotelName = hotelName,
    id = id,
    numberOfNights = numberOfNights,
    nutrition = nutrition,
    ratingName = ratingName,
    room = room,
    serviceCharge = serviceCharge,
    tourDateStart = tourDateStart,
    tourDateStop = tourDateStop,
    tourPrice = tourPrice
)