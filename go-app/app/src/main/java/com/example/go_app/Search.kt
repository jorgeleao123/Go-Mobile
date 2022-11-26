package com.example.go_app

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
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

//import com.example.go_app.adapter.CustomAdapter

class Search : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var inputPesquisa: EditText? = null
    var btnBusca: ImageView? = null
    var total: TextView? = null
    val listaPub = mutableListOf<ComplaintsResponse>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        inputPesquisa = findViewById(R.id.index_et_search)
        btnBusca = findViewById(R.id.search_btn_search)
        total = findViewById(R.id.search_result)

        recyclerView = findViewById(R.id.search_list_publication)
        //configurar RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

        btnBusca!!.setOnClickListener {
            btnBusca!!.setOnClickListener {
                if (inputPesquisa!!.text.isNotEmpty() && inputPesquisa!!.text.isNotBlank())
                    getPlaca(inputPesquisa!!.text.toString())
            }
        }
        val buscar = intent.getStringExtra(
            "busca"
        )
        if (buscar != null) {
            getPlaca(buscar)
        }

    }

    private fun getPlaca(value: String) {
        listaPub.clear()
        val request = Rest.getInstance().create(Publications::class.java)
        request.getPublicationsByLicense(value).enqueue(
            object : Callback<List<ComplaintsResponse>> {
                override fun onResponse(
                    call: Call<List<ComplaintsResponse>>,
                    response: Response<List<ComplaintsResponse>>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {
                        println("aqui")
                        if (response.body() != null) {
                            for (item in (response.body()!!)) {
                                if (!item.status.equals("Inativo")) {

                                } else {
                                    listaPub.add(item)
                                }
                            }
                        }

                        getCidade(value)
                    }

                }

                override fun onFailure(call: Call<List<ComplaintsResponse>>, t: Throwable) {
                    println("não foi")
                    getCidade(value)
                }

            }
        )
    }

    private fun getCidade(value: String) {
        val request = Rest.getInstance().create(Publications::class.java)
        request.getPublicationsByCity(value).enqueue(
            object : Callback<List<ComplaintsResponse>> {
                override fun onResponse(
                    call: Call<List<ComplaintsResponse>>,
                    response: Response<List<ComplaintsResponse>>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {
                        println("aqui")
                        if (response.body() != null) {
                            for (item in (response.body()!!)) {
                                if (!item.status.equals("Inativo")) {

                                } else {
                                    listaPub.add(item)
                                }
                            }
                        }
                        total!!.text =
                            "Encontramos ${listaPub.size} resultados para essa pesquisa!"

                        val pasta = getSharedPreferences(
                            "CREDENCIAIS",
                            MODE_PRIVATE
                        )

                        val id = pasta.getString("idLogado", "")
                        if (id != null) {
                            val adapter = CustomAdapter(
                                listaPub,
                                id.toString().toInt(),
                                true,
                                applicationContext
                            )
                            recyclerView?.adapter = adapter
                        }


                    }

                }

                override fun onFailure(call: Call<List<ComplaintsResponse>>, t: Throwable) {
                    println("não foi")
                }

            }
        )
    }
}