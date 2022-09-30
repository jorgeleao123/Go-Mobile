package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.go_app.databinding.ActivityCadastro3Binding
import com.example.go_app.models.CepResponse
import com.example.go_app.rest.RestCep
import com.example.go_app.services.Cep
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class Cadastro3 : AppCompatActivity() {

    private lateinit var binding: ActivityCadastro3Binding
//    val email = intent.getStringExtra("email")
//    val senha = intent.getStringExtra("senha")
//    val nome = intent.getStringExtra("nome")
//    val data = intent.getStringExtra("data")

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

    private fun preencherCampos(){
        val cep = binding.etCep.text.toString()

        val request = RestCep.getInstance().create(Cep::class.java)

        request.getEnderecoByCEP(cep).enqueue(object : Callback<CepResponse>{
            override fun onResponse(call: Call<CepResponse>, response: Response<CepResponse>) {
                binding.etEstado.setText(response.body()!!.uf)
                binding.etCidade.setText(response.body()!!.localidade)
            }

            override fun onFailure(call: Call<CepResponse>, t: Throwable) {
                binding.etCep.setError("O CEP não existe")
            }
        })

    }
}
