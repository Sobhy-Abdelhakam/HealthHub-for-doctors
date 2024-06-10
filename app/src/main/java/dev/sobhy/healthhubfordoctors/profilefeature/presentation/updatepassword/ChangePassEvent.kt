package dev.sobhy.healthhubfordoctors.profilefeature.presentation.updatepassword

sealed class ChangePassEvent {
    data class CurrentPassChange(val currentPass: String) : ChangePassEvent()

    data class NewPassChange(val newPass: String) : ChangePassEvent()

    data class ConfirmPassChange(val confirmPass: String) : ChangePassEvent()

    data object SavePass : ChangePassEvent()
}
