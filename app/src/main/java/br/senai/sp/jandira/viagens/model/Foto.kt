package br.senai.sp.jandira.viagens.model

data class Foto(
    var id: Long = 0,
    var url: String = "",
    var destaque: Boolean
) {
}