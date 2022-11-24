package com.example.go_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.adapter.CustomAdapter
import com.example.go_app.databinding.ActivityPerfilBinding
import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.ComplaintSavedController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        this.setRecyclerView()
//        this.getUserRegisters()
    }

    fun setRecyclerView() {
        recyclerView = findViewById(R.id.listaItem)
        setContentView(binding.root)
        //configurar adapter
//        val adapter = CustomAdapter()
        //configurar RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
//        recyclerView?.adapter = adapter
    }

//    fun getUserRegisters() {
//        val request = Rest.getInstance().create(ComplaintSavedController::class.java)
//        val pasta = getSharedPreferences("CREDENCIAIS", MODE_PRIVATE)
//        val id = pasta.getString("idLogado","")
//        request.getComplaintSave(id!!.toInt()).enqueue(object : Callback<List<ComplaintsResponse>> {
//            override fun onResponse(
//                call: Call<ComplaintsResponse>,
//                response: Response<ComplaintsResponse>
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<ComplaintsResponse>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })
//
//    }

}