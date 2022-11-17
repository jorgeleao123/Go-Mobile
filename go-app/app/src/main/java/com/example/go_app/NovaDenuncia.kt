package com.example.go_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.go_app.databinding.ActivityNovaDenunciaBinding

class NovaDenuncia : AppCompatActivity() {

    private lateinit var binding : ActivityNovaDenunciaBinding
    val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaDenunciaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivImagem.setOnClickListener {
            openGalleryForImage()
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
            binding.ivImagem.setImageURI(data?.data) // handle chosen image
        }
    }

}

