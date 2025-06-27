package com.example.androidfirebasestarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.androidfirebasestarter.data.preferences.AuthPreferences
import com.example.androidfirebasestarter.di.ViewModelFactory
import com.example.androidfirebasestarter.navigation.AppNavigation
import com.example.androidfirebasestarter.ui.theme.AndroidFirebaseStarterTheme
import com.example.androidfirebasestarter.utils.NetworkManager

class MainActivity : ComponentActivity() {

    private lateinit var networkManager: NetworkManager
    private lateinit var authPreferences: AuthPreferences
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize dependencies
        networkManager = NetworkManager(this)
        authPreferences = AuthPreferences(this)
        viewModelFactory = ViewModelFactory(this)

        enableEdgeToEdge()
        setContent {
            AndroidFirebaseStarterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        networkManager = networkManager,
                        authPreferences = authPreferences,
                        viewModelFactory = viewModelFactory
                    )
                }
            }
        }
    }
}