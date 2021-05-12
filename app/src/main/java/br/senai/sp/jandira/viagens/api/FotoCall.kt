package br.senai.sp.jandira.viagens.api

import br.senai.sp.jandira.viagens.model.Foto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FotoCall {

    @GET("destinos/{id}/fotos")
    fun getFotosDestino(@Path("id") id: Long): Call<List<Foto>>

    @GET("destinos/100/fotos")
    fun getFotos(): Call<List<Foto>>

}