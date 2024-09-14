package com.nurullahsevinckan.cryptopricebank.adapter

import com.nurullahsevinckan.cryptopricebank.model.CryptoModel

interface Listener {
    fun onItemClick(cryptoModel : CryptoModel)
}