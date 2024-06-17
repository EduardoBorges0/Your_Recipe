package com.app.receitas.viewModel

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.receitas.view.UsernameScreen
import com.app.receitas.view.navigations.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val showAlertDialog = mutableStateOf(false)
    val alertDialogMessage = mutableStateOf("")

    fun signUpOrSignIn(activity: ComponentActivity, email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                startNextActivityUsernameScreen(activity)
            } catch (e: Exception) {
                if (e is FirebaseAuthUserCollisionException) {
                    try {
                        val signInResult = auth.signInWithEmailAndPassword(email, password).await()
                        if (signInResult.user != null) {
                            startNextActivityMainActivity(activity)
                        } else {
                            handleSignUpFailure(activity, e)
                        }
                    } catch (signInException: Exception) {
                        handleSignUpFailure(activity, signInException)
                    }
                } else {
                    handleSignUpFailure(activity, e)
                }
            }
        }
    }

    private fun startNextActivityUsernameScreen(activity: ComponentActivity) {
        val intent = Intent(activity, UsernameScreen::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
    private fun startNextActivityMainActivity(activity: ComponentActivity) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    private fun handleSignUpFailure(activity: ComponentActivity, e: Exception) {
        when (e) {
            is FirebaseAuthUserCollisionException -> {
                Toast.makeText(activity, "User with this email already exists.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(activity, "Operation failed: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Operation failed: ${e.message}", e)
            }
        }
    }
     fun username(activity: ComponentActivity, username : String){
        val user = Firebase.auth.currentUser
        user?.let {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
            it.updateProfile(profileUpdates)
            startNextActivityMainActivity(activity)
        }
    }
    fun ChangePassword(activity: ComponentActivity, email : String){
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            alertDialogMessage.value = "Foi enviado para seu email um link para trocar sua senha."
            showAlertDialog.value = true
        }.addOnFailureListener {
            alertDialogMessage.value = "Falha ao enviar email para troca de senha: ${it.message}"
            showAlertDialog.value = true
        }
    }
}

