package com.example.go_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.net.toFile
import com.example.go_app.databinding.ActivityNovaDenunciaBinding
import com.example.go_app.models.*
import com.example.go_app.rest.Rest
import com.example.go_app.rest.RestCep
import com.example.go_app.services.Cep
import com.example.go_app.services.Publications
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class NovaDenuncia : AppCompatActivity() {

    private lateinit var binding : ActivityNovaDenunciaBinding
    val REQUEST_CODE = 100

    var id = 1
    var estado = ""
    var cidade = ""
    var bairro = ""
    lateinit var colorProfile : String
    lateinit var colorMenu : String
    lateinit var dataFormatada : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaDenunciaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDenunciar.isEnabled = false

        var descricao = binding.etDescricao
        var placa = binding.etPlaca
        var data = binding.etData
        var codigoBo = binding.etCodigoBo

        //TODO: Pegar o id, colorProfile e colorMenu do offline
        colorProfile = ""
        colorMenu = ""
        id = 1

        descricao.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(descricao.text.isEmpty()){
                    descricao.error = "A descrição não pode ser vazia"
                    binding.btnDenunciar.isEnabled = false
                } else if(descricao.text.length <= 10){
                    descricao.error = "Deve ter mais de 10 caracteres"
                    binding.btnDenunciar.isEnabled = false
                } else if(descricao.text.length >= 256){
                    descricao.error = "Não pode ter mais de 256 caracteres"
                    binding.btnDenunciar.isEnabled = false
                } else {
                    binding.btnDenunciar.isEnabled = true
                }
            }

        })
        placa.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(placa.text.length == 7){
                    binding.btnDenunciar.isEnabled = true
                } else if(placa.text.isEmpty()){
                    placa.error = "A placa não pode ser vazia"
                } else{
                    descricao.error = "Placa inválida"
                }
            }

        })
        data.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(data.text.length == 10){
                    binding.btnDenunciar.isEnabled = true
                    var dia : String = data.text.substring(0, 2)
                    var mes : String = data.text.substring(3, 5)
                    var ano : String = data.text.substring(6, 10)
                    dataFormatada = "$ano-$mes-$dia"
                } else {
                    binding.btnDenunciar.isEnabled = false
                    data.setError("Insira uma data correta")
                }
            }
        })
        codigoBo.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO: Colocar a validação certa
                if(data.text.length == 10){
                    binding.btnDenunciar.isEnabled = true
                } else {
                    binding.btnDenunciar.isEnabled = false
                    codigoBo.error = "Insira um código válido"
                }
            }
        })

        binding.ivImagem.setOnClickListener {
            openGalleryForImage()
        }

        binding.ivSearch.setOnClickListener {
            preencherCampos()
        }

        binding.btnDenunciar.setOnClickListener {
            cadastrarDenuncia()
        }

    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            val uri = data?.data!!
            //TODO: Tratar data nula depois
            binding.ivImagem.setImageURI(uri) // handle chosen image
            println(uri)
            val file = uri.toFile()
            val inputStream = file.inputStream()
            val bytes = inputStream.readBytes()
            print(bytes);
        }
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

    private fun cadastrarDenuncia(){
        //TODO: Ver como pegar o tipo de denúncia
        var type = ""
        var descricao = binding.etDescricao.text.toString()
        var bo = binding.etCodigoBo.text.toString()
        //TODO: Ver como pegar o nome do motorista
        //Figma está faltando muita coisa!
        var driverName = ""
        var licensePlate = ""
        var state = binding.etEstado.text.toString()
        var city = binding.etCidade.text.toString()
        var district = bairro
        var dateTimeComplaint = dataFormatada
        val request = Rest.getInstance().create(Publications::class.java)
        val body = ComplaintRequest(
            type, descricao, bo, driverName, licensePlate, state, city, district, dateTimeComplaint
        )
        request.postPublication(id, body).enqueue(object : Callback<ComplaintsResponse>{
            override fun onResponse(call: Call<ComplaintsResponse>, response: Response<ComplaintsResponse>) {
                if(response.code() == 201){
                    Toast.makeText(this@NovaDenuncia, "Denúncia cadastrada!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@NovaDenuncia, "Erro no cadastro da denúncia", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ComplaintsResponse>, t: Throwable) {
                Toast.makeText(this@NovaDenuncia, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

