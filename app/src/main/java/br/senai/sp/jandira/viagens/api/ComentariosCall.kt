package br.senai.sp.jandira.viagens.api

import br.senai.sp.jandira.viagens.model.Comentario
import br.senai.sp.jandira.viagens.model.Foto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ComentariosCall {

    // Recuperar lista de destinos por id do destino
    @GET("comentarios/destinos/{id}")
    fun getComentariosFromDestino(@Path("id") id: Long): Call<List<Comentario>>

    // Enviar um comentário para um destino
    @POST("comentarios")
    fun postComentario(@Body comentario: Comentario) : Call<Comentario>

}