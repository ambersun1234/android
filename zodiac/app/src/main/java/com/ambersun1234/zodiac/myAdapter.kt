package com.ambersun1234.zodiac

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class myAdapter(
    var nameArr: ArrayList<String>,
    var dateArr: ArrayList<String>,
    var starArr: ArrayList<String>,
    var imgArr: ArrayList<Int>,
    private val mcontext: Context
) : RecyclerView.Adapter<myAdapter.myViewHolder>() {
    /* extends recycler view adapter
     * implement all 3 methods: onCreateViewHolder, onBindViewHolder, getItemCount
     * implementation should subclass recyclerview ViewHolder
     */

    companion object {
        lateinit var ccontext: Context
        val imgList: IntArray = intArrayOf(
            R.mipmap.capricorn, R.mipmap.aquarius, R.mipmap.pisces,
            R.mipmap.aries, R.mipmap.taurus, R.mipmap.gemini,
            R.mipmap.cancer, R.mipmap.leo, R.mipmap.virgo,
            R.mipmap.libra, R.mipmap.scorpio, R.mipmap.sagittarius
        )
    }

    init {
        ccontext = mcontext
    }

    inner class myViewHolder : RecyclerView.ViewHolder {
        var d_img: ImageView
        var d_name: TextView
        var d_cb: TextView
        var mtview: View
        var lala: TextView

        private val dialogHandler = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }
        }

        private val deleteDialogHandler = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val index = layoutPosition
                nameArr.removeAt(index)
                dateArr.removeAt(index)
                starArr.removeAt(index)
                imgArr.removeAt(index)
                notifyDataSetChanged()

                dialog?.dismiss()
            }
        }

        constructor(tview: View) : super(tview) {
            d_img = tview.findViewById(R.id.imageView)
            d_name = tview.findViewById(R.id.data_name)
            d_cb = tview.findViewById(R.id.data_cb)
            lala = tview.findViewById(R.id.lala)
            mtview = tview

            mtview.setOnClickListener{showInfo(tview)}
            mtview.setOnLongClickListener{deleteInfo(tview)}
        }

        fun deleteInfo(v: View): Boolean {
            val ad_builder = AlertDialog.Builder(v.context)
            ad_builder.setTitle(ccontext.resources.getString(R.string.deleteConfirm))
            ad_builder.setCancelable(false)
            ad_builder.setPositiveButton(
                ccontext.resources.getString(R.string.yes), this.deleteDialogHandler
            )
            ad_builder.setNegativeButton(
                ccontext.resources.getString(R.string.no), this.dialogHandler
            )
            ad_builder.create().show()

            return true
        }

        fun showInfo(v: View): Boolean {
            val index = layoutPosition
            val ad_builder = AlertDialog.Builder(v.context)
            ad_builder.setIcon(imgList[imgArr[index]])
            ad_builder.setTitle(nameArr[index])
            ad_builder.setMessage(dateArr[index] + starArr[index])
            ad_builder.setCancelable(false)
            ad_builder.setPositiveButton(
                ccontext.resources.getString(R.string.leave), this.dialogHandler
            )
            ad_builder.create().show()

            return true
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
        holder.d_img.setImageResource(imgList[this.imgArr[position]])
        holder.d_cb.setText(this.dateArr[position] + this.starArr[position])
        holder.d_name.setText(this.nameArr[position])
        holder.lala.text = this.imgArr[position].toString()
    }

    override fun getItemCount() = this.nameArr.size
}