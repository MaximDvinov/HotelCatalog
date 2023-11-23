package com.hotel.booking.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hotel.booking.screens.booking.BookingScreen
import com.hotel.booking.screens.booking.BookingViewModel
import com.hotel.booking.screens.paymentresult.PaymentResultScreen

enum class BookingNavigationRoutes(val route: String) {
    Root("booking_root"),
    Booking("booking"),
    PaymentResult("payment_result")
}

fun NavGraphBuilder.bookingNavigation(
    onClickPay: () -> Unit,
    onConfirmClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    navigation(
        route = BookingNavigationRoutes.Root.route,
        startDestination = BookingNavigationRoutes.Booking.route
    ) {
        composable(route = BookingNavigationRoutes.Booking.route) {
            val viewModel = hiltViewModel<BookingViewModel>()
            BookingScreen(
                viewModel,
                onClickPay = onClickPay,
                onBackClick = onBackClick
            )
        }
        composable(route = BookingNavigationRoutes.PaymentResult.route) {
            PaymentResultScreen(
                onConfirmClick = onConfirmClick,
                onBackClick = onBackClick
            )
        }
    }
}