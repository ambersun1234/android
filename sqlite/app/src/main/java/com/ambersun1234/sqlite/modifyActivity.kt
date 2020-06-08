package com.ambersun1234.sqlite

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.modify_layout.*

class modifyActivity : AppCompatActivity() {
    private lateinit var ex: Bundle
    private lateinit var _name: EditText
    private lateinit var _address: EditText
    private lateinit var _sex: RadioGroup

    private lateinit var _submitBtn: Button
    private lateinit var _cancelBtn: Button

    private lateinit var it: Intent

    private lateinit var mymember: memberdb
    private lateinit var row_id: String

    private val clickHandler = object: View.OnClickListener {
        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.modify_btn -> {
                    val tmember = memberdb(v.context)
                    val sexid = _sex.checkedRadioButtonId
                    val sex_input = findViewById<RadioButton>(sexid).text.toString()
                    val name_input = _name.text.toString()
                    val address_input = _address.text.toString()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_layout)

        this._name    = findViewById(R.id.name_minput)
        this._sex     = findViewById(R.id.sex_minput)
        this._address = findViewById(R.id.address_minput)

        this._submitBtn = findViewById(R.id.modify_btn)
        this._cancelBtn = findViewById(R.id.cancel_btn)

        this._submitBtn.setOnClickListener(this.clickHandler)
        this._cancelBtn.setOnClickListener(this.clickHandler)

        // get intent value
        this.ex = intent.extras!!
        this.row_id = this.ex.getString("id")!!
        this.mymember = memberdb(this.baseContext)
        val mycursor = this.mymember.getDataInterface(
            "`${memberdb.ID_FILED}` = ${this.row_id}",
            ""
        )
        if (mycursor.count > 0 && mycursor.moveToFirst()) {
            do {
                this._name.setText(mycursor.getString(mycursor.getColumnIndex("name")))
                val tmp = mycursor.getString(mycursor.getColumnIndex("sex"))
                when (tmp) {
                    getString(R.string.prompt_male) -> this._sex.check(R.id.rbtn_male)
                    getString(R.string.prompt_female) -> this._sex.check(R.id.rbtn_female)
                    getString(R.string.prompt_none) -> this._sex.check(R.id.rbtn_none)
                }
                this._address.setText(mycursor.getString(mycursor.getColumnIndex("address")))

            } while (mycursor.moveToNext());
        }

    }
}