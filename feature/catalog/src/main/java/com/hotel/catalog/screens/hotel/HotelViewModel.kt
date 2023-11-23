package com.hotel.catalog.screens.hotel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotel.hotel.model.Hotel
import com.hotel.hotel.repositories.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HotelState {
    data class Success(val hotel: Hotel) : HotelState()
    data class Failure(val message: String) : HotelState()
    data object Loading : HotelState()
    data object Init : HotelState()
}

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<HotelState> = MutableStateFlow(HotelState.Init)
    val state = _state.asStateFlow()

    init {
        getHotelInfo()
    }

    fun getHotelInfo() {
        _state.value = HotelState.Loading
        viewModelScope.launch {
            val result = catalogRepository.getHotelInfo()
            if (result.isSuccess) {
                Log.i("Hotel", "getHotelInfo: ${(result.getOrDefault(Hotel()))}")
                _state.value = HotelState.Success(result.getOrDefault(Hotel()))
            } else {
                _state.value =
                    HotelState.Failure(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}