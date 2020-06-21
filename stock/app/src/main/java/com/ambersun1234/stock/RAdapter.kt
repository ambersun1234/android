package com.ambersun1234.stock

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RAdapter(
    val stock_name: ArrayList<String>,
    val stock_code: ArrayList<String>,
    val stock_volume: ArrayList<String>,
    val mcontext: Context
) : RecyclerView.Adapter<RAdapter.myViewHolder>() {

    companion object {
        lateinit var ccontext: Context
    }

    init {
        ccontext = this.mcontext
    }

    inner class myViewHolder : RecyclerView.ViewHolder {
        var _name: TextView
        var _volume: TextView
        var _dividend: TextView

        constructor(tview: View) : super(tview) {
            this._name = tview.findViewById(R.id.stock_name)
            this._volume = tview.findViewById(R.id.stock_volume)
            this._dividend = tview.findViewById(R.id.stock_dividend)
        }
    }

    override fun getItemCount(): Int {
        return this.stock_code.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder._name.text = this.stock_name[position]
        holder._volume.text = this.stock_volume[position]
        holder._dividend.text = this.stock_code[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val txview: View = LayoutInflater.from(parent.context).inflate(
            R.layout.stock,
            parent,
            false
        )

        return myViewHolder(txview)

    }
}