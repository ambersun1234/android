package com.ambersun1234.weekday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lv = findViewById<ListView>(R.id.mylistview)

        lv.adapter = ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.weekday_list)
        )

        lv.onItemClickListener = this.mylistener
    }
}
