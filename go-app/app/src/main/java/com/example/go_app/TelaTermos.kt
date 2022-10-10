package com.example.go_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.go_app.databinding.ActivityTelaTermosBinding
import com.example.go_app.models.UserRequest
import com.example.go_app.models.UserResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TelaTermos : AppCompatActivity() {

    private  lateinit var binding: ActivityTelaTermosBinding
    lateinit var email : String
    lateinit var senha : String
    lateinit var nome : String
    lateinit var data : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaTermosBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun cadastrarUsuario(){
        val request = Rest.getInstance().create(Users::class.java)
        val body = UserRequest(email, "","","", "", "", "", "", "")
        request.postUser(body).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}