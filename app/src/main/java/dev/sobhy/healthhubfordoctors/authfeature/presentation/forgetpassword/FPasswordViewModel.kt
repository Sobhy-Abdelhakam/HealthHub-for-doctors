package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FPasswordViewModel : ViewModel() {
    var email by mutableStateOf("")

    fun onEmailChange(email: String) {
        this.email = email
    }

    fun sendEmail()  {
        // TODO: send email to reset password
    }
}
