package br.senai.sp.jandira.viagens.model

data class Usuario(
    var id: String,
    var displayName: String = "",
    var email: String = "",
    var photoUrl: String = ""
)