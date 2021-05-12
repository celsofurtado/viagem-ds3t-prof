package br.senai.sp.jandira.viagens.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.senai.sp.jandira.viagens.R
import br.senai.sp.jandira.viagens.model.DestinosRecentes
import br.senai.sp.jandira.viagens.ui.DestinoDetailActivity
import com.bumptech.glide.Glide

class DestinoRecenteAdapter(val context: Context) : RecyclerView.Adapter<DestinoRecenteAdapter.Holder>() {

    var listRecentes: List<DestinosRecentes> = emptyList()

    fun updateListRecentes(lista: List<DestinosRecentes>) {
        this.listRecentes = lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.card_recentes, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listRecentes.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val destinoRecente = listRecentes[position]

        holder.tvNomeDestino.text = destinoRecente.nome
        holder.tvLocalidade.text = destinoRecente.nomeCidade

        if (destinoRecente.valor <= 0) {
            holder.tvValor.text = "GRÁTIS"
        } else {
            holder.tvValor.text = "R$ ${String.format("%.2f", destinoRecente.valor)}"
        }

        // inserir imagem no ImageView com Glide através da URL
        if (destinoRecente.urlFoto.trim().isNotEmpty()) {
            Glide.with(context).load(destinoRecente.urlFoto).into(holder.ivDestinosRecentes)
        }

        holder.cardHolder.setOnClickListener {
            val intent = Intent(context, DestinoDetailActivity::class.java)
            intent.putExtra("destino", destinoRecente)
            context.startActivity(intent)
        }
    }

    // inner class
    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        val tvNomeDestino = view.findViewById<TextView>(R.id.tv_nome_destino)
        val tvLocalidade = view.findViewById<TextView>(R.id.tv_localidade)
        val tvValor = view.findViewById<TextView>(R.id.tv_valor)
        val ivDestinosRecentes = view.findViewById<ImageView>(R.id.iv_destinos_recentes)
        val cardHolder = view.findViewById<CardView>(R.id.card_holder)

    }

}