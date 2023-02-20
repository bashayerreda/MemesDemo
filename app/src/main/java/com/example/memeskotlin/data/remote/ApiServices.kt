package com.example.memeskotlin.data.remote

import com.example.memeskotlin.domain.models.MemesResults
import retrofit2.http.GET

interface ApiServices {
    @GET("get_memes")
    suspend fun returnMemes():MemesResults
}