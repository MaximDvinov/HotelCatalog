package com.hotel.network.retrofit

import com.hotel.network.model.NetworkBookingData
import com.hotel.network.model.NetworkHotel
import com.hotel.network.model.NetworkRoom
import com.hotel.network.model.NetworkRooms
import retrofit2.http.GET

interface RetrofitApi {
    @GET("d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotelInfo(): NetworkHotel

    @GET("8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getRoomsList(): NetworkRooms

    @GET("63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getBookingInfo(): NetworkBookingData
}