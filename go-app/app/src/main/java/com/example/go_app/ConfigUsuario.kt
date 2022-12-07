package com.example.go_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.go_app.models.SuccessResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigUsuario(
    val contex: Context
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_config_usuario, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn: Button = view.findViewById(R.id.btn_desativar)
        btn.setOnClickListener {
            desativaUsuario()
        }
        val btnSair: Button = view.findViewById(R.id.btn_sair)
        btnSair.setOnClickListener {
            val pasta = getActivity()?.getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)
            pasta?.getString("idLogado", "")
            val editor = pasta?.edit()
            editor?.putString("idLogado", "")
            editor?.commit()
        }
    }


    fun desativaUsuario() {
        var idLogado: String? = null
        val pasta = getActivity()?.getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)
        idLogado = pasta?.getString("idLogado", "")
        val request = Rest.getInstance().create(Users::class.java)

        request.deleteUser(idLogado.toString().toInt()).enqueue(
            object : Callback<SuccessResponse> {
                override fun onResponse(
                    call: Call<SuccessResponse>,
                    response: Response<SuccessResponse>
                ) {
                }

                override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                }

            }
        )
        val text = "Conta Desativada"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(contex, text, duration)
        toast.show()

        val telaLogin = Intent(contex, MainActivity::class.java)
        startActivity(telaLogin)
    }

}