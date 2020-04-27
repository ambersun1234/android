package com.ambersun1234.zodiac

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class myAdapter(
    private val nameArr: ArrayList<String>,
    private val dateArr: ArrayList<String>,
    private val starArr: ArrayList<String>,
    private val imgArr: ArrayList<Int>,
    private val mcontext: Context
) : RecyclerView.Adapter<myAdapter.myViewHolder>() {
    /* extends recycler view adapter
     * implement all 3 methods: onCreateViewHolder, onBindViewHolder, getItemCount
     * implementation should subclass recyclerview ViewHolder
     */

    companion object {
        lateinit var ccontext: Context
    }

    init {
        ccontext = mcontext
    }

    class myViewHolder : RecyclerView.ViewHolder {
        var d_img: ImageView
        var d_name: TextView
        var d_cb: TextView
        var mtview: View
        var lala: TextView

        constructor(tview: View) : super(tview) {
            d_img = tview.findViewById(R.id.imageView)
            d_name = tview.findViewById(R.id.data_name)
            d_cb = tview.findViewById(R.id.data_cb)
            lala = tview.findViewById(R.id.lala)
            mtview = tview

            mtview.setOnClickListener{showInfo(tview)}
        }

        fun showInfo(v: View) {
            val index = lala.text.toString().toInt()
            Toast.makeText(
                v.context,
                ccontext.resources.getStringArray(R.array.star_en)[index] +
                        "\n" +
                        ccontext.resources.getStringArray(R.array.star_date)[index],
//                https://stackoverflow.com/a/8864116
                Toast.LENGTH_SHORT
            ).show()
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val imgList: IntArray = intArrayOf(
            R.mipmap.capricorn, R.mipmap.aquarius, R.mipmap.pisces,
            R.mipmap.aries, R.mipmap.taurus, R.mipmap.gemini,
            R.mipmap.cancer, R.mipmap.leo, R.mipmap.virgo,
            R.mipmap.libra, R.mipmap.scorpio, R.mipmap.sagittarius
        )
        holder.d_img.setImageResource(imgList[this.imgArr[position]])
        holder.d_cb.setText(this.dateArr[position] + this.starArr[position])
        holder.d_name.setText(this.nameArr[position])
        holder.lala.text = this.imgArr[position].toString()
    }

    override fun getItemCount() = this.nameArr.size
}