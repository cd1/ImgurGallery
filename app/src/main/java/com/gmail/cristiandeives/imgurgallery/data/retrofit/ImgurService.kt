package com.gmail.cristiandeives.imgurgallery.data.retrofit

import com.gmail.cristiandeives.imgurgallery.data.Repository
import com.gmail.cristiandeives.imgurgallery.data.ResponseRoot
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImgurService {
    @Headers("Authorization: Client-ID ${Repository.IMGUR_CLIENT_ID}")
    @GET("gallery/hot/viral/day")
    fun getGalleries(): Call<ResponseRoot>
}