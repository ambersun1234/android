package com.ambersun1234.weekday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private val mylistener = object: AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val str = resources.getString(R.string.toast_prefix) +
                    resources.getStringArray(R.array.weekday_list)[position].toString()

            Toast.makeText(
                this@MainActivity,
                str,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private var myadapter: SimpleAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lv = findViewById<ListView>(R.id.mylistview)

//        // ArrayAdapter way
//        lv.adapter = ArrayAdapter<String>(
//            this,
//            R.layout.support_simple_spinner_dropdown_item,
//            resources.getStringArray(R.array.weekday_list)
//        )

        val dataList: ArrayList<Map<String, Any>> = ArrayList()

        for (i in 0..6) {
            val tmap = hashMapOf<String, Any>()
            tmap.put("myimg", if (i % 2 == 0) android.R.drawable.btn_star else android.R.drawable.btn_radio)
            tmap.put("mytext", resources.getStringArray(R.array.weekday_list)[i])
            tmap.put("mytext2", resources.getStringArray(R.array.weekday_list_en)[i])
            dataList.add(tmap)
        }

        this.myadapter = SimpleAdapter(
            this,
            dataList,
            R.layout.listview_pic,
            arrayOf("myimg", "mytext", "mytext2"),
            intArrayOf(R.id.myimageView, R.id.mytextView, R.id.mytextView2)
        )

        lv.adapter = this.myadapter

        lv.onItemClickListener = this.mylistener
    }
}
