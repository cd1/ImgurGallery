package com.gmail.cristiandeives.imgurgallery

import java.util.Locale

enum class GallerySection {
    HOT,
    TOP,
    USER;

    override fun toString() = name.toLowerCase(Locale.ROOT)
}