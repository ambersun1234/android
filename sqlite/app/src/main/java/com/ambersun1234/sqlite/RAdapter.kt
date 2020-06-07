package com.ambersun1234.sqlite

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Blob

class RAdapter(
    var name_arr:    ArrayList<String>,
    var sex_arr:     ArrayList<String>,
    var address_arr: ArrayList<String>,
    var img_arr:     ArrayList<Bitmap>,
    val mcontext:    Context
) : RecyclerView.Adapter<RAdapter.myViewHolder>() {
    companion object {
        lateinit var ccontext: Context
    }

    init {
        ccontext = mcontext
    }

    inner class myViewHolder : RecyclerView.ViewHolder {
        val sql_img: ImageView
        val sql_name: TextView
        val sql_sex: TextView
        val sql_address: TextView

        constructor(tview: View) : super(tview) {
            sql_img     = tview.findViewById(R.id.sql_img)
            sql_name    = tview.findViewById(R.id.sql_name)
            sql_sex     = tview.findViewById(R.id.sql_sex)
            sql_address = tview.findViewById(R.id.sql_address)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val txview: View = LayoutInflater.from(parent.context).inflate(
            R.layout.slayout,
            parent,
            false
        )

        return myViewHolder(txview)
    }

    override fun getItemCount() = this.name_arr.size

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.sql_address.text = this.address_arr[position]
        holder.sql_sex.text     = this.sex_arr[position]
        holder.sql_name.text    = this.name_arr[position]
        holder.sql_img.setImageBitmap(this.img_arr[position])
    }
}