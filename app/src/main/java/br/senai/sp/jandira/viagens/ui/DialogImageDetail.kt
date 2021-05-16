package br.senai.sp.jandira.viagens.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import br.senai.sp.jandira.viagens.R
import com.bumptech.glide.Glide

class DialogImageDetail : DialogFragment() {

    lateinit var image: ImageView
    lateinit var btnFechar: ImageButton

    var imageUrl: String = ""

    fun updateImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogCustom)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.custom_dialog_foto_detail, container, false)
        //dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        image = view.findViewById(R.id.iv_detalhe)
        btnFechar = view.findViewById(R.id.bt_fechar)

        btnFechar.setOnClickListener {
            dismiss()
        }

        Glide.with(context!!).load(imageUrl).into(image)

        return view
    }

}