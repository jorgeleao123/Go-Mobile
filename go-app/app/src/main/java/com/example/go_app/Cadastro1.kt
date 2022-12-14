package com.example.go_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import com.example.go_app.databinding.ActivityCadastro1Binding

class Cadastro1 : AppCompatActivity() {

    private lateinit var binding: ActivityCadastro1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastro1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var isShowing1 = false
        var isShowing2 = false

        binding.btnProximo.isEnabled = false

        var email = binding.etEmail
        var senha = binding.etSenha
        var confirmSenha = binding.etConfirmSenha

        email.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() ||
                        email.text.toString().isEmpty()){
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
                if(senha.text.length >= 8 || senha.text.toString().isEmpty()){
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

    }

    private fun nextActivity(){
        val telaCadastro2 = Intent(this, Cadastro2::class.java)
        telaCadastro2.putExtra("email", binding.etEmail.text.toString())
        telaCadastro2.putExtra("senha", binding.etSenha.text.toString())
        startActivity(telaCadastro2)
    }

    private fun goToLogin(){
        val telaLogin = Intent(this, Login::class.java)
        startActivity(telaLogin)
    }


}