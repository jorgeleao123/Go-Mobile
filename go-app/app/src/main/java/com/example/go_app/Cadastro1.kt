package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.go_app.databinding.ActivityCadastro1Binding

class Cadastro1 : AppCompatActivity() {

    private lateinit var binding: ActivityCadastro1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastro1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var email = binding.etEmail
        var senha = binding.etSenha
        var confirmSenha = binding.etConfirmSenha

        email.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
                    binding.btnProximo.isEnabled = true
                } else {
                    binding.btnProximo.isEnabled = false
                    email.setError("Email inválido")
                }
            }
        })
        senha.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(senha.text.length >= 8){
                    binding.btnProximo.isEnabled = true
                } else {
                    binding.btnProximo.isEnabled = false
                    senha.setError("Senha deve ter no mínimo 8 caracteres")
                }
            }
        })
        confirmSenha.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(confirmSenha.text.toString().equals(senha.text.toString())){
                    binding.btnProximo.isEnabled = true
                } else {
                    binding.btnProximo.isEnabled = false
                    confirmSenha.setError("As senhas devem ser iguais")
                }
            }
        })

        binding.btnProximo.setOnClickListener{
            nextActivity()
        }

        binding.tvLogin.setOnClickListener {
            goToLogin()
        }

    }

    private fun nextActivity(){
        //TODO: mandar para outra activity os dados
        val telaCadastro2 = Intent(this, Cadastro2::class.java)
//        telaCadastro2.putExtra("email", binding.etEmail.text.toString())
//        telaCadastro2.putExtra("senha", binding.etSenha.text.toString())
        startActivity(telaCadastro2)
    }

    private fun goToLogin(){
        val telaLogin = Intent(this, Login::class.java)
        startActivity(telaLogin)
    }


}