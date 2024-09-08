package com.ipb.remangokbabel.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.ipb.remangokbabel.model.response.ErrorResponse
import com.ipb.remangokbabel.ui.common.UiState
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val MAXIMAL_SIZE = 50000 //1 MB
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())


fun navigateTo(navController: NavHostController, route: String) {
    navController.navigate(route)
}

fun navigateToAndMakeTop(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            inclusive = true
        }
    }
}

fun navigateToBack(navController: NavHostController) {
    navController.navigateUp()
}

fun handleException2(exception: Exception): UiState.Error<ErrorResponse> {
    val errorResponse = when (exception) {
        is HttpException -> parseError(exception.response()?.errorBody()?.string())
        else -> ErrorResponse("An unknown error occurred") // Return a default ErrorResponse object
    }
    return UiState.Error(errorResponse)
}

fun handleException(exception: Exception): ErrorResponse {
    val errorResponse = when (exception) {
        is HttpException -> parseError(exception.response()?.errorBody()?.string())
        else -> ErrorResponse("An unknown error occurred") // Return a default ErrorResponse object
    }
    return errorResponse
}

fun parseError(json: String?): ErrorResponse {
    return try {
        if (json != null) {
            val gson = Gson()
            gson.fromJson(json, ErrorResponse::class.java)
        } else {
            ErrorResponse("An unexpected error occurred") // Return default ErrorResponse if JSON is null
        }
    } catch (e: Exception) {
        ErrorResponse("Failed to parse error message") // Return default ErrorResponse if parsing fails
    }
}

// create function to convert Int to rupiah
fun Int.toRupiah(): String {
    val rupiah = StringBuilder()
    val value = this.toString()
    val reverseValue = value.reversed()
    for (i in reverseValue.indices) {
        rupiah.append(reverseValue[i])
        if ((i + 1) % 3 == 0 && i != reverseValue.length - 1) {
            rupiah.append(".")
        }
    }
    return "Rp. ${rupiah.reverse()}"
}

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

fun uriToFile(imageUri: Uri, context: Context): File {
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun File.reduceFileImage(): File {
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun String.toOnlyNumber(): String {
    return this.replace(Regex("[^0-9]"), "")
}