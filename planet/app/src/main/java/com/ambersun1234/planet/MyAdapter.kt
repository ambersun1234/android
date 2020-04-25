package com.ambersun1234.planet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.re_layout.view.*

class myAdapter(
    private val zhDataset: Array<String>,
    private val enDataset: Array<String>) : RecyclerView.Adapter<myAdapter.myViewHolder>() {
    /* extends recycler view adapter
     * implement all 3 methods: onCreateViewHolder, onBindViewHolder, getItemCount
     * implementation should subclass recyclerview ViewHolder
     */

    class myViewHolder : RecyclerView.ViewHolder {
        var d_zh: TextView
        var d_en: TextView
        var d_btn: Button
        var mtview: View

        constructor(tview: View) : super(tview) {
            d_zh = tview.findViewById(R.id.data_zh)
            d_en = tview.findViewById(R.id.data_en)
            d_btn = tview.findViewById(R.id.data_button)
            mtview = tview

            mtview.setOnClickListener{
                v -> Toast.makeText(
                    v?.context,
                "click " + d_zh.text.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            d_btn.setOnClickListener{
                v -> Toast.makeText(
                    v?.context,
                    (d_en.text.toString() + "\n" + d_zh.text.toString()),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val txview: View = LayoutInflater.from(parent.context).inflate(
            R.layout.re_layout,
            parent,
            false
        )

        return myViewHolder(txview)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.d_en.setText(enDataset[position])
        holder.d_zh.setText(zhDataset[position])
    }

    override fun getItemCount() = zhDataset.size
}