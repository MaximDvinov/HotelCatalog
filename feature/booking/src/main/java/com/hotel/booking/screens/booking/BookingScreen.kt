package com.hotel.booking.screens.booking

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.hotel.booking.R
import com.hotel.hotel.model.BookingData
import com.hotel.ui.component.CustomTextField
import com.hotel.ui.component.HotelHeader
import com.hotel.ui.component.LoadScreen
import com.hotel.ui.component.PhoneVisualTransformation
import com.hotel.ui.component.PrimaryButton
import com.hotel.ui.component.format
import com.hotel.ui.theme.Headline1
import com.hotel.ui.theme.subtitleLight
import com.hotel.ui.theme.title

@Composable
fun BookingScreen(viewModel: BookingViewModel, onClickPay: () -> Unit, onBackClick: () -> Unit) {
    val bookingState by viewModel.state.collectAsState()
    val touristState by viewModel.touristState.collectAsState()
    val buyerState by viewModel.buyerState.collectAsState()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.payEvent.collect {
                onClickPay()
            }
        }
    }

    Column {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painterResource(id = R.drawable.arrow_left),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.booking),
                style = Headline1,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp),
                textAlign = TextAlign.Center
            )
        }

        when {
            bookingState.isLoading -> {
                LoadScreen(modifier = Modifier.fillMaxSize())
            }

            bookingState.error != null -> {
                Text(
                    text = bookingState.error!!,
                    style = subtitleLight,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            else -> {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    HotelHeader(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(16.dp),
                        rating = bookingState.bookingData.horating,
                        ratingName = bookingState.bookingData.ratingName,
                        hotelName = bookingState.bookingData.hotelName ?: "",
                        hotelAddress = bookingState.bookingData.hotelAddress ?: ""
                    )

                    Information(bookingState.bookingData)

                    InformationBuyer(
                        buyerState,
                        viewModel::onChangeNumber,
                        viewModel::onChangeMail,
                    )

                    TouristList(
                        touristState.list,
                        onChangeTourist = viewModel::onChangeTouristData,
                        touristState.isError
                    )

                    AddTourist(onClickAdd = viewModel::onAddTourist)

                    TotalAmount(
                        bookingState.bookingData,
                    )

                    PayButton(
                        onClickPay = viewModel::onPayClick,
                        getTotalPrice(bookingState.bookingData).format()
                    )
                }
            }
        }

    }


}


@Composable
private fun getTotalPrice(bookingData: BookingData) =
    ((bookingData.tourPrice ?: 0) + (bookingData.fuelCharge
        ?: 0) + (bookingData.serviceCharge ?: 0))

@Composable
fun PayButton(onClickPay: () -> Unit, totalPrice: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickPay,
            text = stringResource(id = R.string.pay_with_price, totalPrice)
        )

    }
}

