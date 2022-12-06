package com.example.go_app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.go_app.models.SuccessResponse
import com.example.go_app.rest.Rest
import com.example.go_app.services.Publications
import com.example.go_app.services.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_usuario)
        val switch: Switch = findViewById(R.id.switch1)
        val pasta = getSharedPreferences(
            "CREDENCIAIS",
            MODE_PRIVATE
        )
        val editor = pasta.edit()


        val dark = pasta.getBoolean("dark", false)
        if (dark) {
            switch.isChecked = true
        }

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("dark", true)
                editor.commit()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("dark", false)
                editor.commit()
            }

        }

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

        val telaLogin = Intent(this, MainActivity::class.java)
        startActivity(telaLogin)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val index = Intent(this, IndexActivity::class.java)
        val publication = Intent(this, NovaDenuncia::class.java)
        val search = Intent(this, Search::class.java)
        val save = Intent(this, ItensSalvos::class.java)
        val config = Intent(this, ConfigUsuario::class.java)

        when(item.itemId){
            R.id.ic_index -> startActivity(index)
            R.id.ic_publication -> startActivity(publication)
            R.id.ic_search -> startActivity(search)
            R.id.ic_save -> startActivity(save)
            R.id.ic_settings -> startActivity(config)
        }
       return super.onOptionsItemSelected(item)
    }
}