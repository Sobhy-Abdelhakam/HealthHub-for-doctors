package dev.sobhy.healthhubfordoctors.clinicfeature.presentation.addclinic

data class AddClinicState(
    val clinicName: String = "",
    val clinicNumber: String = "",
    val clinicAddress: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val examination: String = "",
    val followUp: String = "",
    val loading: Boolean = false,
    val isSubmitting: Boolean = false,
    val errorMessages: String? = null,
)

// data class AvailabilityUi(
//    val dayAvailable: Map<DayOfWeek, DayStateUi> = emptyMap(),
// )
//
// // data class DayStateUi(
// //    val isSwitchOn: Boolean = false,
// //    val from: LocalTime = LocalTime.of(6, 0),
// //    val to: LocalTime = LocalTime.of(6, 0),
// // )
// data class DayStateUi(
//    val isOn: MutableState<Boolean> = mutableStateOf(false),
//    val from: MutableState<LocalTime> = mutableStateOf(LocalTime.of(6, 0)),
//    val to: MutableState<LocalTime> = mutableStateOf(LocalTime.of(6, 0)),
// )
