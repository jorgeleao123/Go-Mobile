package com.example.go_app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.adapter.CustomNotification
import com.example.go_app.models.NotificationResponse
import com.example.go_app.models.SuccessResponse
import com.example.go_app.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Notification : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var btnBack: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        recyclerView = findViewById(R.id.list_notification)
        btnBack = findViewById(R.id.btn_volta)
        //configurar RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        val pasta = getSharedPreferences(
            "CREDENCIAIS",
            MODE_PRIVATE
        )
        btnBack!!.setOnClickListener {
            val telaIndex = Intent(this, IndexActivity::class.java)
            startActivity(telaIndex)
        }
        val id = pasta.getString("idLogado", "")
        if (id != null) {
            getMovies(id.toInt())
        }
    }

    private fun getMovies(id: Int) {
        val request =
            Rest.getInstance().create(com.example.go_app.services.Notification::class.java)

        request.getNotifications(id).enqueue(
            object : Callback<List<NotificationResponse>> {
                override fun onResponse(
                    call: Call<List<NotificationResponse>>,
                    response: Response<List<NotificationResponse>>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {
                        val listNotification = mutableListOf<NotificationResponse>()
                        println("aqui")
                        for (item in (response.body()!!)) {
                            if (!item.hasViewed) {
                                listNotification.add(item)
                            }
                        }
                        val adapter = CustomNotification(listNotification)
                        recyclerView?.adapter = adapter
                        viewNotification(id)
                    }

                }

                override fun onFailure(call: Call<List<NotificationResponse>>, t: Throwable) {
                    println("não foi")
                }

            }
        )
    }

    private fun viewNotification(id: Int) {
        val request =
            Rest.getInstance().create(com.example.go_app.services.Notification::class.java)

        request.patchNotifications(id).enqueue(
            object : Callback<SuccessResponse> {
                override fun onResponse(
                    call: Call<SuccessResponse>,
                    response: Response<SuccessResponse>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {
                        println("aqui")


                    }
                }

                override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                    println("não foi")
                }

            }
        )

    }
}
