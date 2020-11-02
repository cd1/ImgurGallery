package com.gmail.cristiandeives.imgurgallery.data.retrofit

import com.gmail.cristiandeives.imgurgallery.GallerySection
import com.gmail.cristiandeives.imgurgallery.GallerySortBy
import com.gmail.cristiandeives.imgurgallery.data.Repository
import com.gmail.cristiandeives.imgurgallery.data.ResponseRoot
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ImgurService {
    @Headers("Authorization: Client-ID ${Repository.IMGUR_CLIENT_ID}")
    @GET("gallery/{section}/{sort}/day")
    fun getGalleries(
        @Path("section") section: GallerySection,
        @Path("sort") sort: GallerySortBy,
        @Query("showViral") showViral: Boolean,
    ): Call<ResponseRoot>
}