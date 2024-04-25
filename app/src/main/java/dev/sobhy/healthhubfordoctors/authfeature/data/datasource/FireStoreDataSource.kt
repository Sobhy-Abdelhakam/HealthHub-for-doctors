package dev.sobhy.healthhubfordoctors.authfeature.data.datasource

import com.google.firebase.auth.FirebaseUser
import dev.sobhy.healthhubfordoctors.authfeature.data.models.UserDetailsModel

interface FireStoreDataSource {
    suspend fun saveUserData(
        user: FirebaseUser,
        userData: UserDetailsModel,
    )
}
