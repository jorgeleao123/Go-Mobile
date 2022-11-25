package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import com.example.go_app.databinding.ActivityCadastro2Binding

class Cadastro2 : AppCompatActivity() {

    private lateinit var binding: ActivityCadastro2Binding
    lateinit var email : String
    lateinit var senha : String
    lateinit var sexo : String
    lateinit var dataFormatada : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastro2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProximo.isEnabled = false

        var nome = binding.etNome
        var data = binding.etData
        sexo = ""
        dataFormatada = ""
        email = intent.getStringExtra("email").toString()
        senha = intent.getStringExtra("senha").toString()

        nome.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(nome.text.length >= 3 || nome.text.toString().isEmpty()){
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
                if(data.text.length == 10){
                    binding.btnProximo.isEnabled = true
                    var dia : String = data.text.substring(0, 2)
                    var mes : String = data.text.substring(3, 5)
                    var ano : String = data.text.substring(6, 10)
                    dataFormatada = ano + "-" + mes + "-" + dia
                } else {
                    binding.btnProximo.isEnabled = false
                    data.setError("Insira uma data correta")
                }
            }
        })

        binding.rbFeminino.setOnClickListener {
            sexo = "F"
        }
        binding.rbMasculino.setOnClickListener {
            sexo = "M"

        }
        binding.rbNenhum.setOnClickListener {
            sexo = "0"

        }

        binding.btnProximo.setOnClickListener{
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
        telaCadastro3.putExtra("data", dataFormatada)
        telaCadastro3.putExtra("sexo", sexo)
        startActivity(telaCadastro3)
    }

    private fun previousActivity(){
        val telaCadastro1 = Intent(this, Cadastro1::class.java)
        startActivity(telaCadastro1)
    }

}