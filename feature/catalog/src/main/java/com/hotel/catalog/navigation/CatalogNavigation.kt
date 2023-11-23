package com.hotel.catalog.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hotel.catalog.screens.hotel.HotelScreen
import com.hotel.catalog.screens.hotel.HotelViewModel
import com.hotel.catalog.screens.rooms.RoomsScreen
import com.hotel.catalog.screens.rooms.RoomsViewModel
import com.hotel.hotel.model.Room

enum class CatalogRoutes(val route: String) {
    Root("catalog"), Hotel("hotel"), Rooms("rooms")


}

fun NavGraphBuilder.catalogNavigation(
    onClickSelectRoom: (String) -> Unit,
    onClickRoom: (Room) -> Unit,
    onBackClick: () -> Unit
) {
    navigation(route = CatalogRoutes.Root.route, startDestination = CatalogRoutes.Hotel.route) {
        composable(route = CatalogRoutes.Hotel.route) {
            val viewModel = hiltViewModel<HotelViewModel>()
            HotelScreen(viewModel, onClickSelectRoom = onClickSelectRoom)
        }
        composable(route = CatalogRoutes.Rooms.route + "/{hotelName}") { backStackEntry ->
            val viewModel = hiltViewModel<RoomsViewModel>()
            RoomsScreen(
                viewModel,
                hotelName = backStackEntry.arguments?.getString("hotelName") ?: "",
                onClickRoom = onClickRoom,
                onBackClick
            )
        }
    }

}