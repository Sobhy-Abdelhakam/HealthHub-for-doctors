package dev.sobhy.healthhubfordoctors.profilefeature.presentation.updatepassword

data class ChangePassState(
    val currentPass: String = "",
    val newPass: String = "",
    val confirmPass: String = "",
    val isButtonEnable: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
)
