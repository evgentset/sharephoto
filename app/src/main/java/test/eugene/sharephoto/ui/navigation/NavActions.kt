package test.eugene.sharephoto.ui.navigation

import androidx.navigation.NavController
import test.eugene.sharephoto.feature.photoitem.ui.navigation.ScreenPhoto

class NavActions(private val navController: NavController) {

    fun navigateToPhotoDetail(photoId: String) {
        navController.navigate("${ScreenPhoto.PhotoDetail.route}/$photoId")
    }
}