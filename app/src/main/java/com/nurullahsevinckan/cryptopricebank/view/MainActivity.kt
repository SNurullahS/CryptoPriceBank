package com.nurullahsevinckan.cryptopricebank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurullahsevinckan.cryptopricebank.R
import com.nurullahsevinckan.cryptopricebank.adapter.CryptoAdapter
import com.nurullahsevinckan.cryptopricebank.adapter.CryptoViewHolder
import com.nurullahsevinckan.cryptopricebank.adapter.Listener
import com.nurullahsevinckan.cryptopricebank.databinding.ActivityMainBinding
import com.nurullahsevinckan.cryptopricebank.model.CryptoModel
import com.nurullahsevinckan.cryptopricebank.services.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),Listener {
    // https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    private lateinit var binding :ActivityMainBinding
    private val BASE_URL : String = "https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private  var recyclerViewAdapter : CryptoAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        loadData()
    }

    private fun loadData(){
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object  : Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {listCryptoModel ->
                        cryptoModels =ArrayList(listCryptoModel)
                        cryptoModels?.let { cryptoModels->
                            recyclerViewAdapter = CryptoAdapter(cryptoModels,this@MainActivity)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }



                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this," ${cryptoModel.currency} is clicked",Toast.LENGTH_LONG).show()
    }

}