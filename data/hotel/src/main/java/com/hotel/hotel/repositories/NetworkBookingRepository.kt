package com.hotel.hotel.repositories

import android.util.Log
import com.hotel.hotel.model.BookingData
import com.hotel.hotel.model.toBookingData
import com.hotel.network.retrofit.RetrofitNetwork
import javax.inject.Inject

class NetworkBookingRepository @Inject constructor(private val retrofitNetwork: RetrofitNetwork) :
    BookingRepository {
    override suspend fun getBookingInfo(): Result<BookingData> = try {
        Result.success(retrofitNetwork.getBookingInfo().toBookingData())
    } catch (e: Throwable) {
        e.message?.let { Log.e("getBookingInfo", it) }
        Result.failure(e)
    }

}