package com.hotel.hotel.repositories

import com.hotel.hotel.model.Hotel
import com.hotel.hotel.model.Room

interface CatalogRepository {
    suspend fun getHotelInfo(): Result<Hotel>
    suspend fun getRooms(): Result<List<Room>>
}