package com.example.whatsthatplant.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PlantData(
    @Json(name = "id") val id: Long?,
    @Json(name = "suggestions") val suggestions: List<Suggestion>?,
    @Json(name = "is_plant_probability") val isPlantProbability: Double?,
    @Json(name = "is_plant") val isPlant: Boolean?
)

@JsonClass(generateAdapter = true)
data class Suggestion(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "plant_name")
    val plantName: String?,
    @Json(name = "plant_details")
    val plantDetails: PlantDetails?,
    @Json(name = "probability")
    val probability: Double?,
    @Json(name = "similar_images")
    val similarImages: List<SimilarImage>?
)

@JsonClass(generateAdapter = true)
data class PlantDetails(
    @Json(name = "common_names")
    val commonNames: List<String>?,
    @Json(name = "edible_parts")
    val edibleParts: List<String>?,
    @Json(name = "propagation_methods")
    val propagationMethods: List<String>?,
    @Json(name = "taxonomy")
    val taxonomy: Taxonomy?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "scientific_name")
    val scientificName: String?
)

@JsonClass(generateAdapter = true)
data class Taxonomy(
    @Json(name = "class")
    val className: String?,
    @Json(name = "family")
    val family: String?,
    @Json(name = "genus")
    val genus: String?,
    @Json(name = "kingdom")
    val kingdom: String?,
    @Json(name = "order")
    val order: String?,
    @Json(name = "phylum")
    val phylum: String?,
)

@JsonClass(generateAdapter = true)
data class SimilarImage(
    @Json(name = "id")
    val id: String?,
    @Json(name = "similarity")
    val similarity: Double?,
    @Json(name = "url")
    val url: String?
)



