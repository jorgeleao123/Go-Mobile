package com.example.go_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
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

class IndexActivity(
    val contex: Context
) : Fragment() {
    var recyclerView: RecyclerView? = null
    var btnPerfil: FrameLayout? = null
    var inputPesquisa: TextView? = null
    var btnNotification: ImageView? = null
    var idLogado: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_index,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.index_list_publication)
        btnPerfil = view.findViewById(R.id.index_profile)
        inputPesquisa = view.findViewById(R.id.new_den)
        btnNotification = view.findViewById(R.id.index_notification)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(contex)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        val pasta = getActivity()?.getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)

        btnPerfil!!.setOnClickListener {
            val telaPerfil = Intent(contex, Perfil::class.java)
            startActivity(telaPerfil)
        }
        inputPesquisa!!.setOnClickListener {
            irPesquisa()
        }
        btnNotification!!.setOnClickListener {
            goNotification()
        }
        idLogado = pasta?.getString("idLogado", "")
        val nome = pasta?.getString("nomeLogado", "")
        if (idLogado != null) {
            var nomeTela: TextView? = null
            nomeTela = view.findViewById(R.id.index_text_name)
            var primeiraLetra: TextView? = null
            primeiraLetra = view.findViewById(R.id.tv_valor)
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
                                contex
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
                        val pasta =
                            getActivity()?.getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)
                        val editor = pasta?.edit()
                        editor?.putString(
                            "cidadeLogado",
                            "${response.body()!![0].city}, ${response.body()!![0].district}"
                        )
                        editor?.commit()
                        var cidade: TextView? = null
                        cidade = view?.findViewById(R.id.index_text_city)
                        cidade?.text =
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
        val tela = Intent(
            contex,
            NovaDenuncia::class.java
        )

        startActivity(tela)
    }

    private fun goNotification() {
        val notification = Intent(
            contex,
            Notification::class.java
        )

        startActivity(notification)
    }

}
