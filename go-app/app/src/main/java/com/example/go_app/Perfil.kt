package com.example.go_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.adapter.CustomAdapter
import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Publications
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Perfil : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        recyclerView = findViewById(R.id.listaItem)

        //configurar RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        val pasta = getSharedPreferences(
            "CREDENCIAIS",
            MODE_PRIVATE
        )

        val id = pasta.getString("idLogado", "")
        val cidade = pasta.getString("cidadeLogado", "")
        val buscasFeitas = pasta.getString("buscasFeitas", "")
        val nome = pasta.getString("nomeLogado", "")

        var nomeTela: TextView? = null
        var bfTela: TextView? = null
        var cidadeTela: TextView? = null
        var inicial: TextView? = null

        nomeTela = findViewById(R.id.textView14)
        bfTela = findViewById(R.id.textView16)
        inicial = findViewById(R.id.tv_valor)
        cidadeTela = findViewById(R.id.textView15)


        bfTela.text = buscasFeitas
        nomeTela.text = nome
        cidadeTela.text = cidade
        inicial.text = nome!!.subSequence(0, 1)


        if (id != null) {
            getMovies(id.toInt())
        }
    }


    private fun getMovies(id: Int) {
        val request = Rest.getInstance().create(Publications::class.java)

        request.getPublicationUser(id).enqueue(
            object : Callback<List<ComplaintsResponse>> {
                override fun onResponse(
                    call: Call<List<ComplaintsResponse>>,
                    response: Response<List<ComplaintsResponse>>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {
                        println("aqui")
                        var totalPubli: TextView? = null
                        totalPubli = findViewById(R.id.textView17)
                        totalPubli.text = response.body()!!.size.toString()
                        val adapter = CustomAdapter(response.body()!!)
                        recyclerView?.adapter = adapter
                    }

                }

                override fun onFailure(call: Call<List<ComplaintsResponse>>, t: Throwable) {
                    println("não foi")
                }

            }
        )
    }

}