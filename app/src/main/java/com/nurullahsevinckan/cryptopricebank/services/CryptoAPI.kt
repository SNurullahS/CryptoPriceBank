package com.nurullahsevinckan.cryptopricebank.services

import com.nurullahsevinckan.cryptopricebank.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    //GET, POST, UPDATE, DELETE requests
    //https://raw.githubusercontent.com/
    //atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    // We convert Call to Observable for using RxJava working asynchronous.
    fun getData(): Observable<List<CryptoModel>>

//  fun getData(): Call<List<CryptoModel>>
}