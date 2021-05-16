package br.senai.sp.jandira.viagens.model

import java.time.LocalDate

data class Comentario (
    var idComentario: Long = 0,
    var usuario: Usuario,
    var comentario: String = "",
    var nota: Float = 0.0F,
    var dataComentario: String,
    var destino: Destino
)

//data class Comentario (
//    var idComentario: Long,
//    var idUsuario: String,
//    var displayName: String,
//    var photoUrl: String,
//    var comentario: String,
//    var nota: Int,
//    var dataComentario: String
//)