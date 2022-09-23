package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.go_app.databinding.ActivityCadastro1Binding

class Cadastro1 : AppCompatActivity() {

    private lateinit var binding: ActivityCadastro1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastro1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProximo.setOnClickListener{
            nextActivity()
        }

        binding.tvLogin.setOnClickListener {
            goToLogin()
        }

    }

    private fun nextActivity(){
        val telaCadastro2 = Intent(this, Cadastro2::class.java)
        startActivity(telaCadastro2)
    }

    private fun goToLogin(){
        val telaLogin = Intent(this, Login::class.java)
        startActivity(telaLogin)
    }
}