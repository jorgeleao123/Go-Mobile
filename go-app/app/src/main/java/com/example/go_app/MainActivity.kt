package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.go_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnJaTenhoConta.setOnClickListener {
            goToLogin()
        }

    }

    fun goToLogin(){
        val telaCadastro = Intent(this, Cadastro1::class.java)
        startActivity(telaCadastro)
    }
}