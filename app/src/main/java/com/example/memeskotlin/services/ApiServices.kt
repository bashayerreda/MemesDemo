package com.example.memeskotlin.services

import com.example.memeskotlin.pojos.Memes
import com.example.memeskotlin.pojos.MemesResults
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface ApiServices {
    @GET("get_memes")
    suspend fun returnMemes():MemesResults
}