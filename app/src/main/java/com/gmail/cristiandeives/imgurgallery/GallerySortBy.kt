package com.gmail.cristiandeives.imgurgallery

import java.util.Locale

enum class GallerySortBy {
    VIRAL,
    TOP,
    TIME,
    RISING;

    override fun toString() = name.toLowerCase(Locale.ROOT)
}