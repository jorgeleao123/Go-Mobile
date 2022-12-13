package com.example.go_app

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.example.go_app.databinding.ActivityLoginBinding
import com.example.go_app.models.UserResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var isShowing = false

        binding.tvRegistrar.setOnClickListener {
            goToCadastro()
        }

        binding.btnLogar.setOnClickListener {
            logarUsuario()
        }

        binding.ivHide.setOnClickListener {
            if (isShowing) {
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                isShowing = false
            } else {
                binding.etPassword.inputType = 1
                isShowing = true
            }
        }

    }

    private fun goToCadastro() {
        val telaCadastro1 = Intent(this, Cadastro1::class.java)
        startActivity(telaCadastro1)
    }

    private fun logarUsuario() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        val request = Rest
            .getInstance()
            .create(Users::class.java)

        request.loginUser(email, password).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.code() == 400) {
                    binding.tvErro.text = "Email e/ou senha inválidos"
                } else if(response.code() == 403){
                    binding.tvErro.text = "Erro no servidor"
                }
                else if(response.body()?.status.equals("inativo")){
                        binding.tvErro.text = "Conta Desativada"
                } else if(response.code() == 200){
                    val pasta = getSharedPreferences(
                        "CREDENCIAIS",
                        MODE_PRIVATE
                    )
                    val editor = pasta.edit()
                    editor.putString("idLogado", response.body()?.id.toString())
                    editor.commit()
                    editor.putString("nomeLogado", response.body()?.name.toString())
                    editor.commit()
                    editor.putString("buscasFeitas", response.body()?.searchCounter.toString())
                    editor.commit()
                    editor.putString("colorProfile", response.body()?.colorProfile.toString())
                    editor.commit()
                    editor.putString("colorMenu", response.body()?.colorMenu.toString())
                    editor.commit()
                    entrarHome()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                binding.tvErro.text = "Erro no login"
            }
        })

    }

    private fun entrarHome() {
        val telaHome = Intent(this, Home::class.java)
        startActivity(telaHome)
    }

}