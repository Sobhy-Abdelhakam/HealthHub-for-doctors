package dev.sobhy.healthhubfordoctors.profilefeature.presentation.profile

import android.net.Uri

sealed class ProfileUiEvent {
    data class ChangeProfileImage(val image: Uri?) : ProfileUiEvent()
}
