package br.senai.sp.jandira.viagens.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.senai.sp.jandira.viagens.R
import br.senai.sp.jandira.viagens.adapter.ComentariosAdapter
import br.senai.sp.jandira.viagens.adapter.GaleriaFotosDestinoAdapter
import br.senai.sp.jandira.viagens.api.ComentariosCall
import br.senai.sp.jandira.viagens.api.FotoCall
import br.senai.sp.jandira.viagens.api.RetrofitApi
import br.senai.sp.jandira.viagens.model.Comentario
import br.senai.sp.jandira.viagens.model.DestinosRecentes
import br.senai.sp.jandira.viagens.model.Foto
import br.senai.sp.jandira.viagens.repository.ComentarioRepository
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinoDetailActivity : AppCompatActivity(), DialogComment.DialogCommentListener {

    var comentarioRepository: ComentarioRepository? = null

    lateinit var textViewLocalizacao: TextView
    lateinit var imageViewDestino: ImageView
    lateinit var textValorDestino: TextView
    lateinit var textDescricao: TextView
    lateinit var rvGaleriaFotosDestino: RecyclerView
    lateinit var galeriaFotosDestinoAdapter: GaleriaFotosDestinoAdapter
    lateinit var destinoRecente: DestinosRecentes
    lateinit var buttonComentario: Button
    lateinit var collapseToolbarLayout: CollapsingToolbarLayout

    lateinit var rvComentarios: RecyclerView
    lateinit var comentariosAdapter: ComentariosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destino_detail2)

        comentarioRepository = ComentarioRepository(this)

        destinoRecente = intent.getSerializableExtra("destino") as DestinosRecentes

        textViewLocalizacao = findViewById(R.id.tv_local)
        imageViewDestino = findViewById(R.id.iv_destino)
        textValorDestino = findViewById(R.id.tv_valor_destino)
        textDescricao = findViewById(R.id.tv_texto_descricao)
        rvGaleriaFotosDestino = findViewById(R.id.rv_galeria_fotos_destino)
        rvComentarios = findViewById(R.id.rv_comentarios)
        collapseToolbarLayout = findViewById(R.id.collapsing_toolbar)

        buttonComentario = findViewById(R.id.button_comentario)

        buttonComentario.setOnClickListener {
            abrirComentarioDialog()
        }

        preencherActivity()
        carregarListaFotos()
        carregarListaComentarios()

    }

    private fun abrirComentarioDialog() {

        val customDialogComment = DialogComment(this)
        customDialogComment.updateDestino(destinoRecente.id)
        customDialogComment.show(supportFragmentManager, "custom dialog comment")

    }

    private fun carregarListaFotos() {

        galeriaFotosDestinoAdapter = GaleriaFotosDestinoAdapter(this)
        rvGaleriaFotosDestino.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvGaleriaFotosDestino.adapter = galeriaFotosDestinoAdapter

        // Lista de fotos que será exibida na recyclerview
        var fotos: List<Foto> = listOf<Foto>()

        // Instanciar retrofit
        val retrofitApi = RetrofitApi.getRetrofit()

        // Instancia da interface
        val fotosCall = retrofitApi.create(FotoCall::class.java)
        val call = fotosCall.getFotosDestino(destinoRecente.id)
        //val call = fotosCall.getFotos()

        // Executar a chamada para a API Http
        call.enqueue(object : Callback<List<Foto>> {
            override fun onFailure(call: Call<List<Foto>>, t: Throwable) {
                Toast.makeText(
                    this@DestinoDetailActivity,
                    "Erro ao carregar fotos!",
                    Toast.LENGTH_SHORT
                ).show()

            }

            override fun onResponse(call: Call<List<Foto>>, response: Response<List<Foto>>) {
                fotos = response.body()!!
                print(fotos.toString())
                galeriaFotosDestinoAdapter.updateListaFotos(fotos)
            }

        })

    }

    private fun carregarListaComentarios() {
        comentariosAdapter = ComentariosAdapter(this)
        rvComentarios.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvComentarios.adapter = comentariosAdapter

        var comentarios: List<Comentario> = listOf()

        val retrofit = RetrofitApi.getRetrofit()

        val comentariosCall = retrofit.create(ComentariosCall::class.java)
        val call = comentariosCall.getComentariosFromDestino(destinoRecente.id)

        call.enqueue(object : Callback<List<Comentario>>{
            override fun onFailure(call: Call<List<Comentario>>, t: Throwable) {
                Toast.makeText(
                    this@DestinoDetailActivity,
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
                comentariosAdapter.updateListaComentarios(comentarios)
            }

        })

    }

    private fun preencherActivity() {
        //textViewNomeDestino.text = "${destinoRecente.nome}"
        textViewLocalizacao.text = "${destinoRecente.nomeCidade}/${destinoRecente.siglaEstado}"
        collapseToolbarLayout.title = "${destinoRecente.nome}"

        if (destinoRecente.valor <= 0) {
            textValorDestino.text = "GRÁTIS"
        } else {
            textValorDestino.text = "R$ ${String.format("%.2f", destinoRecente.valor)}"
        }

        textDescricao.text = destinoRecente.descricao
        if (destinoRecente.urlFoto.trim().isNotEmpty()) {
            Glide.with(this).load(destinoRecente.urlFoto).into(imageViewDestino)
        }
    }

    override fun updateComments() {
        carregarListaComentarios()
    }
}