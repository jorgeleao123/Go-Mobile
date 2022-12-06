    package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        //configurar RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
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
                            val adapter = CustomAdapter(response.body()!!,id,false,applicationContext)
                            recyclerView?.adapter = adapter
                        }

                    }

                    override fun onFailure(call: Call<List<ComplaintsResponse>>, t: Throwable) {
                        println("não foi")
                    }

                }
            )
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val index = Intent(this, IndexActivity::class.java)
            val publication = Intent(this, NovaDenuncia::class.java)
            val search = Intent(this, Search::class.java)
            val save = Intent(this, ItensSalvos::class.java)
            val config = Intent(this, ConfigUsuario::class.java)

            when(item.itemId){
                R.id.ic_index -> startActivity(index)
                R.id.ic_publication -> startActivity(publication)
                R.id.ic_search -> startActivity(search)
                R.id.ic_save -> startActivity(save)
                R.id.ic_settings -> startActivity(config)
            }
            return super.onOptionsItemSelected(item)
        }
}