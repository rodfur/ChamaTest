package com.chama.googleplacestest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NearbySearchResponse (
    @Json(name = "html_attributions")
    val htmlAttributions: List<Any?>,

    val results: List<NearbySearchResult>,
    val status: String
)

data class NearbySearchResult (
    val geometry: Geometry,
    val icon: String,
    val id: String,
    val name: String,

    @Json(name = "opening_hours")
    val openingHours: OpeningHours? = null,

    val photos: List<Photo>,

    @Json(name = "place_id")
    val placeID: String,

    val reference: String,
    val types: List<String>,
    val vicinity: String
)

data class Geometry (
    val location: Location
)

data class Location (
    val lat: Double,
    val lng: Double
)

data class OpeningHours (
    @Json(name = "open_now")
    val openNow: Boolean
)

data class Photo (
    val height: Long,

    @Json(name = "html_attributions")
    val htmlAttributions: List<Any?>,

    @Json(name = "photo_reference")
    val photoReference: String,

    val width: Long
)
