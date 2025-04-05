package test.eugene.sharephoto.feature.photoitem.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class ScreenPhoto(val route: String) {
    object PhotoFeed : ScreenPhoto("photo_feed")

    object PhotoDetail : ScreenPhoto("photo_detail") {
        const val PHOTO_ID_ARG = "photoId"
        val routeWithArgs = "$route/{$PHOTO_ID_ARG}"
        val arguments = listOf(
            navArgument(PHOTO_ID_ARG) { type = NavType.StringType }
        )
    }
}