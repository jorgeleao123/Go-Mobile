package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.go_app.databinding.ActivityCadastro3Binding

class Cadastro3 : AppCompatActivity() {

    private lateinit var binding: ActivityCadastro3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastro3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProximo.setOnClickListener{
            nextActivity()
        }

        binding.btnAnterior.setOnClickListener{
            previousActivity()
        }

    }

    private fun nextActivity(){

    }

    private fun previousActivity(){
        val telaCadastro2 = Intent(this, Cadastro2::class.java)
        startActivity(telaCadastro2)
    }
}