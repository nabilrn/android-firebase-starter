package com.example.androidfirebasestarter.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidfirebasestarter.data.preferences.AuthPreferences
import com.example.androidfirebasestarter.data.repository.AuthRepository
import com.example.androidfirebasestarter.presentation.home.HomeViewModel
import com.example.androidfirebasestarter.presentation.login.LoginViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val authPreferences by lazy { AuthPreferences(context) }
    private val authRepository by lazy { AuthRepository(context, authPreferences) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginViewModel::class.java -> LoginViewModel(authRepository) as T
            HomeViewModel::class.java -> HomeViewModel(authRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
