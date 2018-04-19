package com.beuno.beuno.alpha

import com.beuno.beuno.bean.UnoGoods
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UnoWebService {
    @GET("/cart/{cart}")
    fun getCart(@Path("cart") userId: String): Call<List<UnoGoods>>
}