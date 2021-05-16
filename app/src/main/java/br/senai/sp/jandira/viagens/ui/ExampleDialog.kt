package br.senai.sp.jandira.viagens.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import br.senai.sp.jandira.viagens.R

class ExampleDialog: AppCompatDialogFragment() {

    lateinit var editTextUsername: EditText
    lateinit var editTextPassword: EditText
    lateinit var listener: ExampleDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!)

        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.layout_dialog, null)

        builder.setView(view)
            .setTitle("Comentários")
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->

            })
            .setPositiveButton("Salvar", DialogInterface.OnClickListener { dialog, which ->
                var userName = editTextUsername.text.toString()
                var password = editTextPassword.text.toString()
                listener.applyTexts(userName, password)
            })


        editTextUsername = view.findViewById(R.id.edit_username)
        editTextPassword = view.findViewById(R.id.edit_password)

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as ExampleDialogListener
    }

    interface ExampleDialogListener {
        fun applyTexts(userName: String, password: String)
    }

}