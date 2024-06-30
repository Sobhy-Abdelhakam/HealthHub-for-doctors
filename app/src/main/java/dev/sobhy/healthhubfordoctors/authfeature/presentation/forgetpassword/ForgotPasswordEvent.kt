package dev.sobhy.healthhubfordoctors.authfeature.presentation.forgetpassword

sealed class ForgotPasswordEvent {
    data class EnterEmail(val email: String) : ForgotPasswordEvent()

    data class EnterOtp(val otp: String) : ForgotPasswordEvent()

    data class EnterNewPassword(val newPassword: String) : ForgotPasswordEvent()

    object SendOtp : ForgotPasswordEvent()

    object SubmitNewPassword : ForgotPasswordEvent()
}
