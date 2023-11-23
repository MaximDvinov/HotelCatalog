package com.hotel.catalog.screens.rooms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotel.hotel.model.Room
import com.hotel.hotel.repositories.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoomsState {
    data class Success(val rooms: List<Room>) : RoomsState()
    data class Failure(val message: String) : RoomsState()
    data object Loading : RoomsState()
    data object Init : RoomsState()
}

@HiltViewModel
class RoomsViewModel @Inject constructor(private val viewModel: CatalogRepository) : ViewModel() {
    private val _state: MutableStateFlow<RoomsState> = MutableStateFlow(RoomsState.Init)
    val state = _state.asStateFlow()

    init {
        getRooms()
    }

    private fun getRooms() {
        _state.value = RoomsState.Loading
        viewModelScope.launch {
            val result = viewModel.getRooms()
            Log.i("RoomsViewModel", "getRooms: $result")
            if (result.isSuccess) {
                _state.value = RoomsState.Success(result.getOrDefault(listOf()))
            } else {
                _state.value =
                    RoomsState.Failure(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}