package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.ProfileRequest
import com.ipb.remangokbabel.model.response.GetKabupatenKotaResponseItem
import com.ipb.remangokbabel.model.response.GetKecamatanResponseItem
import com.ipb.remangokbabel.model.response.GetKelurahanResponseItem
import com.ipb.remangokbabel.model.response.GetProfileResponse
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : BaseViewModel() {
    private val _getProfileState: MutableStateFlow<GetProfileResponse?> =
        MutableStateFlow(null as GetProfileResponse?)
    private val _getKotaKabupatenState: MutableStateFlow<List<GetKabupatenKotaResponseItem>> = MutableStateFlow(
        listOf())
    private val _getKecamatanState: MutableStateFlow<List<GetKecamatanResponseItem>> =
        MutableStateFlow(listOf())
    private val _getKelurahanState: MutableStateFlow<List<GetKelurahanResponseItem>> =
        MutableStateFlow(listOf())
    private val _updateProfileState: MutableStateFlow<StatusMessageResponse?> =
        MutableStateFlow(null as StatusMessageResponse?)
    private val _logoutState: MutableStateFlow<StatusMessageResponse?> =
        MutableStateFlow(null as StatusMessageResponse?)

    val getProfileState: StateFlow<GetProfileResponse?>
        get() = _getProfileState
    val getKotaKabupatenState : StateFlow<List<GetKabupatenKotaResponseItem>> get() = _getKotaKabupatenState
    val getKecamatanState: StateFlow<List<GetKecamatanResponseItem>>
        get() = _getKecamatanState
    val getKelurahanState: StateFlow<List<GetKelurahanResponseItem>>
        get() = _getKelurahanState
    val updateProfileState: StateFlow<StatusMessageResponse?>
        get() = _updateProfileState
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

    fun clearProfileState() {
        _getProfileState.value = null
    }

    fun updateProfile(data: ProfileRequest) {
        viewModelScope.launch {
            setLoading(true)
            try {
                _updateProfileState.value = repository.updateProfile(data)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
    }

    fun clearUpdateProfileState() {
        _updateProfileState.value = null
    }

    fun getKotaKabupaten(id: String = "19") {
        viewModelScope.launch {
            setLoading(true)
            try {
                _getKotaKabupatenState.value = repository.getKabupatenKota(id)
            } catch (e: Exception) {
                setError(handleException(e))
            }
            setLoading(false)
        }
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

    fun cleanKotaKabupatenState() {
        _getKotaKabupatenState.value = listOf()
    }

    fun clearKecamatanState() {
        _getKecamatanState.value = listOf()
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

    fun clearLogoutState() {
        _logoutState.value = null
    }
}