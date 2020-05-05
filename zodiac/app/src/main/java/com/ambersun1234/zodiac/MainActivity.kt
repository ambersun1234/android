package com.ambersun1234.zodiac;

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*

class constant {
    companion object {
        val date_map = mapOf<Int, Int>(
            1 to 31, 2 to 30, 3 to 31, 4 to 30, 5 to 31, 6 to 30,
            7 to 31, 8 to 31, 9 to 30, 10 to 31, 11 to 30, 12 to 31
        )
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var sp_m: Spinner
    private lateinit var sp_d: Spinner
    private lateinit var input: EditText
    private lateinit var res: TextView
    private lateinit var btn: Button
    private lateinit var ex: Bundle

    private var name_arr: ArrayList<String> = ArrayList()
    private var star_arr: ArrayList<String> = ArrayList()
    private var date_arr: ArrayList<String> = ArrayList()
    private var img_arr:  ArrayList<Int>    = ArrayList()

    private val myActionListener = object: TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                storeInfo()
                return true
            }
            return false
        }
    }

    private val mySelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            date_check()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Unit
        }
    }

    private val myClickListener = object: View.OnClickListener {
        override fun onClick(v: View?) {
            val it = Intent(this@MainActivity, ShowActivity::class.java)
            it.putStringArrayListExtra("name", name_arr)
            it.putStringArrayListExtra("date", date_arr)
            it.putStringArrayListExtra("star", star_arr)
            it.putIntegerArrayListExtra("img" , img_arr)
            startActivityForResult(it, 0x111)
        }
    }

    fun id(m: Int, d: Int): Int {
        when (m) {
            12 -> return if (d in 22..31) 0  else 11
            1  -> return if (d in  1..19) 0  else 1
            2  -> return if (d in  1..18) 1  else 2
            3  -> return if (d in  1..20) 2  else 3
            4  -> return if (d in  1..19) 3  else 4
            5  -> return if (d in  1..20) 4  else 5
            6  -> return if (d in  1..20) 5  else 6
            7  -> return if (d in  1..22) 6  else 7
            8  -> return if (d in  1..22) 7  else 8
            9  -> return if (d in  1..22) 8  else 9
            10 -> return if (d in  1..22) 9  else 10
            11 -> return if (d in  1..21) 10 else 11
        }
        return -1
    }

    @SuppressLint("SetTextI18n")
    fun storeInfo(): Boolean {
        val m = this.sp_m.selectedItem.toString()
        val d = this.sp_d.selectedItem.toString()
        val name = this.input.text.toString()
        val res = this.id(m.toInt(), d.toInt())
        val star = resources.getStringArray(R.array.star_zh)[res]
        if (name == "") {
            Toast.makeText(
                this.baseContext,
                "輸入姓名才可查詢",
                Toast.LENGTH_SHORT
            ).show()
            return true
        }
        if (date_check() == false) {
            return false
        }

        this.res.text = "生日: $m 月 $d 日\n星座: " + star.toString()

        // add data
        this.name_arr.add(name)
        this.date_arr.add("$m/$d")
        this.star_arr.add(star!!)
        this.img_arr.add(res)
        return true
    }

    fun date_check(): Boolean {
        val d = this.sp_d.selectedItem.toString().toInt()
        val m = this.sp_m.selectedItem.toString().toInt()
        var rt = false

        if (d > constant.date_map[m]!!) {
            Toast.makeText(
                this.baseContext,
                "日期錯誤!",
                Toast.LENGTH_SHORT
            ).show()
            rt = false
        } else {
            rt = true
        }

        return rt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent.extras != null) {
            this.ex = intent.extras!!
            // retrieve data from activity
            this.name_arr = this.ex.getStringArrayList("name")!!
            this.date_arr = this.ex.getStringArrayList("date")!!
            this.star_arr = this.ex.getStringArrayList("star")!!
            this.img_arr = this.ex.getIntegerArrayList("img")!!
        }

        this.sp_m = findViewById(R.id.spinner_month)
        this.sp_d = findViewById(R.id.spinner_date)
        this.input = findViewById(R.id.input_name)
        this.res = findViewById(R.id.result)
        this.btn = findViewById(R.id.rview_btn)

        this.sp_d.onItemSelectedListener = this.mySelectedListener
        this.input.setOnEditorActionListener(this.myActionListener)
        this.btn.setOnClickListener(this.myClickListener)
    }
}
