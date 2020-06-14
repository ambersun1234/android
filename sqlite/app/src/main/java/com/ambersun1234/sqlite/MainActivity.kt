package com.ambersun1234.sqlite

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var btn_arr: Array<Button>
    private lateinit var name: EditText
    private lateinit var sex: RadioGroup
    private lateinit var address: EditText
    private lateinit var img: ImageView

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
    private val magicNumber = 0xff22

    private val chooseImgHandler = object: View.OnClickListener {
        override fun onClick(v: View?) {
            val it = Intent()
            it.type = "image/*"
            it.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(it, "select picture"), magicNumber)
        }
    }

    private val clickHandler = object: View.OnClickListener {
        // retrieve name, sex, address
        override fun onClick(v: View?) {
            val sexid = sex.checkedRadioButtonId
            val sex_input = findViewById<RadioButton>(sexid).text.toString()
            val name_input = name.text.toString()
            val address_input = address.text.toString()
            val img_input = img.drawable.toBitmap()

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
                            address_input,
                            img_input
                        )
                        if (rt >= 0) {
                            Toast.makeText(
                                baseContext, "欄位已新增", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    showInit()
                }
                R.id.btn_query -> {
                    showInit(
                        if (name_input != "") "`${memberdb.NAME_FIELD}` LIKE '%$name_input%'" else "" +
                        if (sex_input != "") " `${memberdb.SEX_FIELD}` = '$sex_input'" else "" +
                        if (address_input != "") " `${memberdb.ADDRESS_FIELD}` LIKE '%$address_input%'" else "" ,
                        ""
                    )
                }
                R.id.btn_list -> {
                    showInit()
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

    fun showInit(whereClause: String = "", orderClause: String = "") {
        val mycursor = mydb.getDataInterface(whereClause, orderClause)
        this.retrieveData(mycursor)
    }

    fun retrieveData(mycursor: Cursor) {
        try {
            this.onOverWrite()
            // https://stackoverflow.com/a/21432966
            if (mycursor.count > 0 && mycursor.moveToFirst()) {
                do {
                    this.name_arr.add(mycursor.getString(mycursor.getColumnIndex(memberdb.NAME_FIELD)))
                    this.sex_arr.add(mycursor.getString(mycursor.getColumnIndex(memberdb.SEX_FIELD)))
                    this.address_arr.add(mycursor.getString(mycursor.getColumnIndex(memberdb.ADDRESS_FIELD)))

                    val tmp = mycursor.getBlob(mycursor.getColumnIndex(memberdb.IMG_FIELD))
                    if (tmp != null) {
                        this.img_arr.add(BitmapFactory.decodeByteArray(tmp, 0, tmp.size))
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
        Toast.makeText(
            baseContext, "查詢結果如列表所示", Toast.LENGTH_SHORT
        ).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.magicNumber && resultCode == Activity.RESULT_OK) {
            val uri = data?.data!!

            this.img_input.setImageBitmap(BitmapFactory.decodeStream(contentResolver.openInputStream(uri)))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.name = findViewById(R.id.name_input)
        this.sex = findViewById(R.id.radioGroup)
        this.address = findViewById(R.id.address_input)
        this.img = findViewById(R.id.img_input)

        this.mydb = memberdb(baseContext)

        this.img.setOnClickListener(this.chooseImgHandler)

        this.btn_arr = arrayOf(
            findViewById(R.id.btn_insert),
            findViewById(R.id.btn_list),
            findViewById(R.id.btn_query)
        )
        for (element in this.btn_arr) {
            element.setOnClickListener(this.clickHandler)
        }

        this.showInit()
    }
}