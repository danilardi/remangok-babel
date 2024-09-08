package com.ipb.remangokbabel.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.AddProfileRequest
import com.ipb.remangokbabel.model.response.GetKabupatenKotaResponseItem
import com.ipb.remangokbabel.model.response.GetKecamatanResponseItem
import com.ipb.remangokbabel.model.response.GetKelurahanResponseItem
import com.ipb.remangokbabel.model.response.GetProfileResponse
import com.ipb.remangokbabel.model.response.GetProvinsiResponseItem
import com.ipb.remangokbabel.model.response.StatusMessageResponse
import com.ipb.remangokbabel.utils.handleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : BaseViewModel() {

    val getProfileResponse = MutableSharedFlow<GetProfileResponse>()
    val addProfileResponse = MutableSharedFlow<StatusMessageResponse>()
    val updateProfileResponse = MutableSharedFlow<StatusMessageResponse>()
    val deleteProfileResponse = MutableSharedFlow<StatusMessageResponse>()
    val getProvinsiResponse = MutableSharedFlow<List<GetProvinsiResponseItem>>()
    val getKabupatenKotaResponse = MutableSharedFlow<List<GetKabupatenKotaResponseItem>>()
    val getKecamatanResponse = MutableSharedFlow<List<GetKecamatanResponseItem>>()
    val getKelurahanResponse = MutableSharedFlow<List<GetKelurahanResponseItem>>()
    val logoutResponse = MutableSharedFlow<StatusMessageResponse>()

    fun getProfile() {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                getProfileResponse.emit(repository.getProfile())
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun addProfile(data: AddProfileRequest) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                addProfileResponse.emit(repository.addProfile(data))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun updateProfile(id: String, data: AddProfileRequest) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                updateProfileResponse.emit(repository.updateProfile(id, data))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun deleteProfile(id: String) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                deleteProfileResponse.emit(repository.deleteProfile(id))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun getProvinsi() {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                getProvinsiResponse.emit(repository.getProvinsi())
            } catch (e: Exception) {
                println("Error")
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun getKabupatenKota(id: String) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                getKabupatenKotaResponse.emit(repository.getKabupatenKota(id))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun getKecamatan(id: String) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                getKecamatanResponse.emit(repository.getKecamatan(id))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun getKelurahan(id: String) {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                getKelurahanResponse.emit(repository.getKelurahan(id))
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            showLoading.emit(true)
            try {
                logoutResponse.emit(repository.logout())
            } catch (e: Exception) {
                errorResponse.emit(handleException(e))
            }
            showLoading.emit(false)
        }
    }
}