@Composable
fun TotalAmount(bookingData: BookingData) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.tour),
                style = subtitleLight,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${bookingData.tourPrice?.format()} ₽",
                style = subtitleLight,
                color = Color.Black,
                modifier = Modifier.width(220.dp),
                textAlign = TextAlign.End
            )
        }

        Row {
            Text(
                text = stringResource(id = R.string.fuel),
                style = subtitleLight,
                color = Color(0xFF828796),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${bookingData.fuelCharge?.format()} ₽",
                style = subtitleLight,
                color = Color.Black,
                modifier = Modifier.width(220.dp),
                textAlign = TextAlign.End
            )
        }

        Row {
            Text(
                text = stringResource(id = R.string.service),
                style = subtitleLight,
                color = Color(0xFF828796),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${bookingData.serviceCharge?.format()} ₽",
                style = subtitleLight,
                color = Color.Black,
                modifier = Modifier.width(220.dp),
                textAlign = TextAlign.End
            )
        }

        Row {

            Text(
                text = stringResource(id = R.string.to_be_paid),
                style = subtitleLight,
                color = Color(0xFF828796),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${getTotalPrice(bookingData = bookingData).format()} ₽",
                style = subtitleLight,
                color = Color.Black,
                modifier = Modifier.width(220.dp),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun AddTourist(onClickAdd: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.add_tourist),
            style = title,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onClickAdd,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun TouristList(
    touristList: List<TouristData>,
    onChangeTourist: (Int, TouristData) -> Unit,
    isError: Boolean,
) {
    touristList.forEachIndexed { index, item ->
        TouristItem(
            index = index,
            data = item,
            onChangeTourist = onChangeTourist,
            isError
        )
    }
}

@Composable
fun TouristItem(
    index: Int,
    data: TouristData,
    onChangeTourist: (Int, TouristData) -> Unit,
    isError: Boolean,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val (touristData, setTouristData) = remember { mutableStateOf(data) }
    val indexes = stringArrayResource(id = R.array.indexes)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { isExpanded = !isExpanded }
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "${getIndexText(index, indexes)} турист", style = title, modifier = Modifier
        )

        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier.clickable { isExpanded = !isExpanded },
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomTextField(
                    value = touristData.name,
                    onValueChange = {
                        val newTouristData = touristData.copy(name = it)
                        onChangeTourist(index, newTouristData)
                        setTouristData(newTouristData)
                    },
                    placeholder = { Text(stringResource(id = R.string.name)) },
                    label = { Text(stringResource(id = R.string.name)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = touristData.name.isEmpty() && isError
                )

                CustomTextField(
                    value = touristData.surname,
                    onValueChange = {
                        val newTouristData = touristData.copy(surname = it)
                        onChangeTourist(index, newTouristData)
                        setTouristData(newTouristData)
                    },
                    placeholder = { Text(stringResource(id = R.string.surname)) },
                    label = { Text(stringResource(id = R.string.surname)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = touristData.surname.isEmpty() && isError
                )

                CustomTextField(
                    value = touristData.birth,
                    onValueChange = {
                        val newTouristData = touristData.copy(birth = it)
                        onChangeTourist(index, newTouristData)
                        setTouristData(newTouristData)
                    },
                    placeholder = { Text(stringResource(id = R.string.birth)) },
                    label = { Text(stringResource(id = R.string.birth)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = touristData.birth.isEmpty() && isError
                )

                CustomTextField(
                    value = touristData.citizenship,
                    onValueChange = {
                        val newTouristData = touristData.copy(citizenship = it)
                        onChangeTourist(index, newTouristData)
                        setTouristData(newTouristData)
                    },
                    placeholder = { Text(stringResource(id = R.string.citizenship)) },
                    label = { Text(stringResource(id = R.string.citizenship)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = touristData.citizenship.isEmpty() && isError
                )

                CustomTextField(
                    value = touristData.passport,
                    onValueChange = {
                        val newTouristData = touristData.copy(passport = it)
                        onChangeTourist(index, newTouristData)
                        setTouristData(newTouristData)
                    },
                    placeholder = { Text(stringResource(id = R.string.passport_number)) },
                    label = { Text(stringResource(id = R.string.passport_number)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = touristData.passport.isEmpty() && isError
                )

                CustomTextField(
                    value = touristData.passportDate,
                    onValueChange = {
                        val newTouristData = touristData.copy(passportDate = it)
                        onChangeTourist(index, newTouristData)
                        setTouristData(newTouristData)
                    },
                    placeholder = { Text(stringResource(id = R.string.passport_date)) },
                    label = { Text(stringResource(id = R.string.passport_date)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = touristData.passportDate.isEmpty() && isError
                )
            }
        }
    }
}


fun getIndexText(index: Int, indexes: Array<String>): String {
    return indexes.getOrNull(index) ?: (index + 1).toString()
}

@Composable
fun InformationBuyer(
    buyerData: BuyerData,
    onChangeNumber: (String) -> Unit,
    onChangeMail: (String) -> Unit,
) {
    val (number, setNumber) = remember { mutableStateOf(buyerData.phone) }
    val (mail, setMail) = remember { mutableStateOf(buyerData.email) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Информация о покупателе", style = title, modifier = Modifier
        )

        val stringPattern = stringResource(id = R.string.number_pattern)
        CustomTextField(
            value = number,
            onValueChange = {
                if (it.length <= 10 || it.toIntOrNull() != null) {
                    setNumber(it)
                    onChangeNumber(it)
                }
            },
            placeholder = { Text(stringPattern) },
            label = { Text("Номер телефона") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PhoneVisualTransformation(
                stringPattern, '*'
            ),
            isError = !buyerData.phoneIsValid
        )

        CustomTextField(
            value = mail,
            onValueChange = {
                onChangeMail(it)
                setMail(it)
            },
            placeholder = { Text(stringResource(id = R.string.mail)) },
            label = { Text(stringResource(id = R.string.mail)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = !buyerData.mailIsValid
        )

    }
}


@Composable
fun Information(bookingData: BookingData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InformationItem(stringResource(id = R.string.departure), "${bookingData.departure}")
        InformationItem(stringResource(id = R.string.country), "${bookingData.arrivalCountry}")
        InformationItem(stringResource(id = R.string.date), "${bookingData.tourDateStart} – ${bookingData.tourDateStop}")
        InformationItem(stringResource(id = R.string.count_night), "${bookingData.numberOfNights} ночей")
        InformationItem(stringResource(id = R.string.hotel), "${bookingData.hotelName}")
        InformationItem(stringResource(id = R.string.room), "${bookingData.room}")
        InformationItem(stringResource(id = R.string.nutrition), "${bookingData.nutrition}")
    }
}

@Composable
private fun InformationItem(title: String, value: String) {
    Row {
        Text(
            text = title,
            style = subtitleLight,
            color = Color(0xFF828796),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = subtitleLight,
            color = Color.Black,
            modifier = Modifier.width(220.dp)
        )
    }
}
