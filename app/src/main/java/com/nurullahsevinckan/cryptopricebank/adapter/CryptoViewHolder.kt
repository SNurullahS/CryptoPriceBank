package com.nurullahsevinckan.cryptopricebank.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nurullahsevinckan.cryptopricebank.R
import com.nurullahsevinckan.cryptopricebank.databinding.ActivityMainBinding
import com.nurullahsevinckan.cryptopricebank.databinding.CryptoRowBinding
import com.nurullahsevinckan.cryptopricebank.model.CryptoModel

class CryptoViewHolder(val binding: CryptoRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cryptoModel : CryptoModel,colors: Array<String>,position : Int,listener: Listener){

    itemView.setOnClickListener {
        listener.onItemClick(cryptoModel)
    }
        itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
        binding.textName.text = cryptoModel.currency
        binding.cryptoPrice.text = cryptoModel.price
    }
}