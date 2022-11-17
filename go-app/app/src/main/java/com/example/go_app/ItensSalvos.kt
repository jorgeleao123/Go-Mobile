    package com.example.go_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.adapter.CustomAdapter
import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.ComplaintSavedController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

    class ItensSalvos : AppCompatActivity() {
        var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itens_salvos)
        recyclerView = findViewById(R.id.recyclerView)

        //configurar adapter
        val adapter = CustomAdapter()

        //configurar RecyclerView

        //configurar RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        val pasta = getSharedPreferences(
            "CREDENCIAIS",
            MODE_PRIVATE
        )

        val id = pasta.getString("idLogado","")
        if (id != null) {
            getMovies(id.toInt())
        }
    }

        private fun getMovies(id: Int){
            val listMovies: MutableList<List<ComplaintsResponse>> = mutableListOf()
            val request = Rest.getInstance().create(ComplaintSavedController::class.java)

            request.getComplaintSave(id).enqueue(
                object : Callback<List<ComplaintsResponse>> {
                    override fun onResponse(
                        call: Call<List<ComplaintsResponse>>,
                        response: Response<List<ComplaintsResponse>>
                    ) {
                        if(response.code() == 404){
                            println("não foi")
                        }else{
                            println("aqui")
                            listMovies.add(response.body()!!)
                        }

                    }

                    override fun onFailure(call: Call<List<ComplaintsResponse>>, t: Throwable) {
                        println("não foi")
                    }

                }
            )
        }
}