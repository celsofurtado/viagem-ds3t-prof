package br.senai.sp.jandira.viagens.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.senai.sp.jandira.viagens.R
import br.senai.sp.jandira.viagens.model.Foto
import com.bumptech.glide.Glide

class GaleriaFotosDestinoAdapter(var context: Context) : RecyclerView.Adapter<GaleriaFotosDestinoAdapter.ViewHolder>() {

    var listaFotos: List<Foto> = listOf()

    fun updateListaFotos(lista: List<Foto>) {
        this.listaFotos = lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.destino_fotos_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.listaFotos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foto = listaFotos[position]

        Glide.with(context).load(foto.url).into(holder.imageDestino)

        if (foto.destaque){
            holder.checkBoxCapa.isChecked = foto.destaque
            holder.checkBoxCapa.isClickable = false
        } else {
            holder.checkBoxCapa.visibility = View.GONE
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageDestino: ImageView = view.findViewById(R.id.iv_foto_holder)
        val checkBoxCapa: CheckBox = view.findViewById(R.id.check_box_capa)

    }
}