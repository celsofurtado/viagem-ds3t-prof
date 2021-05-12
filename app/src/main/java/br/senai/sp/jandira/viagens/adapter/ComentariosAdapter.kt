package br.senai.sp.jandira.viagens.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.senai.sp.jandira.viagens.R
import br.senai.sp.jandira.viagens.model.Comentario
import com.bumptech.glide.Glide
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ComentariosAdapter(var context: Context) : RecyclerView.Adapter<ComentariosAdapter.ViewHolder>() {

    private var comentarios: List<Comentario> = arrayListOf()

    fun updateListaComentarios(lista: List<Comentario>) {
        this.comentarios = lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comentarios_holder_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comentarios.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comentario = comentarios[position]

        holder.tvDisplayName.text = comentario.displayName
        holder.tvComentario.text = comentario.comentario
        holder.tvDataComentario.text = comentario.dataComentario.toString()
        var nota: Double = comentario.nota.toDouble()
        holder.tvNota.text = String.format("%.1f", nota)
        Glide.with(context).load(comentario.photoUrl).into(holder.ivUserPhoto)

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        var tvDisplayName: TextView = view.findViewById(R.id.tv_display_name)
        var tvDataComentario: TextView = view.findViewById(R.id.tv_data_comentario)
        var tvComentario: TextView = view.findViewById(R.id.tv_comentario)
        var ivUserPhoto: ImageView = view.findViewById(R.id.iv_user_photo)
        var tvNota: TextView = view.findViewById(R.id.tv_nota)

    }

    fun format(date: LocalDate): String {
        return ""
    }
}