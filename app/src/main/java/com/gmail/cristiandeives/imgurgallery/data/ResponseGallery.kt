package com.gmail.cristiandeives.imgurgallery.data

import com.squareup.moshi.Json

class ResponseGallery(
    val id: String,
    val title: String,
    val link: String,
    @field:Json(name = "images_count") val imagesCount: Int?,
    val images: List<ResponseImage>?,
)