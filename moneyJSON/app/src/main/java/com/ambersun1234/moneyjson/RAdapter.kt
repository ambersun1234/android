package com.ambersun1234.moneyjson

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RAdapter(
    val money_current: MutableMap<String, ArrayList<String>>,
    val money_img: MutableMap<String, ArrayList<Int>>,
    val money_prompt: MutableMap<String, ArrayList<String>>,
    val mcontext: Context,
    val inputDate: String
) : RecyclerView.Adapter<RAdapter.myViewHolder>() {

    companion object {
        lateinit var ccontext: Context
    }

    init {
        ccontext = this.mcontext
    }

    inner class myViewHolder : RecyclerView.ViewHolder {
        var _current: TextView
        var _img: ImageView
        var _prompt: TextView

        private val dialogHandler = DialogInterface.OnClickListener { dialog, which -> dialog?.dismiss() }

        private val deleteDialogHandler = DialogInterface.OnClickListener { dialog, which ->
            val index = layoutPosition
            money_current[inputDate]?.removeAt(index)
            money_img[inputDate]?.removeAt(index)
            money_prompt[inputDate]?.removeAt(index)
            notifyDataSetChanged()

            dialog?.dismiss()
        }

        constructor(tview: View) : super(tview) {
            this._current = tview.findViewById(R.id.money_current)
            this._img = tview.findViewById(R.id.money_img)
            this._prompt = tview.findViewById(R.id.money_prompt)

            tview.setOnLongClickListener{deleteRow(tview)}
            tview.setOnClickListener{showInfo()}
        }

        fun showInfo(): Boolean {
            val index = layoutPosition
            val money_code = money_prompt[inputDate]?.get(index)
            val money = 1.0 / money_current[inputDate]?.get(index)!!.toDouble()

            Toast.makeText(
                mcontext,
                mcontext.getString(R.string.showPrompt) +
                        "\n1 TWD = " +
                        money.toString() +
                        money_code.toString(),
                Toast.LENGTH_LONG
            ).show()

            return true
        }

        fun deleteRow(v: View): Boolean {
            val ad_builder = AlertDialog.Builder(v.context)
            ad_builder.setTitle(ccontext.resources.getString(R.string.deleteConfirm))
            ad_builder.setCancelable(false)
            ad_builder.setPositiveButton(
                ccontext.resources.getString(R.string.yes), deleteDialogHandler
            )
            ad_builder.setNegativeButton(
                ccontext.resources.getString(R.string.no), dialogHandler
            )
            ad_builder.create().show()

            return true
        }
    }

    override fun getItemCount(): Int {
        return this.money_current[this.inputDate]!!.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder._current.text = this.money_current[this.inputDate]?.get(position)
        holder._img.setImageResource(this.money_img[this.inputDate]!!.get(position))
        holder._prompt.text = this.money_prompt[this.inputDate]?.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val txview: View = LayoutInflater.from(parent.context).inflate(
            R.layout.money,
            parent,
            false
        )

        return myViewHolder(txview)

    }
}