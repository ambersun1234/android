package com.ambersun1234.moneyjson

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONArray
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val imgArr = arrayOf(
            R.mipmap.us, R.mipmap.hk, R.mipmap.gb, R.mipmap.au,
            R.mipmap.ca, R.mipmap.sg, R.mipmap.ch, R.mipmap.jp,
            R.mipmap.za, R.mipmap.se, R.mipmap.nz, R.mipmap.th,
            R.mipmap.ph, R.mipmap.ido, R.mipmap.euro, R.mipmap.kr,
            R.mipmap.vn, R.mipmap.my, R.mipmap.cn
    )
    private val mapImgArr = mapOf<String, Int>(
            "美金(USD)" to R.mipmap.us, "港幣(HKD)" to R.mipmap.hk, "英鎊(GBP)" to R.mipmap.gb, "澳幣(AUD)" to R.mipmap.au,
            "加拿大幣(CAD)" to R.mipmap.ca, "新加坡幣(SGD)" to R.mipmap.sg, "瑞士法郎(CHF)" to R.mipmap.ch, "日圓(JPY)" to R.mipmap.jp,
            "南非幣(ZAR)" to R.mipmap.za, "瑞典幣(SEK))" to R.mipmap.se, "紐元(NZD)" to R.mipmap.nz, "泰幣(THB)" to R.mipmap.th,
            "菲國比索(PHP)" to R.mipmap.ph, "印尼幣(IDR)" to R.mipmap.ido, "歐元(EUR)" to R.mipmap.euro, "韓元(KRW)" to R.mipmap.kr,
            "越南盾(VND)" to R.mipmap.vn, "馬來幣(MYR)" to R.mipmap.my, "人民幣(CNY)" to R.mipmap.cn
    )
    private lateinit var titleImg: ImageView
    private lateinit var txtView: TextView

    private lateinit var recycler: RecyclerView
    private lateinit var myrviewAdapter: RecyclerView.Adapter<*>
    private lateinit var myrviewManager: RecyclerView.LayoutManager

    private lateinit var money_arr: JSONArray

    private var inputDate: Int = 1
    @SuppressLint("SimpleDateFormat")
    var dateFormat = SimpleDateFormat("yyyy/MM/dd");
    private lateinit var _inputDate: String
    private val dateMap = arrayOf(
            0 to "目前", 1 to "昨天", 2 to "前天"
    )
    private val moneyList = arrayOf(
            "美金(USD)","港幣(HKD)","英鎊(GBP)","澳幣(AUD)",
            "加拿大幣(CAD)","新加坡幣(SGD)","瑞士法郎(CHF)","日圓(JPY)",
            "南非幣(ZAR)","瑞典幣(SEK))","紐元(NZD)","泰幣(THB)",
            "菲國比索(PHP)","印尼幣(IDR)","歐元(EUR)","韓元(KRW)",
            "越南盾(VND)","馬來幣(MYR)","人民幣(CNY)"
    )

    private var money_current: MutableMap<String, ArrayList<String>> = mutableMapOf()
    private var money_img: MutableMap<String, ArrayList<Int>> = mutableMapOf()
    private var money_prompt: MutableMap<String, ArrayList<String>> = mutableMapOf()

    private val myLHandler = Response.Listener<JSONArray> { response ->
        money_arr = response!!
        requestParse()
    }

    private val myEHandler = Response.ErrorListener { error ->
        Toast.makeText(
                this@MainActivity,
                error!!.toString(),
                Toast.LENGTH_LONG
        ).show()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        this.titleImg = findViewById(R.id.title_img)
        this.titleImg.setImageResource(R.mipmap.tw)

        this.txtView = findViewById(R.id.textView)
        this.txtView.text = getString(R.string.title1) + this.dateMap[this.inputDate] + getString(R.string.title2)
        this.txtView.setOnClickListener{datePrevious()}

        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        this._inputDate = dateFormat.format(cal.getTime())

        this.getData()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    fun datePrevious(): Boolean {
        this.inputDate += 1
        this.inputDate %= 3
        this.txtView.text = getString(R.string.title1) + this.dateMap[this.inputDate] + getString(R.string.title2)

        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1 * (this.inputDate + 1));
        this._inputDate = dateFormat.format(cal.getTime());

        Toast.makeText(
                this@MainActivity,
                this._inputDate,
                Toast.LENGTH_SHORT
        ).show()

        this.getData()

        return true
    }

    fun dataClear() {
        for (item in this.money_prompt) item.value.clear()
        for (item in this.money_current) item.value.clear()
        for (item in this.money_img) item.value.clear()
    }

    fun requestParse() {
        this.dataClear()

        val length = this.money_arr.length() - 1 // 3 day
        for (index in 0 .. length) {
            try {
                val tmp = this.money_arr.getJSONObject(index)

                val ctmp = tmp.getJSONArray("currency")
                val dtmp = tmp.getString("date")
                val rtmp = tmp.getJSONArray("rate")

                val tlength = ctmp.length() - 1

                this.money_img[dtmp] = ArrayList()
                this.money_current[dtmp] = ArrayList()
                this.money_prompt[dtmp] = ArrayList()

                for (iindex in 0 .. tlength) {
                    this.money_current[dtmp]?.add(rtmp.getString(iindex))
                    this.money_img[dtmp]?.add(this.mapImgArr[ctmp.getString(iindex)]!!)
                    this.money_prompt[dtmp]?.add(ctmp.getString(iindex))
                }

            } catch (e: Exception) {
                throw e
            }
        }

        this.onCreateRecyclerView()
    }

    fun getData() {
        val url = "http://140.129.26.29/currency_json_10906.php"
        val jArray = JsonArrayRequest(url, this.myLHandler, this.myEHandler)
        Volley.newRequestQueue(this).add(jArray)
    }

    fun onCreateRecyclerView() {
        this.recycler = findViewById(R.id.rview)
        this.recycler.setHasFixedSize(true)
        this.myrviewManager = LinearLayoutManager(this)
        this.recycler.layoutManager = this.myrviewManager
        this.myrviewAdapter = RAdapter(
                this.money_current,
                this.money_img,
                this.money_prompt,
                baseContext,
                this._inputDate
        )
        this.recycler.adapter = this.myrviewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private val dHandler = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            moneyList[which]
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val ad_builder = AlertDialog.Builder(this@MainActivity)

                ad_builder.setTitle(R.string.add_prompt)
                ad_builder.setCancelable(true)
                ad_builder.setSingleChoiceItems(moneyList, 1, this.dHandler)
                ad_builder.create().show()
                return true
            }
            R.id.action_clear -> {
                this.dataClear()
                this.onCreateRecyclerView()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}