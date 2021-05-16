package br.senai.sp.jandira.viagens.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import br.senai.sp.jandira.viagens.R
import br.senai.sp.jandira.viagens.model.Comentario
import br.senai.sp.jandira.viagens.model.Destino
import br.senai.sp.jandira.viagens.model.Usuario
import br.senai.sp.jandira.viagens.repository.ComentarioRepository
import java.time.LocalDate

class DialogComment(context: Context) : AppCompatDialogFragment() {

    var idDestino: Long = 0
    var comentarioRepository: ComentarioRepository? = null

    lateinit var buttonEnviar: Button
    lateinit var editTextComentario: EditText
    lateinit var ratingComentario: RatingBar

    lateinit var listener: DialogCommentListener

    fun updateDestino(id: Long) {
        this.idDestino = id
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!)

        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.custom_dialog_comment, null)

        // Inicialização das views do layout
        editTextComentario = view.findViewById(R.id.et_comentario)
        ratingComentario = view.findViewById(R.id.rating_bar)

        builder.setView(view)
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->

            })
            .setPositiveButton("Salvar", DialogInterface.OnClickListener { dialog, which ->
                enviarComentario()
                listener.updateComments()
            })

        return builder.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("dialogFragment", "onCreate")
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogCustom)
        comentarioRepository = ComentarioRepository(context!!)
        listener.updateComments()
    }

    private fun enviarComentario() {
        val userData = context!!.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val comentario = Comentario(
            0,
            Usuario(userData.getString("id", null)!!),
            editTextComentario.text.toString(),
            ratingComentario.rating,
            LocalDate.now().toString(),
            Destino(this.idDestino)
        )

        comentarioRepository!!.salvarComentario(comentario)
        dismiss()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DialogCommentListener
    }

    interface DialogCommentListener {
        fun updateComments()
    }

}