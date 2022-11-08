package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.go_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llBtnLogin.setOnClickListener {
            goToLogin()
        }

        binding.llBtnCadastro.setOnClickListener {
            goToCadastro()
        }

    }

    private fun goToLogin(){
        val telaLogin = Intent(this, Login::class.java)
        startActivity(telaLogin)
    }

    private fun goToCadastro(){
//        val telaCadastro = Intent(this, Cadastro1::class.java)
//        val telaCadastro = Intent(this, ItensSalvos::class.java)
        val telaCadastro = Intent(this, NovaDenuncia::class.java)
        startActivity(telaCadastro)
    }
}