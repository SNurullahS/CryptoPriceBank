package com.nurullahsevinckan.cryptopricebank.model

import com.google.gson.annotations.SerializedName
//397
data class CryptoModel(
    @SerializedName("currency")
    val currency : String,
    @SerializedName("price")
    val price: String
)