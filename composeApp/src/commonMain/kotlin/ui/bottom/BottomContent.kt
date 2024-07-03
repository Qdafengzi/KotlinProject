package ui.bottom

import HomeListViewModel
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.kmpapp.Res
import org.example.kmpapp.compose_camera
import org.example.kmpapp.compose_home
import org.example.kmpapp.compose_profile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ui.home.MainContent
import ui.camera.CameraScreen
import ui.profile.ProfileScreen


sealed class BottomNavItem(var title: String, var icon: DrawableResource, var route: String) {
    data object Home : BottomNavItem("Home", Res.drawable.compose_home, "home")
    data object Camera : BottomNavItem("Camera", Res.drawable.compose_camera, "Camera")
    data object Profile : BottomNavItem("Profile", Res.drawable.compose_profile, "profile")
}

@Composable
fun BottomContent(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Camera,
        BottomNavItem.Profile
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        elevation = 5.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        tint = if (currentRoute == item.route) Color(0xFF0099a1) else Color.Gray,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = if (currentRoute == item.route) FontWeight.Medium else FontWeight.Normal,
                        color = if (currentRoute == item.route) Color(0xFF0099a1) else Color.Gray,
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Navigate to the selected route and pop up to the start destination
                        navController.graph.findStartDestination().route?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun NavHostContainer(navController: NavHostController,homeViewModel: HomeListViewModel, modifier: Modifier = Modifier) {
    //val animationSpec = tween<IntOffset>(70)
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier,
//        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec) },
//        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec) },
//        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec) },
//        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec) }
        ) {
        composable(
            route = BottomNavItem.Home.route,
            enterTransition = { fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) },
            exitTransition = { fadeOut() },
            popEnterTransition = null,
            popExitTransition = null,

            ) {
            MainContent(homeViewModel)
        }
        composable(
            route = BottomNavItem.Camera.route,
            enterTransition = { fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) },
            exitTransition = { fadeOut() },
            popEnterTransition = null,
            popExitTransition = null,
        ) {
            CameraScreen()
        }
        composable(
            route = BottomNavItem.Profile.route,
            enterTransition = { fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) },
            exitTransition = { fadeOut() },
            popEnterTransition = null,
            popExitTransition = null,
        ) {
            ProfileScreen()
        }
    }
}