package com.ambersun1234.stock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var stockArray: JSONArray
    private lateinit var refreshBtn: Button

    private var stock_code: ArrayList<String>   = ArrayList()
    private var stock_name: ArrayList<String>   = ArrayList()
    private var stock_volume: ArrayList<String> = ArrayList()

    private lateinit var recycler: RecyclerView
    private lateinit var myrviewAdapter: RecyclerView.Adapter<*>
    private lateinit var myrviewManager: RecyclerView.LayoutManager

    private val myLHandler = Response.Listener<JSONArray> { response ->
        stockArray = response!!
        requestParse()
    }

    private val myEHandler = Response.ErrorListener { error ->
        Toast.makeText(
            this@MainActivity,
            error!!.toString(),
            Toast.LENGTH_LONG
        ).show()
    }

    private val myClickHandler = View.OnClickListener {view ->
        getData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.refreshBtn = findViewById(R.id.refresh)
        this.refreshBtn.setOnClickListener(this.myClickHandler)

        this.getData()
    }

    fun dataClear() {
        this.stock_code.clear()
        this.stock_name.clear()
        this.stock_volume.clear()
    }

    fun requestParse() {
        this.dataClear()

        val length = this.stockArray.length() - 1
        for (index in 0 .. length) {
            try {
                val tmp = this.stockArray.getJSONObject(index)

                this.stock_code.add(tmp.getString("categoryCode"))
                this.stock_name.add(tmp.getString("categoryName"))
                this.stock_volume.add(tmp.getString("total"))
            } catch (e: Exception) {
                throw e
            }
        }
        this.createRecyclerView()
    }

    fun createRecyclerView() {
        this.recycler = findViewById(R.id.rview)
        this.recycler.setHasFixedSize(true)
        this.myrviewManager = LinearLayoutManager(this)
        this.recycler.layoutManager = this.myrviewManager
        this.myrviewAdapter = RAdapter(
            this.stock_name,
            this.stock_code,
            this.stock_volume,
            baseContext
        )
        this.recycler.adapter = this.myrviewAdapter
    }

    fun getData() {
        val url = "https://cloud.culture.tw/frontsite/trans/SearchShowAction.do?method=doFindAllTypeJ"
//        val url = "https://www.twse.com.tw/rsrc/data/zh/home/summary.json?_=1592756047820"
//        val url = "https://www.twse.com.tw/rsrc/data/zh/home/values.json?_=1592756047793"
        val jArray = JsonArrayRequest(url, this.myLHandler, this.myEHandler)
        Volley.newRequestQueue(this).add(jArray)
    }
}