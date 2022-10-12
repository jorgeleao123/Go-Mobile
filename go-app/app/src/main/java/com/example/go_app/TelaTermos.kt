package com.example.go_app

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.go_app.databinding.ActivityTelaTermosBinding
import com.example.go_app.models.UserRequest
import com.example.go_app.models.UserResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class TelaTermos : AppCompatActivity() {

    private  lateinit var binding: ActivityTelaTermosBinding
    lateinit var email : String
    lateinit var senha : String
    lateinit var nome : String
    lateinit var data : String
    lateinit var sexo : String
    lateinit var estado : String
    lateinit var cidade : String
    lateinit var bairro : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaTermosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("email").toString()
        senha = intent.getStringExtra("senha").toString()
        nome = intent.getStringExtra("nome").toString()
        data = intent.getStringExtra("data").toString()
        sexo = intent.getStringExtra("sexo").toString()
        estado = intent.getStringExtra("estado").toString()
        cidade = intent.getStringExtra("nome").toString()
        bairro = intent.getStringExtra("bairro").toString()

        binding.checkBox.setOnClickListener {
            binding.button.isEnabled = !binding.checkBox.isChecked
        }

        binding.button.setOnClickListener {
            cadastrarUsuario()
        }

    }

    private fun cadastrarUsuario(){
        val request = Rest.getInstance().create(Users::class.java)
        val body = UserRequest(
            nome, email, senha,"user", sexo, data, estado, cidade, bairro)
        request.postUser(body).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.code() == 201){
                    binding.checkBox.text = "Sucesso"
                }
                else {
                    binding.checkBox.text = "Erro"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                binding.checkBox.text = "Erro"
            }
        })
    }
}