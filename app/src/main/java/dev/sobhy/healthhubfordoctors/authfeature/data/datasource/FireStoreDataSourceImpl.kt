package dev.sobhy.healthhubfordoctors.authfeature.data.datasource

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dev.sobhy.healthhubfordoctors.authfeature.data.models.UserDetailsModel
import kotlinx.coroutines.tasks.await

class FireStoreDataSourceImpl(
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance(),
) : FireStoreDataSource {
    override suspend fun saveUserData(
        user: FirebaseUser,
        userData: UserDetailsModel,
    ) {
        val userRef = fireStore.collection("doctors").document(user.uid)
        userRef.set(userData)
    }

    override suspend fun getUserData(user: FirebaseUser): UserDetailsModel? {
        val userRef = fireStore.collection("doctors").document(user.uid)
        return userRef.get().await().toObject(UserDetailsModel::class.java)
    }
}
