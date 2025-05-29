package com.example.recipefinder.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import com.example.recipefinder.ui.screens.*
import com.example.recipefinder.viewmodel.MealViewModel
import com.example.recipefinder.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    mealViewModel: MealViewModel,
    settingsViewModel: SettingsViewModel
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val items = listOf(
        DrawerItem("Search", Icons.Filled.Search, "search"),
        DrawerItem("Favorites", Icons.Filled.Star, "favorites"),
        DrawerItem("Settings", Icons.Filled.Settings, "settings")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Recipe Finder",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                items.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.label) },
                        selected = navController.currentBackStackEntryAsState().value?.destination?.route == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo("search") { inclusive = false }
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Recipe Finder") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "search",
                modifier = Modifier.padding(padding)
            ) {
                composable("search") { SearchScreen(navController, mealViewModel) }
                composable("detail/{mealId}") {
                    val mealId = it.arguments?.getString("mealId") ?: ""
                    DetailScreen(mealId, mealViewModel)
                }
                composable("favorites") {
                    FavoriteScreen(viewModel = mealViewModel, navController = navController)
                }

                composable("settings") {
                    SettingsScreen(
                        settingsViewModel = settingsViewModel,
                        mealViewModel = mealViewModel
                    )
                }

            }
        }
    }
}

data class DrawerItem(val label: String, val icon: ImageVector, val route: String)
