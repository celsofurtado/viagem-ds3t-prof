package br.senai.sp.jandira.viagens.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.senai.sp.jandira.viagens.R
import br.senai.sp.jandira.viagens.adapter.DestinoRecenteAdapter
import br.senai.sp.jandira.viagens.api.DestinosRecentesCall
import br.senai.sp.jandira.viagens.api.RetrofitApi
import br.senai.sp.jandira.viagens.model.DestinosRecentes
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var rvDestinosRecentes: RecyclerView
    lateinit var destinoRecenteAdapter: DestinoRecenteAdapter
    lateinit var textViewUserName: TextView
    lateinit var textViewUserMail: TextView
    lateinit var userImage: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var editBusca: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewUserName = findViewById(R.id.tv_user_name)
        textViewUserMail = findViewById(R.id.tv_user_email)
        userImage = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.progressBar)
        editBusca = findViewById(R.id.edit_busca)


        userImage.setOnClickListener {
            Toast.makeText(this, "Fechando", Toast.LENGTH_SHORT).show()
            signOut()
        }

        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null){
            val userData = getSharedPreferences("userData", Context.MODE_PRIVATE)
            textViewUserName.text = userData.getString("displayName", null)
            textViewUserMail.text = userData.getString("email", null)

            Glide.with(this)
                .load(userData.getString("photoUrl", null))
                .into(userImage)
        }

        rvDestinosRecentes = findViewById(R.id.rv_destinos_recentes)

        rvDestinosRecentes.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false)

        destinoRecenteAdapter = DestinoRecenteAdapter(this)
        rvDestinosRecentes.adapter = destinoRecenteAdapter

        setListaDestinosRecentes()

    }

//    override fun onResume() {
//        super.onResume()
//        val userData = getSharedPreferences("userData", Context.MODE_PRIVATE)
//        textViewUserName.text = userData.getString("displayName", null)
//        textViewUserMail.text = userData.getString("id", null)
//        Glide.with(this)
//            .load(userData.getString("photoUrl", null))
//            .into(userImage)
//    }

    private fun setListaDestinosRecentes() {

        var destinosRecentes: List<DestinosRecentes>? = listOf<DestinosRecentes>()

        val retrofit = RetrofitApi.getRetrofit()

        val destinosRecentesCall = retrofit.create(DestinosRecentesCall::class.java)
        val call = destinosRecentesCall.getDestinosRecentes()

        call.enqueue(object : Callback<List<DestinosRecentes>> {

            override fun onFailure(call: Call<List<DestinosRecentes>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "A conexão falhou!", Toast.LENGTH_SHORT).show()
                Log.e("xpto", t.cause.toString())
                Log.e("xpto", t.message.toString())
                Log.e("xpto", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<List<DestinosRecentes>>,
                response: Response<List<DestinosRecentes>>
            ) {
                destinosRecentes = response.body()
                Log.i("Teste", destinosRecentes.toString())
                destinoRecenteAdapter.updateListRecentes(destinosRecentes!!)
                progressBar.visibility = View.GONE
            }

        })

    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {

            }
        finish()
    }
}