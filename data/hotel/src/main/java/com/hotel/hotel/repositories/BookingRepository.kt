package com.hotel.hotel.repositories

import com.hotel.hotel.model.BookingData

interface BookingRepository {
    suspend fun getBookingInfo(): Result<BookingData>
}