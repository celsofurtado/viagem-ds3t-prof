package br.senai.sp.jandira.viagens.api

import br.senai.sp.jandira.viagens.model.DestinosRecentes
import retrofit2.Call
import retrofit2.http.GET

interface DestinosRecentesCall {

    @GET("destinos/recentes")
    fun getDestinosRecentes() : Call<List<DestinosRecentes>>



}