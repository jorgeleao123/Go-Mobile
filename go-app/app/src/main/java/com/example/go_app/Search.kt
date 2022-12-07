package com.example.go_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.adapter.CustomAdapter
import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Publications
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Search(
    val contex: Context,
) : Fragment() {
    var recyclerView: RecyclerView? = null
    var inputPesquisa: EditText? = null
    var btnBusca: ImageView? = null
    var total: TextView? = null
    val listaPub = mutableListOf<ComplaintsResponse>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputPesquisa = view.findViewById(R.id.search_et_search)
        btnBusca = view.findViewById(R.id.search_btn_search)
        total = view.findViewById(R.id.search_result)

        recyclerView = view.findViewById(R.id.search_list_publication)
        //configurar RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(contex)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

        btnBusca!!.setOnClickListener {
            btnBusca!!.setOnClickListener {
                if (inputPesquisa!!.text.isNotEmpty() && inputPesquisa!!.text.isNotBlank())
                    getPlaca(inputPesquisa!!.text.toString())
            }
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
                                    listaPub.add(item)
                                }
                            }
                        }
                        total!!.text =
                            "Encontramos ${listaPub.size} resultados para essa pesquisa!"

                        val pasta =
                            getActivity()?.getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)
                        val id = pasta?.getString("idLogado", "")
                        if (id != null) {
                            val adapter = CustomAdapter(
                                listaPub,
                                id.toString().toInt(),
                                true,
                                contex
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