package com.example.go_app.services

import com.example.go_app.models.CepResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Cep {

    @GET("{CEP}/json/")
    fun getEnderecoByCEP(@Path("CEP") CEP : String) : Call<CepResponse>

    @GET("{estado}/{cidade}/{endereco}/json/")
    fun getCEPByCidadeEstadoEndereco(@Path("estado") estado: String,
                                     @Path("cidade") cidade: String,
                                     @Path("endereco") endereco: String): Call<List<CepResponse>>

}