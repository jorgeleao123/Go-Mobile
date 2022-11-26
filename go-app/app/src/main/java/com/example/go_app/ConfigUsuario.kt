package com.example.go_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.go_app.models.SuccessResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_usuario)
    }


     fun desativaUsuario(v: View) {
        var idLogado: String? = null
        val pasta = getSharedPreferences(
            "CREDENCIAIS",
            MODE_PRIVATE
        )
        idLogado = pasta.getString("idLogado", "")
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
        val toast = Toast.makeText(this, text, duration)
        toast.show()

        val telaLogin= Intent(this, MainActivity::class.java)
        startActivity(telaLogin)
    }
}