package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.AddProfileRequest
import com.ipb.remangokbabel.model.response.GetKecamatanResponseItem
import com.ipb.remangokbabel.model.response.GetKelurahanResponseItem
import com.ipb.remangokbabel.model.response.GetProfileResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : BaseViewModel() {

//    val addProfileResponse = MutableSharedFlow<StatusMessageResponse>()
//    val updateProfileResponse = MutableSharedFlow<StatusMessageResponse>()
//    val deleteProfileResponse = MutableSharedFlow<StatusMessageResponse>()
//    val logoutResponse = MutableSharedFlow<StatusMessageResponse>()

    private val _getProfileState: MutableStateFlow<GetProfileResponse?> =
        MutableStateFlow(null as GetProfileResponse?)
    private val _getKecamatanState: MutableStateFlow<List<GetKecamatanResponseItem>> =
        MutableStateFlow(listOf())
    private val _getKelurahanState: MutableStateFlow<List<GetKelurahanResponseItem>> =
        MutableStateFlow(listOf())
    private val _logoutState: MutableStateFlow<StatusMessageResponse?> =
        MutableStateFlow(null as StatusMessageResponse?)

    val getProfileState: StateFlow<GetProfileResponse?>
        get() = _getProfileState
    val getKecamatanState: StateFlow<List<GetKecamatanResponseItem>>
        get() = _getKecamatanState
    val getKelurahanState: StateFlow<List<GetKelurahanResponseItem>>
        get() = _getKelurahanState
    val logoutState: StateFlow<StatusMessageResponse?>
        get() = _logoutState

    fun getProfile() {
        viewModelScope.launch {
            setLoading(true)
            try {
                _getProfileState.value = repository.getProfile()
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun addProfile(data: AddProfileRequest) {
//        viewModelScope.launch {
//            showLoading.emit(true)
//            try {
//                addProfileResponse.emit(repository.addProfile(data))
//            } catch (e: Exception) {
//                errorResponse.emit(handleException(e))
//            }
//            showLoading.emit(false)
//        }
    }

    fun updateProfile(id: String, data: AddProfileRequest) {
//        viewModelScope.launch {
//            showLoading.emit(true)
//            try {
//                updateProfileResponse.emit(repository.updateProfile(id, data))
//            } catch (e: Exception) {
//                errorResponse.emit(handleException(e))
//            }
//            showLoading.emit(false)
//        }
    }

    fun deleteProfile(id: String) {
//        viewModelScope.launch {
//            showLoading.emit(true)
//            try {
//                deleteProfileResponse.emit(repository.deleteProfile(id))
//            } catch (e: Exception) {
//                errorResponse.emit(handleException(e))
//            }
//            showLoading.emit(false)
//        }
    }

    fun getKecamatan(id: String) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _getKecamatanState.value = repository.getKecamatan(id)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun getKelurahan(id: String) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _getKelurahanState.value = repository.getKelurahan(id)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            setLoading(true)
            try {
                _logoutState.value = repository.logout()
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }
}