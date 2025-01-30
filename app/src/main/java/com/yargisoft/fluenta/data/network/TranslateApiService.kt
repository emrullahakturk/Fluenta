package com.yargisoft.fluenta.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateApiService {
    @GET("translate_a/single?client=gtx&dt=t")
    suspend fun translate(
        @Query("sl") sourceLang: String,  // Kaynak dil
        @Query("tl") targetLang: String,  // Hedef dil
        @Query("q") text: String          // Çevrilecek metin
    ): List<Any>
}
