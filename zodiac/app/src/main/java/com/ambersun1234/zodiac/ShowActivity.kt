package com.ambersun1234.zodiac

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShowActivity : AppCompatActivity() {
    private lateinit var btn: Button
    private lateinit var myrview: RecyclerView
    private lateinit var myrviewAdapter: RecyclerView.Adapter<*>
    private lateinit var myrviewManager: RecyclerView.LayoutManager
    private lateinit var ex: Bundle

    private lateinit var n_arr: ArrayList<String>
    private lateinit var d_arr: ArrayList<String>
    private lateinit var s_arr: ArrayList<String>
    private lateinit var i_arr: ArrayList<Int>

    private val myClickListener = object: View.OnClickListener {
        override fun onClick(v: View?) {
            val it = Intent(this@ShowActivity, MainActivity::class.java)
            it.putStringArrayListExtra("name", n_arr)
            it.putStringArrayListExtra("date", d_arr)
            it.putStringArrayListExtra("star", s_arr)
            it.putIntegerArrayListExtra("img", i_arr)
            startActivity(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        this.btn = findViewById(R.id.btmain)
        this.myrview = findViewById(R.id.myrview)
        this.ex = intent.extras!!

        // get intent data
        this.n_arr = this.ex.getStringArrayList("name")!!
        this.d_arr = this.ex.getStringArrayList("date")!!
        this.s_arr = this.ex.getStringArrayList("star")!!
        this.i_arr = this.ex.getIntegerArrayList("img")!!

        this.btn.setOnClickListener(this.myClickListener)

        // recycler view
        this.myrview.setHasFixedSize(true)
        this.myrviewManager = LinearLayoutManager(this)
        this.myrview.layoutManager = this.myrviewManager
        this.myrviewAdapter = myAdapter(
            this.n_arr,
            this.d_arr,
            this.s_arr,
            this.i_arr,
            baseContext
        )
        this.myrview.adapter = this.myrviewAdapter
    }
}
