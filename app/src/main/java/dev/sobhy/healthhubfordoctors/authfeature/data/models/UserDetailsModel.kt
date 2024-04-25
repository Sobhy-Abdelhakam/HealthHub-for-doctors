package dev.sobhy.healthhubfordoctors.authfeature.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserDetailsModel(
    val id: String? = null,
    val name: String? = null,
    val image: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val gender: String? = null,
    val dateOfBirth: Long? = null,
    val specialization: String? = null,
    val professionalTitle: String? = null,
    val rate: Int? = null,
    val createdAt: Long? = null,
) : Parcelable
