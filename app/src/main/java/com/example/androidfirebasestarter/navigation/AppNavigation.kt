package com.example.androidfirebasestarter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidfirebasestarter.data.preferences.AuthPreferences
import com.example.androidfirebasestarter.di.ViewModelFactory
import com.example.androidfirebasestarter.presentation.home.HomeScreen
import com.example.androidfirebasestarter.presentation.home.HomeViewModel
import com.example.androidfirebasestarter.presentation.login.LoginScreen
import com.example.androidfirebasestarter.presentation.login.LoginViewModel
import com.example.androidfirebasestarter.utils.NetworkManager

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
}

@Composable
fun AppNavigation(
    networkManager: NetworkManager,
    authPreferences: AuthPreferences,
    viewModelFactory: ViewModelFactory,
    navController: NavHostController = rememberNavController()
) {
    val isNetworkAvailable by networkManager.isNetworkAvailable.collectAsStateWithLifecycle(initialValue = false)
    val isLoggedIn by authPreferences.isLoggedIn.collectAsStateWithLifecycle(initialValue = false)

    // Determine start destination based on auth state
    val startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(factory = viewModelFactory)
            LoginScreen(
                viewModel = loginViewModel,
                isNetworkAvailable = isNetworkAvailable,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = viewModelFactory)
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
