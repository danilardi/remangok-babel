package com.ipb.remangokbabel.data.local

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import com.google.gson.Gson
import com.ipb.remangokbabel.model.response.ProfilesItem
import io.paperdb.Paper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PaperPrefs : CoroutineScope, LifecycleObserver {

    // Coroutine background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    constructor(application: Application) {
        Paper.init(application)
    }

    constructor(context: Context) {
        Paper.init(context)
    }

    fun deleteAllData() {
        val allKeys = Paper.book().allKeys
        allKeys.forEach {
            if (it != "EMAIL_SAVED") {
                Paper.book().delete(it)
            }
        }
    }

    private fun getStringFromPaperPrefAsync(key: String): String {
        return Paper.book().read(key, "")!!
    }

    private fun getBooleanFromPaperPrefAsync(key: String, default: Boolean): Boolean {
        return Paper.book().read(key, default)!!
    }

    private fun getIntFromPaperPrefAsync(key: String, default: Int): Int {
        return Paper.book().read(key, default)!!
    }

    private fun getLongFromPaperPrefAsync(key: String, default: Long): Long {
        return Paper.book().read(key, default)!!
    }

    private fun getDoubleFromPaperPrefAsync(key: String, default: Double): Double {
        return Paper.book().read(key, default)!!
    }

    private suspend fun saveStringToPaperPref(key: String, value: String) {
        coroutineScope {
            launch {
                withContext(Dispatchers.IO) {
                    Paper.book().write(key, value)
                }
            }
        }
    }

    private suspend fun saveBooleanToPaperPref(key: String, value: Boolean) {
        coroutineScope {
            launch {
                withContext(Dispatchers.IO) {
                    try {
                        Paper.book().write(key, value)
                    } catch (_: Exception) {

                    }
                }
            }
        }
    }

    private suspend fun saveIntToPaperPref(key: String, value: Int) {
        coroutineScope {
            launch {
                withContext(Dispatchers.IO) {
                    Paper.book().write(key, value)
                }
            }
        }
    }

    private suspend fun saveLongToPaperPref(key: String, value: Long) {
        coroutineScope {
            launch {
                withContext(Dispatchers.IO) {
                    Paper.book().write(key, value)
                }
            }
        }
    }

    private suspend fun saveDoubleToPaperPref(key: String, value: Double) {
        coroutineScope {
            launch {
                withContext(Dispatchers.IO) {
                    Paper.book().write(key, value)
                }
            }
        }
    }

    suspend fun setAccessToken(token: String) {
        saveStringToPaperPref("ACCESS_TOKEN", token)
    }

    fun getAccessToken(): String {
        return getStringFromPaperPrefAsync("ACCESS_TOKEN")
    }

    suspend fun setRefreshToken(token: String) {
        saveStringToPaperPref("REFRESH_TOKEN", token)
    }

    fun getRefreshToken(): String {
        return getStringFromPaperPrefAsync("REFRESH_TOKEN")
    }

    suspend fun setRole(role: String) {
        saveStringToPaperPref("ROLE", role)
    }

    fun getRole(): String {
        return getStringFromPaperPrefAsync("ROLE")
    }

    suspend fun setEmailSaved(email: String) {
        saveStringToPaperPref("EMAIL_SAVED", email)
    }

    fun getEmailSaved(): String {
        return getStringFromPaperPrefAsync("EMAIL_SAVED")
    }

    suspend fun setProfile(dataProfile: ProfilesItem) {
        val data = Gson().toJson(dataProfile)
        saveStringToPaperPref("PROFILE", data)
    }

    fun getProfile(): ProfilesItem? {
        val data = getStringFromPaperPrefAsync("PROFILE")
        if (data.isEmpty()) {
            return null
        }
        return Gson().fromJson(data, ProfilesItem::class.java) as ProfilesItem
    }

}