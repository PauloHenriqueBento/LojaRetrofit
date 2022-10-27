package com.psicoamerican.lojaretrofit.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.psicoamerican.lojaretrofit.databinding.ActivityMainBinding
import com.psicoamerican.lojaretrofit.model.Produto
import com.psicoamerican.lojaretrofit.service.ProdutoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chamarApiListProd()
    }

    fun chamarApiListProd() {
        //1 - Criar uma instância do Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://oficinacordova.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //2 - Criar uma instância do serviço
        val service = retrofit.create(ProdutoService::class.java)

        //3 - Criar uma chamada
        val chamada = service.listar() //Possivelmente aqui

        //4 - Definir um callback de retorno
        val callback = object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    val listarProduto = response.body()

                    val nomeProduto = listarProduto?.first()?.nomeProduto

                    alert("Sucesso", "Nome do primeiro Produto: $nomeProduto")
                } else {
                    alert("Erro", response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                alert("Erro", t.message.toString())
            }

        }

        //5 - Executar a chamada
        chamada.enqueue(callback)
    }

    fun alert(titulo: String, msg: String){
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(msg)
            .setPositiveButton("OK", null)
            .create()
            .show()

    }
}