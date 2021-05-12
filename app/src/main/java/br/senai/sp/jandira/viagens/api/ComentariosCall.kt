package br.senai.sp.jandira.viagens.api

import br.senai.sp.jandira.viagens.model.Comentario
import br.senai.sp.jandira.viagens.model.Foto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ComentariosCall {

    @GET("comentarios/destinos/{id}")
    fun getComentariosFromDestino(@Path("id") id: Long): Call<List<Comentario>>

//    @GET("destinos/{id}/fotos")
//    fun getFotosDestino(@Path("id") id: Long): Call<List<Foto>>

}