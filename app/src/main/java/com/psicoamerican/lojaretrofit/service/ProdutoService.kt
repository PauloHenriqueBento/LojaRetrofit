package com.psicoamerican.lojaretrofit.service

import com.psicoamerican.lojaretrofit.model.Produto
import retrofit2.Call
import retrofit2.http.GET

interface ProdutoService {

    @GET("/android/rest/produto")
    fun listar(): Call<List<Produto>>
}