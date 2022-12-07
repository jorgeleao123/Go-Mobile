package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.Toast
import com.example.go_app.databinding.ActivityEditarPerfilBinding
import com.example.go_app.models.UserAttRequest
import com.example.go_app.models.UserAttResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarPerfil : AppCompatActivity() {

    private lateinit var binding : ActivityEditarPerfilBinding
    var idLogado : String? = null;
    var colorProfile : String? = ""
    var colorMenu : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seta.setOnClickListener {
            goToProfile()
        }

        val pasta = getSharedPreferences(
            "CREDENCIAIS",
            MODE_PRIVATE
        )
        idLogado = pasta.getString("idLogado", "0")
        colorMenu = pasta.getString("colorMenu", "#144D6C")
        colorProfile = pasta.getString("colorProfile", "#1F869D")

        var email = binding.etEmail
        var senha = binding.etSenha
        var confirmSenha = binding.etConfirmSenha

        var isShowing1 = false
        var isShowing2 = false

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() ||
                    email.text.toString().isEmpty()){
                    binding.btnSalvar.isEnabled = true
                } else {
                    binding.btnSalvar.isEnabled = false
                    email.setError("Email inválido")
                }
            }
        })
        senha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(senha.text.length >= 8 || senha.text.toString().isEmpty()){
                    binding.btnSalvar.isEnabled = true
                } else {
                    binding.btnSalvar.isEnabled = false
                    senha.setError("Senha deve ter no mínimo 8 caracteres")
                }
            }
        })
        confirmSenha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(confirmSenha.text.toString().equals(senha.text.toString())){
                    binding.btnSalvar.isEnabled = true
                } else {
                    binding.btnSalvar.isEnabled = false
                    confirmSenha.setError("As senhas devem ser iguais")
                }
            }
        })

        binding.ivHide.setOnClickListener {
            if(isShowing1){
                binding.etSenha.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                isShowing1 = false
            } else {
                binding.etSenha.inputType = 1
                isShowing1 = true
            }
        }
        binding.ivHide2.setOnClickListener {
            if(isShowing2){
                binding.etConfirmSenha.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                isShowing2 = false
            } else {
                binding.etConfirmSenha.inputType = 1
                isShowing2 = true
            }
        }

        binding.btnSalvar.setOnClickListener {
            salvarPerfil()
        }

    }

    private fun salvarPerfil(){

        var name = binding.etNome.text.toString()
        var email = binding.etEmail.text.toString()
        var password = binding.etSenha.text.toString()
        var id = idLogado?.toInt()

        val request = Rest.getInstance().create(Users::class.java)
        val body = UserAttRequest(
            name, email, password, colorProfile!!, colorMenu!!
        )
        request.attUser(id!!, body).enqueue(object : Callback<UserAttResponse> {
            override fun onResponse(call: Call<UserAttResponse>, response: Response<UserAttResponse>) {
                if(response.code() == 200){
                    Toast.makeText(this@EditarPerfil, "Perfil atualizado!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@EditarPerfil, "Erro na atualização", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserAttResponse>, t: Throwable) {
                Toast.makeText(this@EditarPerfil, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun goToProfile(){
        val telaProfile= Intent(this, Perfil::class.java)
        startActivity(telaProfile)
    }


}