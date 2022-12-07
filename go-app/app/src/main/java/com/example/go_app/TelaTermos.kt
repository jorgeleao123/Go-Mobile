package com.example.go_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.go_app.databinding.ActivityTelaTermosBinding
import com.example.go_app.models.UserRequest
import com.example.go_app.models.UserResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TelaTermos : AppCompatActivity() {

    private lateinit var binding: ActivityTelaTermosBinding
    lateinit var email: String
    lateinit var senha: String
    lateinit var nome: String
    lateinit var data: String
    lateinit var sexo: String
    lateinit var estado: String
    lateinit var cidade: String
    lateinit var bairro: String

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
        cidade = intent.getStringExtra("cidade").toString()
        bairro = intent.getStringExtra("bairro").toString()

        binding.button.visibility = View.INVISIBLE

        binding.checkBox.setOnClickListener {
            if (binding.button.visibility == View.INVISIBLE) {
                binding.button.visibility = View.VISIBLE
            } else {
                binding.button.visibility = View.INVISIBLE
            }
        }

        binding.button.setOnClickListener {
            cadastrarUsuario()
        }

    }

    private fun cadastrarUsuario() {
        val request = Rest.getInstance().create(Users::class.java)
        val body = UserRequest(
            nome, email, senha, "user", sexo, data, estado, cidade, bairro
        )
        request.postUser(body).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.code() == 201) {
                    Toast.makeText(this@TelaTermos, "Cadastrado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                    goToLogin()
                } else {
                    Toast.makeText(this@TelaTermos, "Erro no cadastro", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@TelaTermos, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun goToLogin() {
        val telaLogin = Intent(this, Login::class.java)
        startActivity(telaLogin)
    }
}