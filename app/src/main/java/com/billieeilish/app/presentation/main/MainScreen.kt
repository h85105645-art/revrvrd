package com.billieeilish.app.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.billieeilish.app.R
import com.billieeilish.app.presentation.anime.AnimeScreen
import com.billieeilish.app.presentation.favorites.FavoritesScreen
import com.billieeilish.app.presentation.movies.MoviesScreen
import com.billieeilish.app.presentation.profile.ProfileScreen
import com.billieeilish.app.presentation.series.SeriesScreen
import com.billieeilish.app.presentation.tv.TvChannelsScreen

/**
 * Main screen with bottom navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "movies",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("movies") {
                MoviesScreen()
            }
            composable("series") {
                SeriesScreen()
            }
            composable("anime") {
                AnimeScreen()
            }
            composable("tv") {
                TvChannelsScreen()
            }
            composable("favorites") {
                FavoritesScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
        }
    }
}

/**
 * Bottom navigation items
 */
data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

private val bottomNavItems = listOf(
    BottomNavItem(
        title = "أفلام",
        icon = Icons.Default.Movie,
        route = "movies"
    ),
    BottomNavItem(
        title = "مسلسلات",
        icon = Icons.Default.Tv,
        route = "series"
    ),
    BottomNavItem(
        title = "أنمي",
        icon = Icons.Default.Animation,
        route = "anime"
    ),
    BottomNavItem(
        title = "قنوات",
        icon = Icons.Default.LiveTv,
        route = "tv"
    ),
    BottomNavItem(
        title = "مفضلة",
        icon = Icons.Default.Favorite,
        route = "favorites"
    ),
    BottomNavItem(
        title = "الملف الشخصي",
        icon = Icons.Default.Person,
        route = "profile"
    )
)