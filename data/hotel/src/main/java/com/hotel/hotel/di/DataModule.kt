package com.hotel.hotel.di

import com.hotel.hotel.repositories.BookingRepository
import com.hotel.hotel.repositories.CatalogRepository
import com.hotel.hotel.repositories.NetworkBookingRepository
import com.hotel.hotel.repositories.NetworkCatalogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindBookingRepository(bookingRepositoryImpl: NetworkBookingRepository): BookingRepository

    @Binds
    fun bindCatalogRepository(catalogRepositoryImpl: NetworkCatalogRepository): CatalogRepository
}