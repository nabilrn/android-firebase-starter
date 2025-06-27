package com.example.androidfirebasestarter.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.androidfirebasestarter.BuildConfig
import com.example.androidfirebasestarter.data.preferences.AuthPreferences
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
    object Loading : AuthResult()
}

data class User(
    val name: String,
    val email: String
)

class AuthRepository(
    private val context: Context,
    private val authPreferences: AuthPreferences
) {
    private val auth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)

    val isLoggedIn: Flow<Boolean> = authPreferences.isLoggedIn
    val userName: Flow<String> = authPreferences.userName
    val userEmail: Flow<String> = authPreferences.userEmail

    fun getCurrentUser(): User? {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            User(
                name = currentUser.displayName ?: "",
                email = currentUser.email ?: ""
            )
        } else null
    }

    suspend fun signInWithGoogle(): AuthResult {
        return try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )

            val credential = result.credential

            when (credential.type) {
                GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

                    auth.signInWithCredential(firebaseCredential).await()

                    val user = getCurrentUser()
                    if (user != null) {
                        authPreferences.saveUserData(user.name, user.email)
                    }

                    AuthResult.Success
                }
                else -> {
                    AuthResult.Error("Unexpected credential type: ${credential.type}")
                }
            }
        } catch (e: GetCredentialException) {
            AuthResult.Error("Sign in failed: ${e.message}")
        } catch (e: GoogleIdTokenParsingException) {
            AuthResult.Error("Invalid ID token response: ${e.message}")
        } catch (e: Exception) {
            AuthResult.Error("Sign in failed: ${e.message}")
        }
    }

    suspend fun signOut(): AuthResult {
        return try {
            auth.signOut()
            authPreferences.clearUserData()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error("Sign out failed: ${e.message}")
        }
    }
}
