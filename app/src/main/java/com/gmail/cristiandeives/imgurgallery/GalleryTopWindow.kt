package com.gmail.cristiandeives.imgurgallery

import java.util.Locale

enum class GalleryTopWindow {
    DAY,
    WEEK,
    MONTH,
    YEAR,
    ALL;

    override fun toString() = name.toLowerCase(Locale.ROOT)
}