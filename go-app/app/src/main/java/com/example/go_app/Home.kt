package com.example.go_app

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.go_app.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val indexPage = IndexActivity(this)
    private val configPage = ConfigUsuario(this)
    private val savePage = ItensSalvos(this)
    private val searchPage = Search(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pasta = getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(indexPage)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_index -> replaceFragment(indexPage)
                R.id.ic_settings -> replaceFragment(configPage)
                R.id.ic_save -> replaceFragment(savePage)
                R.id.ic_search -> replaceFragment(searchPage)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragment_container, fragment)
        transition.commit()
    }
}