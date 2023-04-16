package com.example.whatsthatplant.network

import android.content.Context
import android.net.Uri
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


private const val BASE_URL =
    "https://api.plant.id/v2/"


interface PlantIdApiService {
    @Headers(
        "Content-Type: application/json",
        "Api-Key: tNfcdZf9BO11x5aGXnxRFfrfg7NjXNRp58UTZtnlFUZlYdMDjM"
    )
    @POST("identify")
    suspend fun identifyPlant(
        @Body requestBody: IdentifyRequestBody,
    ): PlantData
}


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


object PlantIdApi {
    private val service : PlantIdApiService by lazy {
        retrofit.create(PlantIdApiService::class.java)
    }

    suspend fun identifyPlant(context: Context, imageUri: Uri?): PlantData? {
        val plantRequestBody = createIdentifyRequestBody(context, imageUri)

        return try {
            service.identifyPlant(plantRequestBody)
        } catch (e: Exception) {
            Log.e("PlantIdApi", "Error fetching plant data", e)
            null
        }
    }

}