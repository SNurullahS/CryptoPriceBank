package com.nurullahsevinckan.cryptopricebank.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurullahsevinckan.cryptopricebank.R
import com.nurullahsevinckan.cryptopricebank.adapter.CryptoAdapter
import com.nurullahsevinckan.cryptopricebank.adapter.Listener
import com.nurullahsevinckan.cryptopricebank.databinding.ActivityMainBinding
import com.nurullahsevinckan.cryptopricebank.model.CryptoModel
import com.nurullahsevinckan.cryptopricebank.services.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),Listener {
    // https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    private lateinit var binding :ActivityMainBinding
    private val BASE_URL : String = "https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private  var recyclerViewAdapter : CryptoAdapter? = null

    //Disposable: use and free it
    private var compositDisposable : CompositeDisposable? = null



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
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(CryptoAPI::class.java)


        compositDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io()) // Working on background thread
            .observeOn(AndroidSchedulers.mainThread()) // Process data on main thread
            .subscribe(this::handleResponse) // When this work done and return data handleResponse get data
        )



        //Just using Retrofit way :
        /*

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

         */



    }


    private  fun handleResponse(cryptoList : List<CryptoModel>){

        cryptoModels =ArrayList(cryptoList)

        cryptoModels?.let { cryptoModels->
            recyclerViewAdapter = CryptoAdapter(cryptoModels,this@MainActivity)
            binding.recyclerView.adapter = recyclerViewAdapter
        }
    }
    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this," ${cryptoModel.currency} is clicked",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        compositDisposable?.clear() // Clear all API calls when app dead
    }

}