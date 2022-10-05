package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.go_app.databinding.ActivityCadastro2Binding

class Cadastro2 : AppCompatActivity() {

    private lateinit var binding: ActivityCadastro2Binding
    lateinit var email : String
    lateinit var senha : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastro2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var nome = binding.etNome
        var data = binding.etData
        email = intent.getStringExtra("email")!!
        senha = intent.getStringExtra("senha")!!

        nome.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(nome.text.length >= 3){
                    binding.btnProximo.isEnabled = true
                } else {
                    binding.btnProximo.isEnabled = false
                    nome.setError("O nome deve ter pelo menos 3 caracteres")
                }
            }

        })
        data.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(true){
                    binding.btnProximo.isEnabled = true
                } else {
                    binding.btnProximo.isEnabled = false
                    data.setError("Insira uma data correta")
                }
            }

        })

        binding.btnProximo.setOnClickListener{
            print(email)
            print(senha)
            print(nome)
            nextActivity()
        }

        binding.btnAnterior.setOnClickListener{
            previousActivity()
        }

    }

    private fun nextActivity(){
        val telaCadastro3 = Intent(this, Cadastro3::class.java)
        telaCadastro3.putExtra("email", email)
        telaCadastro3.putExtra("senha", senha)
        telaCadastro3.putExtra("nome", binding.etNome.text.toString())
        telaCadastro3.putExtra("data", binding.etData.text.toString())
        //TODO: Passar o sexo tbm
        startActivity(telaCadastro3)
    }

    private fun previousActivity(){
        val telaCadastro1 = Intent(this, Cadastro1::class.java)
        startActivity(telaCadastro1)
    }

}