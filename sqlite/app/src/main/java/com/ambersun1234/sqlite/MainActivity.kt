package com.ambersun1234.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var btn_arr: Array<Button>
    private lateinit var name: EditText
    private lateinit var sex: RadioGroup
    private lateinit var address: EditText
    private val mydb = memberdb(baseContext)

    private val clickHandler = object: View.OnClickListener {
        // retrieve name, sex, address
        override fun onClick(v: View?) {
            val sexid = sex.checkedRadioButtonId
            val sex_input = findViewById<RadioButton>(sexid).text
            val name_input = name.text
            val address_input = address.text

            when (v?.id) {
                R.id.btn_insert -> {
                    if (sex_input == "" || name_input == "" || address_input == "") {
                        
                    }
                }
                R.id.btn_query -> {

                }
                R.id.btn_list -> {

                }
            }
            // end onclick function
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.name = findViewById(R.id.name_input)
        this.sex = findViewById(R.id.radioGroup)
        this.address = findViewById(R.id.address_input)

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