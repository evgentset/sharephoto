package test.eugene.sharephoto.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import test.eugene.sharephoto.feature.photoitem.ui.navigation.ScreenPhoto
import test.eugene.sharephoto.feature.photoitem.ui.photodetails.PhotoDetailScreen
import test.eugene.sharephoto.feature.photoitem.ui.photofeed.PhotoFeedScreen
import test.eugene.sharephoto.ui.navigation.NavActions

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    val actions = remember(navController) { NavActions(navController) }

    NavHost(
        navController = navController,
        startDestination = ScreenPhoto.PhotoFeed.route,
    ) {

        composable(ScreenPhoto.PhotoFeed.route) {
            PhotoFeedScreen(
                navController = navController,
                onPhotoClick = { photo -> actions.navigateToPhotoDetail(photo.id) }
            )
        }

        composable(
            route = ScreenPhoto.PhotoDetail.routeWithArgs,
            arguments = ScreenPhoto.PhotoDetail.arguments
        ) { backStackEntry ->
            PhotoDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }

}
