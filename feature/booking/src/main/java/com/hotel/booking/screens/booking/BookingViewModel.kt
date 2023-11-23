package com.hotel.booking.screens.booking

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotel.hotel.model.BookingData
import com.hotel.hotel.repositories.BookingRepository
import com.hotel.ui.component.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class BuyerData(
    val phone: String = "",
    val email: String = "",
    val mailIsValid: Boolean = true,
    val phoneIsValid: Boolean = true,
)

@Stable
data class TouristData(
    val name: String = "",
    val surname: String = "",
    val birth: String = "",
    val citizenship: String = "",
    val passport: String = "",
    val passportDate: String = "",
    val isError: Boolean = false,
) {
    fun validTouristData(): Boolean {
        return name.isNotBlank()
                && surname.isNotBlank()
                && birth.isNotBlank()
                && citizenship.isNotBlank()
                && passport.isNotBlank()
                && passportDate.isNotBlank()
    }
}

@Stable
data class TouristDataState(
    val list: ImmutableList<TouristData> = persistentListOf(TouristData()),
    val isError: Boolean = false,
)

@Stable
data class BookingState(
    val bookingData: BookingData = BookingData(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class BookingViewModel @Inject constructor(private val bookingRepository: BookingRepository) :
    ViewModel() {
    private val _bookingData: MutableStateFlow<BookingState> = MutableStateFlow(
        BookingState()
    )
    val state = _bookingData.asStateFlow()

    private val _touristDataState: MutableStateFlow<TouristDataState> = MutableStateFlow(
        TouristDataState()
    )
    val touristState = _touristDataState.asStateFlow()

    private val _buyerState: MutableStateFlow<BuyerData> = MutableStateFlow(BuyerData())
    val buyerState = _buyerState.asStateFlow()

    private val _payEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val payEvent = _payEvent.asSharedFlow()

    init {
        getHotelInfo()
    }

    private fun getHotelInfo() {
        _bookingData.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = bookingRepository.getBookingInfo()
            if (result.isSuccess) {
                _bookingData.update {
                    it.copy(
                        bookingData = result.getOrDefault(BookingData()),
                        isLoading = false,
                        error = null,
                    )
                }
            } else {
                _bookingData.update { it.copy(error = result.exceptionOrNull()?.message) }
            }
        }
    }

    fun onChangeNumber(str: String) {
        _buyerState.update {
            it.copy(phone = str)
        }
    }

    var mailJob: Job? = null

    fun onChangeMail(str: String) {
        _buyerState.update {
            it.copy(email = str)
        }

        mailJob = viewModelScope.launch {
            mailJob?.cancel()
            _buyerState.update {
                it.copy(mailIsValid = true)
            }
            delay(1000)
            val isValid = str.isValidEmail()
            _buyerState.update {
                it.copy(mailIsValid = isValid)
            }
        }
    }

    fun onAddTourist() {
        _touristDataState.update {
            it.copy(
                (it.list + TouristData()).toPersistentList()
            )

        }
    }

    fun onChangeTouristData(index: Int, data: TouristData) {
        _touristDataState.update {
            it.copy(
                it.list.mapIndexed { i, d ->
                    if (i == index) data else d
                }.toPersistentList()
            )
        }
    }

    fun onPayClick() {
        viewModelScope.launch {
            _touristDataState.update { touristData ->
                val result = touristData.list.map {
                    it.copy(
                        isError = !it.validTouristData()
                    )
                }

                if (!result.any { it.isError } && buyerState.value.phone.isNotBlank() && buyerState.value.email.isValidEmail()) {
                    _payEvent.emit(Unit)
                }

                return@update touristData.copy(
                    result.toPersistentList(),
                    result.filter { it.isError }.size > 0
                )
            }

            _buyerState.update {
                it.copy(
                    phoneIsValid = it.phone.isNotBlank(),
                    mailIsValid = it.email.isValidEmail()
                )
            }
        }
    }
}