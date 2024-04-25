package dev.sobhy.healthhubfordoctors.authfeature.data.datasource

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dev.sobhy.healthhubfordoctors.authfeature.data.models.UserDetailsModel

class FireStoreDataSourceImpl(
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance(),
) : FireStoreDataSource {
    override suspend fun saveUserData(
        user: FirebaseUser,
        userData: UserDetailsModel,
    ) {
        val userRef = fireStore.collection("users").document(user.uid)
        val temp = userRef.set(userData)
    }
}
