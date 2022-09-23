package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.go_app.databinding.ActivityCadastro2Binding

class Cadastro2 : AppCompatActivity() {

    private lateinit var binding: ActivityCadastro2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastro2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProximo.setOnClickListener{
            nextActivity()
        }

        binding.btnAnterior.setOnClickListener{
            previousActivity()
        }

    }

    private fun nextActivity(){
        val telaCadastro3 = Intent(this, Cadastro3::class.java)
        startActivity(telaCadastro3)
    }

    private fun previousActivity(){
        val telaCadastro1 = Intent(this, Cadastro1::class.java)
        startActivity(telaCadastro1)
    }

}