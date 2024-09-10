package com.ipb.remangokbabel.utils

import com.ipb.remangokbabel.data.local.PaperPrefs
import com.ipb.remangokbabel.data.repository.Repository
import com.ipb.remangokbabel.model.request.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
class TokenInterceptor(
    private val repository: Repository?,
    private val paperPrefs: PaperPrefs
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = paperPrefs.getAccessToken() // Ambil access token yang ada

        // Tambahkan Authorization Header dengan Access Token
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(authenticatedRequest)

        // Jika respon 401 (Unauthorized), refresh token
        if (response.code == 401) {
            synchronized(this) {  // Synchronized untuk menghindari refresh token bersamaan
                // Cek kembali jika token sudah diperbarui oleh thread lain
                val newAccessToken = paperPrefs.getAccessToken()
                if (accessToken == newAccessToken) {
                    // Jika token belum diperbarui, lakukan refresh
                    val refreshTokenResponse = runBlocking {
                        repository?.refreshToken(RefreshTokenRequest(newAccessToken))  // Panggil fungsi suspend di dalam runBlocking
                    }

                    if (refreshTokenResponse?.status == "success") {
                        val newToken = refreshTokenResponse.data.accessToken
                        runBlocking {
                            paperPrefs.setAccessToken(newToken)
                        }
                        // Buat permintaan ulang dengan token baru
                        val newRequest = originalRequest.newBuilder()
                            .header("Authorization", "Bearer $newToken")
                            .build()
                        return chain.proceed(newRequest)
                    } else {
                        // Jika refresh token gagal, misalnya tidak valid, hapus semua data token dan arahkan ke login
                        paperPrefs.deleteAllData()
                        // Anda bisa menambahkan logika untuk menavigasi ulang ke halaman login
                    }
                }
            }
        }

        return response
    }
}
