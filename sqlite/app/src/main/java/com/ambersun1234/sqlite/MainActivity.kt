package com.ambersun1234.sqlite

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var btn_arr: Array<Button>
    private lateinit var name: EditText
    private lateinit var sex: RadioGroup
    private lateinit var address: EditText

    private var name_arr: ArrayList<String>    = ArrayList()
    private var sex_arr: ArrayList<String>     = ArrayList()
    private var img_arr: ArrayList<Bitmap>     = ArrayList()
    private var address_arr: ArrayList<String> = ArrayList()

    private var item_arr = arrayListOf(
        this.name_arr, this.sex_arr,
        this.img_arr, this.address_arr
    )

    private lateinit var recycler: RecyclerView
    private lateinit var myrviewAdapter: RecyclerView.Adapter<*>
    private lateinit var myrviewManager: RecyclerView.LayoutManager

    private lateinit var mydb: memberdb

    private val clickHandler = object: View.OnClickListener {
        // retrieve name, sex, address
        override fun onClick(v: View?) {
            val sexid = sex.checkedRadioButtonId
            val sex_input = findViewById<RadioButton>(sexid).text.toString()
            val name_input = name.text.toString()
            val address_input = address.text.toString()

            when (v?.id) {
                R.id.btn_insert -> {
                    if (sex_input == "" || name_input == "" || address_input == "") {
                        Toast.makeText(
                            baseContext, "欄位不可為空", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val rt = mydb.addData(
                            name_input,
                            sex_input,
                            address_input
                        )
                        if (rt >= 0) {
                            Toast.makeText(
                                baseContext, "欄位已新增", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                R.id.btn_query -> {

                }
                R.id.btn_list -> {
                    val mycursor = mydb.getDataInterface("", "")
                    retrieveData(mycursor)
                }
            }
            // end onclick function
        }
    }

    fun onOverWrite() {
        for (element in this.item_arr) {
            element.clear()
        }
    }

    fun retrieveData(mycursor: Cursor) {
        try {
            this.onOverWrite()
            // https://stackoverflow.com/a/21432966
            if (mycursor.count > 0 && mycursor.moveToFirst()) {
                do {
                    this.name_arr.add(mycursor.getString(mycursor.getColumnIndex("name")))
                    this.sex_arr.add(mycursor.getString(mycursor.getColumnIndex("sex")))
                    this.address_arr.add(mycursor.getString(mycursor.getColumnIndex("address")))

                    val tmp = mycursor.getBlob(mycursor.getColumnIndex("img"))
                    if (tmp != null) {
                        this.img_arr.add(BitmapFactory.decodeByteArray(tmp, -1, tmp.size))
                    } else {
                        this.img_arr.add(BitmapFactory.decodeResource(resources, R.drawable.linux))
                    }
                } while (mycursor.moveToNext());
            }
        } catch (e: Exception) {
            Toast.makeText(
                this.baseContext, "錯誤!!", Toast.LENGTH_SHORT
            ).show()
            throw e
        } finally {
            if (!mycursor.isClosed) {
                mycursor.close()
            }
        }
        this.createRView()
    }

    fun createRView() {
        this.recycler = findViewById(R.id.rview)
        this.recycler.setHasFixedSize(true)
        this.myrviewManager = LinearLayoutManager(this)
        this.recycler.layoutManager = this.myrviewManager
        this.myrviewAdapter = RAdapter(
            this.name_arr,
            this.sex_arr,
            this.address_arr,
            this.img_arr,
            this.baseContext
        )
        this.recycler.adapter = this.myrviewAdapter
        this.recycler.adapter!!.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.name = findViewById(R.id.name_input)
        this.sex = findViewById(R.id.radioGroup)
        this.address = findViewById(R.id.address_input)

        this.mydb = memberdb(baseContext)

        this.btn_arr = arrayOf(
            findViewById(R.id.btn_insert),
            findViewById(R.id.btn_list),
            findViewById(R.id.btn_query)
        )
        for (element in this.btn_arr) {
            element.setOnClickListener(this.clickHandler)
        }
    }
}