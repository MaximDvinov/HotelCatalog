package com.hotel.network.retrofit

import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitNetwork @Inject constructor(
    gson: GsonConverterFactory,
    okHttpClient: OkHttpClient,
) {
    private val networkApi = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(gson)
        .baseUrl("https://run.mocky.io/v3/")
        .build()
        .create(RetrofitApi::class.java)

    suspend fun getHotelInfo() = networkApi.getHotelInfo()
    suspend fun getRooms() = networkApi.getRoomsList()
    suspend fun getBookingInfo() = networkApi.getBookingInfo()
}