package com.example.go_app

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.adapter.CustomAdapter
import com.example.go_app.models.AddressResponse
import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Address
import com.example.go_app.services.Publications
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndexActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var btnPerfil: FrameLayout? = null
    var inputPesquisa: EditText? = null
    var btnBusca: ImageView? = null
    var btnNotification: ImageView? = null
    var idLogado: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        recyclerView = findViewById(R.id.index_list_publication)
        btnPerfil = findViewById(R.id.index_profile)
        inputPesquisa = findViewById(R.id.index_et_search)
        btnBusca = findViewById(R.id.index_btn_search)
        btnNotification = findViewById(R.id.index_notification)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        val pasta = getSharedPreferences(
            "CREDENCIAIS",
            MODE_PRIVATE
        )

        val dark = pasta.getBoolean("dark", false)
        if (dark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        btnPerfil!!.setOnClickListener {
//            val telaPerfil = Intent(this, Perfil::class.java)
//            startActivity(telaPerfil)
            val telaCadastro = Intent(this, NovaDenuncia::class.java)
            startActivity(telaCadastro)
        }
        btnBusca!!.setOnClickListener {
            irPesquisa()
        }
        btnNotification!!.setOnClickListener {
            goNotification()
        }
        idLogado = pasta.getString("idLogado", "")
        val nome = pasta.getString("nomeLogado", "")
        if (idLogado != null) {
            var nomeTela: TextView? = null
            nomeTela = findViewById(R.id.index_text_name)
            var primeiraLetra: TextView? = null
            primeiraLetra = findViewById(R.id.tv_valor)
            nomeTela.text = nome
            primeiraLetra.text = nome!!.subSequence(0, 1)
            getAddress(idLogado.toString().toInt())
        }
    }

    private fun getMovies(endereco: String) {
        val request = Rest.getInstance().create(Publications::class.java)

        request.getPublicationsByCity(endereco).enqueue(
            object : Callback<List<ComplaintsResponse>> {
                override fun onResponse(
                    call: Call<List<ComplaintsResponse>>,
                    response: Response<List<ComplaintsResponse>>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {

                        println("aqui")
                        val listaPub = mutableListOf<ComplaintsResponse>()
                        for (item in (response.body()!!)) {
                            if (!item.status.equals("Inativo")) {
                                listaPub.add(item)
                            }
                        }
                        val adapter =
                            CustomAdapter(
                                listaPub, idLogado.toString().toInt(), true,
                                applicationContext
                            )
                        recyclerView?.adapter = adapter
                    }

                }

                override fun onFailure(call: Call<List<ComplaintsResponse>>, t: Throwable) {
                    println("não foi")
                }

            }
        )
    }

    private fun getAddress(id: Int) {
        val request = Rest.getInstance().create(Address::class.java)

        request.getUserAddressById(id).enqueue(
            object : Callback<List<AddressResponse>> {
                override fun onResponse(
                    call: Call<List<AddressResponse>>,
                    response: Response<List<AddressResponse>>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {
                        val pasta = getSharedPreferences(
                            "CREDENCIAIS",
                            MODE_PRIVATE
                        )
                        val editor = pasta.edit()
                        editor.putString(
                            "cidadeLogado",
                            "${response.body()!![0].city}, ${response.body()!![0].district}"
                        )
                        editor.commit()
                        var cidade: TextView? = null
                        cidade = findViewById(R.id.index_text_city)
                        cidade.text =
                            "${response.body()!![0].city}, ${response.body()!![0].district}"
                        getMovies(response.body()!![0].city)
                    }

                }

                override fun onFailure(call: Call<List<AddressResponse>>, t: Throwable) {
                    println("não foi")
                }

            }
        )
    }

    private fun irPesquisa() {
        val telaPesquisa = Intent(
            this,
            Search::class.java
        )
        var busca = ""
        if (inputPesquisa!!.text.isNotEmpty() && inputPesquisa!!.text.isNotBlank()) {
            busca = inputPesquisa!!.text.toString()
        }
        telaPesquisa.putExtra(
            "busca",
            busca
        )
        startActivity(telaPesquisa)
    }

    private fun goNotification() {
        val viewNotification = Intent(
            this,
            Notification::class.java
        )

        startActivity(viewNotification)
    }
}
