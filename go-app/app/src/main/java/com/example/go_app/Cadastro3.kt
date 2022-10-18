package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.go_app.databinding.ActivityCadastro3Binding
import com.example.go_app.models.CepResponse
import com.example.go_app.rest.RestCep
import com.example.go_app.services.Cep
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Cadastro3 : AppCompatActivity() {

    private lateinit var binding: ActivityCadastro3Binding
    lateinit var email : String
    lateinit var senha : String
    lateinit var nome : String
    lateinit var data : String
    lateinit var sexo : String
    lateinit var estado : String
    lateinit var cidade : String
    lateinit var bairro : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastro3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("email").toString()
        nome = intent.getStringExtra("nome").toString()
        senha = intent.getStringExtra("senha").toString()
        data = intent.getStringExtra("data").toString()
        sexo = intent.getStringExtra("sexo").toString()
        estado = ""
        cidade = ""
        bairro = ""

        binding.btnProximo.setOnClickListener{
            nextActivity()
        }

        binding.btnAnterior.setOnClickListener{
            previousActivity()
        }

        binding.ivSearch.setOnClickListener {
            preencherCampos()
        }

    }

    private fun nextActivity(){
        val telaTermos = Intent(this, TelaTermos::class.java)
        telaTermos.putExtra("email", email)
        telaTermos.putExtra("senha", senha)
        telaTermos.putExtra("nome", nome)
        telaTermos.putExtra("data", data)
        telaTermos.putExtra("sexo", sexo)
        telaTermos.putExtra("estado", estado)
        telaTermos.putExtra("cidade", cidade)
        telaTermos.putExtra("bairro", bairro)
        startActivity(telaTermos)
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
                estado = response.body()!!.uf
                cidade = response.body()!!.localidade
                bairro = response.body()!!.bairro
                binding.etEstado.setText(response.body()!!.uf)
                binding.etCidade.setText(response.body()!!.localidade)
            }

            override fun onFailure(call: Call<CepResponse>, t: Throwable) {
                binding.etCep.setError("O CEP não existe")
            }
        })

    }
}

