package com.gmail.cristiandeives.imgurgallery.data

import com.squareup.moshi.Json

class ResponseGallery(
    val id: String,
    val title: String,
    val description: String?,
    val link: String,
    @field:Json(name = "images_count") val imagesCount: Int?,
    val ups: Int = 0,
    val downs: Int = 0,
    val score: Int = 0,
    val images: List<ResponseImage>?,
)