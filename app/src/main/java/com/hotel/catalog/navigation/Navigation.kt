package com.hotel.catalog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hotel.booking.navigation.BookingNavigationRoutes
import com.hotel.booking.navigation.bookingNavigation

@Composable
fun NavigationContainer(navController: NavHostController, modifier: Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CatalogRoutes.Root.route,
    ) {
        catalogNavigation(
            onClickRoom = {
                navController.navigate(BookingNavigationRoutes.Booking.route)
            },
            onClickSelectRoom = { navController.navigate(CatalogRoutes.Rooms.route + "/$it") },
            onBackClick = navController::popBackStack
        )

        bookingNavigation(
            onClickPay = {
                navController.navigate(BookingNavigationRoutes.PaymentResult.route)
            },
            onConfirmClick = {
                navController.navigate(CatalogRoutes.Root.route) {
                    popUpTo(0)
                }
            },
            onBackClick = navController::popBackStack
        )
    }
}