package com.example.rickandmorty.api

import com.example.rickandmorty.models.PagedCharacters
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RaMApi {
    @GET("/api/character")
    suspend fun getAllCharacter(@Query("page") page: Int): PagedCharacters
}

const val BASE_URL = "https://rickandmortyapi.com"

object RetrofitServices {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val searchApi: RaMApi = retrofit.create(RaMApi::class.java)
}
