package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun goToLogin(v: View){
        val telaCadastro = Intent(this, Cadastro1::class.java)
        startActivity(telaCadastro)
    }
}