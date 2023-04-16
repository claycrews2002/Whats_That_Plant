package com.example.whatsthatplant.network

import android.content.Context
import android.net.Uri
import android.opengl.ETC1
import android.util.Base64
import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import kotlin.coroutines.coroutineContext

@JsonClass(generateAdapter = true)
data class IdentifyRequestBody(
    @Json(name = "images")
    val images: List<String>,
    @Json(name = "plant_details")
    val plantDetails: List<String>? = listOf("common_names", "edible_parts", "propagation_methods", "taxonomy", "url", "scientific_name"),
    @Json(name = "modifiers")
    val modifiers: List<String>? = listOf("similar_images")
)

suspend fun createIdentifyRequestBody(context: Context, imageUri: Uri?): IdentifyRequestBody {
    // encode image for plant.id input
    val encodedImage = encodeImage(context, imageUri)

    val images = listOf(encodedImage)
    return IdentifyRequestBody(images = images)
}


// Encode image as Base64 string
private suspend fun encodeImage(context: Context, imageUri: Uri?): String = withContext(Dispatchers.IO){

    var base64String: String? = null
    try {
        // Get the InputStream for the image URI using the ContentResolver
        val inputStream = context.contentResolver.openInputStream(imageUri!!)
        // Convert the InputStream to a byte array using a ByteArrayOutputStream
        val buffer = ByteArray(8192)
        var bytesRead: Int
        val output = ByteArrayOutputStream()
        while (inputStream?.read(buffer).also { bytesRead = it!! } != -1) {
            output.write(buffer, 0, bytesRead)
        }
        val imageBytes = output.toByteArray()
        // Encode the byte array in base64
        base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    base64String ?: ""
}
