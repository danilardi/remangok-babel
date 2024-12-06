package com.ipb.remangokbabel.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.ipb.remangokbabel.model.response.ErrorResponse
import com.ipb.remangokbabel.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val MAXIMAL_SIZE = 1000000 //1 MB
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())


fun navigateTo(navController: NavHostController, route: String) {
    navController.navigate(route)
}

fun navigateToAndMakeTop(navController: NavHostController, route: String) {
    navController.navigate(route) {
        // Pop up to the start destination of the graph
        popUpTo(0) {
            inclusive = true // Remove the start destination as well
        }

        // Avoid adding the destination to the back stack again if it's already on top
        launchSingleTop = true
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

fun Int.toRupiah2(): String {
    val rupiah = StringBuilder()
    val value = this.toString()
    val reverseValue = value.reversed()
    for (i in reverseValue.indices) {
        rupiah.append(reverseValue[i])
        if ((i + 1) % 3 == 0 && i != reverseValue.length - 1) {
            rupiah.append(".")
        }
    }
    return "${rupiah.reverse()}"
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

suspend fun File.reduceFileImage(): File = withContext(Dispatchers.IO) {
    val file = this@reduceFileImage
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        println("$compressQuality, $streamLength")
        compressQuality -= 10
    } while (streamLength > MAXIMAL_SIZE && compressQuality > 10)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return@withContext file
}

fun String.toOnlyNumber(): String {
    return this.replace(Regex("[^0-9]"), "")
}

fun String.capitalizeEachWord(): String {
    return this.lowercase().split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

fun openWhatsApp(context: Context, phoneNumber: String, message: String) {
//    jika phoneNumber tidak diawali dengan 62, maka ubah menjadi 62
    val number = if (phoneNumber.startsWith("0")) {
        phoneNumber.replaceFirst("0", "62")
    } else {
        phoneNumber
    }
    val url = "https://api.whatsapp.com/send?phone=$number&text=${Uri.encode(message)}"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
    }
}