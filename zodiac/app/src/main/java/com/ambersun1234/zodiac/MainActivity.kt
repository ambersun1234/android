package com.ambersun1234.zodiac;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

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

    private lateinit var myrview: RecyclerView
    private lateinit var myrviewAdapter: RecyclerView.Adapter<*>
    private lateinit var myrviewManager: RecyclerView.LayoutManager

    private var name_arr: ArrayList<String> = ArrayList()
    private var star_arr: ArrayList<String> = ArrayList()
    private var date_arr: ArrayList<String> = ArrayList()
    private var img_arr: ArrayList<Int> = ArrayList()

    private val myActionListener = object: TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                storeInfo()
                createR()
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
            if (myrview.visibility == View.INVISIBLE) {
                myrview.visibility = View.VISIBLE
                rview_btn.text = "隱藏查詢紀錄"
            }
            else {
                myrview.visibility = View.INVISIBLE
                rview_btn.text = "顯示查詢紀錄"
            }
        }
    }

    fun createR() {
        this.myrviewAdapter = myAdapter(
                this.name_arr,
                this.date_arr,
                this.star_arr,
                this.img_arr,
                baseContext // https://stackoverflow.com/a/7666630
        )

        this.myrview.adapter = this.myrviewAdapter
    }

    fun id(m: Int, d: Int): Int {
        if ((m == 12 && 22 <= d && d <= 31) || (m == 1 && 1 <= d && d <= 19)) {
            return 0
        }
        if ((m == 1 && 20 <= d && d <= 31) || (m == 2 && 1 <= d && d <= 18)) {
            return 1
        }
        if ((m == 2 && 19 <= d && d <= 30) || (m == 3 && 1 <= d && d <= 20)) {
            return 2
        }
        if ((m == 3 && 21 <= d && d <= 31) || (m == 4 && 1 <= d && d <= 19)) {
            return 3
        }
        if ((m == 4 && 20 <= d && d <= 30) || (m == 5 && 1 <= d && d <= 20)) {
            return 4
        }
        if ((m == 5 && 21 <= d && d <= 31) || (m == 6 && 1 <= d && d <= 20)) {
            return 5
        }
        if ((m == 6 && 21 <= d && d <= 30) || (m == 7 && 1 <= d && d <= 22)) {
            return 6
        }
        if ((m == 7 && 23 <= d && d <= 31) || (m == 8 && 1 <= d && d <= 22)) {
            return 7
        }
        if ((m == 8 && 23 <= d && d <= 31) || (m == 9 && 1 <= d && d <= 22)) {
            return 8
        }
        if ((m == 9 && 23 <= d && d <= 30) || (m == 10 && 1<= d && d <= 22)) {
            return 9
        }
        if ((m == 10 && 23 <= d && d <= 31) || (m == 11 &&  1<= d && d <= 21)) {
            return 10
        }
        if ((m == 11 && 22 <= d && d <= 30) || (m == 12 &&  1<= d && d <= 21)) {
            return 11
        }
        return -1
    }

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

        this.sp_m = findViewById(R.id.spinner_month)
        this.sp_d = findViewById(R.id.spinner_date)
        this.input = findViewById(R.id.input_name)
        this.res = findViewById(R.id.result)
        this.btn = findViewById(R.id.rview_btn)
        this.myrview = findViewById(R.id.myrview)

        // first set recycler view invisible
        this.myrview.visibility = View.INVISIBLE

        this.myrview.setHasFixedSize(true)
        this.myrviewManager = LinearLayoutManager(this)
        this.myrview.layoutManager = this.myrviewManager

        this.sp_m.onItemSelectedListener = this.mySelectedListener
        this.sp_d.onItemSelectedListener = this.mySelectedListener
        this.input.setOnEditorActionListener(this.myActionListener)
        this.btn.setOnClickListener(this.myClickListener)
    }
}
