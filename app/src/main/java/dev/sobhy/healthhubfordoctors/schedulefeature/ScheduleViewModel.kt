package dev.sobhy.healthhubfordoctors.schedulefeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sobhy.healthhubfordoctors.schedulefeature.domain.CacheInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel
    @Inject
    constructor(
        private val getDoctorProfileUseCase: CacheInfoUseCase,
    ) : ViewModel() {
        init {
            viewModelScope.launch(Dispatchers.IO) {
                getDoctorProfileUseCase()
            }
        }
    }
