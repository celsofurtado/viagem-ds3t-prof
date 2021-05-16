package br.senai.sp.jandira.viagens.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.senai.sp.jandira.viagens.api.ComentariosCall
import br.senai.sp.jandira.viagens.api.RetrofitApi
import br.senai.sp.jandira.viagens.model.Comentario
import br.senai.sp.jandira.viagens.model.DestinosRecentes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComentarioRepository(val context: Context) {

    fun salvarComentario(comentario: Comentario) {
        val retrofit = RetrofitApi.getRetrofit()
        val comentariosCall = retrofit.create(ComentariosCall::class.java)

        val call = comentariosCall.postComentario(comentario)

        call.enqueue(object : Callback<Comentario> {
            override fun onFailure(call: Call<Comentario>, t: Throwable) {
                Toast.makeText(context, "Erro ao gravar!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Comentario>, response: Response<Comentario>) {
                Log.i("Comment", response.body().toString())
                //dismiss()
            }

        })
    }

    fun listarComentarios(destinoRecente: DestinosRecentes): List<Comentario> {

        var comentarios: List<Comentario> = listOf()

        val retrofit = RetrofitApi.getRetrofit()

        val comentariosCall = retrofit.create(ComentariosCall::class.java)
        val call = comentariosCall.getComentariosFromDestino(destinoRecente.id)

        call.enqueue(object : Callback<List<Comentario>>{
            override fun onFailure(call: Call<List<Comentario>>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Não carregou os comentários!",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("Coment", t.localizedMessage)
                Log.e("Coment", t.stackTrace.toString())
                Log.e("Coment", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<Comentario>>,
                response: Response<List<Comentario>>
            ) {
                comentarios = response.body()!!
                //comentariosAdapter.updateListaComentarios(comentarios)
            }

        })

        return comentarios
    }
}