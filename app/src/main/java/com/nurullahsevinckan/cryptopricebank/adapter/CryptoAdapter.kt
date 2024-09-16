package com.nurullahsevinckan.cryptopricebank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nurullahsevinckan.cryptopricebank.R
import com.nurullahsevinckan.cryptopricebank.databinding.CryptoRowBinding
import com.nurullahsevinckan.cryptopricebank.model.CryptoModel

class CryptoAdapter(var cryptoList :ArrayList<CryptoModel>, private val listener: Listener)
    : RecyclerView.Adapter<CryptoViewHolder>() {

    private  val colors : Array<String> = arrayOf("#CD5C5C","#2980b9","#1abc9c","#b7950b","#e67e22","#922b21",
        "#34495e","#6c3483")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = CryptoRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position,listener)
    }
}