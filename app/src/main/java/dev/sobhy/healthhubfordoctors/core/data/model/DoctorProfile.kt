package dev.sobhy.healthhubfordoctors.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.sobhy.healthhubfordoctors.clinicfeature.data.response.GetClinicResponse

@Entity("doctor_profile")
data class DoctorProfile(
    @PrimaryKey
    val uid: String,
    val name: String,
    val birthDate: String,
    val phoneNumber: String,
    val email: String,
    val gender: String,
    val imgPath: String?,
    val specialty: String,
    val profTitle: String,
    val rating: Double,
    val clinics: List<GetClinicResponse>,
)

class Converters {
    @TypeConverter
    fun fromClinicList(value: List<GetClinicResponse>): String {
        val type = object : TypeToken<List<GetClinicResponse>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toClinicList(value: String): List<GetClinicResponse> {
        val type = object : TypeToken<List<GetClinicResponse>>() {}.type
        return Gson().fromJson(value, type)
    }
}
