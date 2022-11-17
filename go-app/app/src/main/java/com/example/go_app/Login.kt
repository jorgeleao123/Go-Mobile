package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.example.go_app.databinding.ActivityLoginBinding
import com.example.go_app.models.UserResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

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

        binding.btnLogar.setOnClickListener{
            logarUsuario()
        }

        binding.ivHide.setOnClickListener {
            if(isShowing){
                binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                isShowing = false
            } else {
                binding.etPassword.inputType = 1
                isShowing = true
            }
        }

    }

    private fun goToCadastro(){
        val telaCadastro1 = Intent(this, Cadastro1::class.java)
        startActivity(telaCadastro1)
    }

    private fun logarUsuario(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        val request = Rest
            .getInstance()
            .create(Users::class.java)

        request.loginUser(email, password).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.code() == 400){
                    binding.tvErro.text = "Email e/ou senha inválidos"
                } else {
                    //TODO: Guardar informações do user offline
                    val pasta = getSharedPreferences(
                        "CREDENCIAIS",
                        MODE_PRIVATE
                    )
                    val editor = pasta.edit()
                    editor.putString("idLogado", response.body()?.id.toString())
                    editor.commit()
                    entrarHome()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                binding.tvErro.text = "Erro no login"
            }
        })

    }

    private fun entrarHome(){
        val telaHome = Intent(this, ItensSalvos::class.java)
        startActivity(telaHome)
    }

}