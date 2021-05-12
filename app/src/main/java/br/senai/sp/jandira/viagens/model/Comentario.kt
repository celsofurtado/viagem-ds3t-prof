package br.senai.sp.jandira.viagens.model

import java.time.LocalDate

data class Comentario (
    var idComentario: Long,
    var idUsuario: String,
    var displayName: String,
    var photoUrl: String,
    var comentario: String,
    var nota: Int,
    var dataComentario: String
)