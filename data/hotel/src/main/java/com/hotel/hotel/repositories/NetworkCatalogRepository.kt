package com.hotel.hotel.repositories

import android.util.Log
import com.hotel.hotel.model.Room
import com.hotel.hotel.model.toHotel
import com.hotel.hotel.model.toRoom
import com.hotel.network.retrofit.RetrofitNetwork
import javax.inject.Inject

class NetworkCatalogRepository @Inject constructor(private val retrofitNetwork: RetrofitNetwork) :
    CatalogRepository {
    override suspend fun getHotelInfo() = try {
        val result = retrofitNetwork.getHotelInfo()
        Log.i("NetworkCatalogRepository", "getHotelInfo: $result")
        Result.success(result.toHotel())
    } catch (e: Throwable) {
        e.message?.let { Log.e("getHotelInfo", it) }
        Result.failure(e)

    }

    override suspend fun getRooms(): Result<List<Room>> = try {
        Result.success(retrofitNetwork.getRooms().rooms?.mapNotNull { it?.toRoom() }.orEmpty())
    } catch (e: Throwable) {
        e.message?.let { Log.e("getRooms", it) }
        Result.failure(e)
    }
}