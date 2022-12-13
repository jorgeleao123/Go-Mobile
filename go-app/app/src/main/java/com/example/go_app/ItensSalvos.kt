package com.example.go_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.adapter.CustomAdapter
import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.ComplaintSavedController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItensSalvos(
    val contex: Context
) : Fragment() {
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_itens_salvos, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)

        //configurar RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(contex)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

        val pasta = getActivity()?.getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)

        val id = pasta?.getString("idLogado", "")
        if (id != null) {
            getMovies(id.toInt())
        }
    }

    private fun getMovies(id: Int) {
        val request = Rest.getInstance().create(ComplaintSavedController::class.java)

        request.getComplaintSave(id).enqueue(
            object : Callback<List<ComplaintsResponse>> {
                override fun onResponse(
                    call: Call<List<ComplaintsResponse>>,
                    response: Response<List<ComplaintsResponse>>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {
                        println("aqui")
                        val adapter = CustomAdapter(response.body()!!, id, false, contex)
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