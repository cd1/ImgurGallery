package com.gmail.cristiandeives.imgurgallery.data

import android.util.Log
import com.gmail.cristiandeives.imgurgallery.Gallery
import com.gmail.cristiandeives.imgurgallery.GallerySection
import com.gmail.cristiandeives.imgurgallery.data.retrofit.ImgurService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Locale

object Repository {
    private const val IMGUR_BASE_URL = "https://api.imgur.com/3/"
    const val IMGUR_CLIENT_ID = "1fc7cee3780f8ee"

    private val TAG = Repository::class.java.simpleName

    private val retrofit = Retrofit.Builder()
        .baseUrl(IMGUR_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val imgurService = retrofit.create(ImgurService::class.java)

    fun getGalleryImages(section: GallerySection, showViral: Boolean, cb: AsyncCallback<List<Gallery>>) {
        Log.d(TAG, "sending request to read galleries from imgur...")
        imgurService.getGalleries(section, showViral).enqueue(object : Callback<ResponseRoot> {
            override fun onResponse(call: Call<ResponseRoot>, response: Response<ResponseRoot>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "request success; response = $response")
                    val galleries = response.body()?.data?.map { g ->
                        val link = if (g.imagesCount == null) {
                            g.link
                        } else {
                            g.images?.firstOrNull()?.link ?: g.link
                        }

                        Gallery(id = g.id, title = g.title, imageUrl = link)
                    }
                    cb.onSuccess(galleries ?: emptyList())
                } else {
                    Log.e(TAG, "request failed; HTTP status code = ${response.code()}")
                    cb.onError(Exception("HTTP status code = ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<ResponseRoot>, t: Throwable) {
                Log.e(TAG, "request failed; error = ${t.message}", t)

                val ex = (t as? Exception) ?: Exception(t)
                cb.onError(ex)
            }
        })
    }
}