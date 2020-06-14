package com.ambersun1234.sqlite

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.modify_layout.*

class modifyActivity : AppCompatActivity() {
    private lateinit var ex: Bundle
    private lateinit var _name: EditText
    private lateinit var _address: EditText
    private lateinit var _sex: RadioGroup

    private lateinit var _img_show: ImageView

    private lateinit var _submitBtn: Button
    private lateinit var _cancelBtn: Button

    private lateinit var it: Intent

    private lateinit var mymember: memberdb
    private lateinit var row_id: String

    private val magicNumber = 0xff33

    private val clickHandler = object: View.OnClickListener {
        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.modify_btn -> {
                    val tmember = memberdb(v.context)
                    val sexid = _sex.checkedRadioButtonId
                    val sex_input = findViewById<RadioButton>(sexid).text.toString()
                    val name_input = _name.text.toString()
                    val address_input = _address.text.toString()
                    val img_input = _img_show.drawable.toBitmap()

                    if (sex_input == "" || name_input == "" || address_input == "") {
                        Toast.makeText(
                            baseContext, "欄位不可為空", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // write to database
                        val rt = tmember.updateData(
                            name_input,
                            sex_input,
                            address_input,
                            img_input,
                            "`${memberdb.ID_FILED}` = '${row_id}'"
                        )
                        if (rt >= 0) {
                            // redirect to page
                            it = Intent(this@modifyActivity, MainActivity::class.java)
                            startActivity(it)
                        } else {
                            Toast.makeText(
                                baseContext, "修改出現錯誤！", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                R.id.cancel_btn -> {
                    // redirect to page
                    it = Intent(this@modifyActivity, MainActivity::class.java)
                    startActivity(it)
                }
            }
        }
    }

    private val modifyImgHandler = object: View.OnClickListener {
        override fun onClick(v: View?) {
            val it = Intent()
            it.type = "image/*"
            it.action = Intent.ACTION_GET_CONTENT
            it.putExtra("id", row_id)
            startActivityForResult(Intent.createChooser(it, "select picture"), magicNumber)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.magicNumber && resultCode == Activity.RESULT_OK) {
            val uri = data?.data!!

            this._img_show.setImageBitmap(BitmapFactory.decodeStream(contentResolver.openInputStream(uri)))
        }
    }

    private fun getData() {
        this.row_id = this.ex.getString("id")!!
        this.mymember = memberdb(this.baseContext)
        val mycursor = this.mymember.getDataInterface(
            "`${memberdb.ID_FILED}` = ${this.row_id}",
            ""
        )
        if (mycursor.count > 0 && mycursor.moveToFirst()) {
            do {
                this._name.setText(mycursor.getString(mycursor.getColumnIndex(memberdb.NAME_FIELD)))
                val tmp = mycursor.getString(mycursor.getColumnIndex(memberdb.SEX_FIELD))
                when (tmp) {
                    getString(R.string.prompt_male) -> this._sex.check(R.id.rbtn_male)
                    getString(R.string.prompt_female) -> this._sex.check(R.id.rbtn_female)
                    getString(R.string.prompt_none) -> this._sex.check(R.id.rbtn_none)
                }
                this._address.setText(mycursor.getString(mycursor.getColumnIndex(memberdb.ADDRESS_FIELD)))

                val tmp2 = mycursor.getBlob(mycursor.getColumnIndex(memberdb.IMG_FIELD))
                if (tmp2 != null) {
                    this._img_show.setImageBitmap(BitmapFactory.decodeByteArray(tmp2, 0, tmp2.size))
                } else {
                    this._img_show.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.linux))
                }

            } while (mycursor.moveToNext());
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_layout)

        this._name    = findViewById(R.id.name_minput)
        this._sex     = findViewById(R.id.sex_minput)
        this._address = findViewById(R.id.address_minput)

        this._submitBtn = findViewById(R.id.modify_btn)
        this._cancelBtn = findViewById(R.id.cancel_btn)

        this._img_show = findViewById(R.id.img_show)

        this._img_show.setOnClickListener(this.modifyImgHandler)
        this._submitBtn.setOnClickListener(this.clickHandler)
        this._cancelBtn.setOnClickListener(this.clickHandler)

        // get intent value
        if (intent.extras != null) {
            this.ex = intent.extras!!
            this.getData()
        }
    }
}