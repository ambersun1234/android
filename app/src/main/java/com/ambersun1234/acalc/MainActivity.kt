package com.ambersun1234.acalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {

    private var history_view:TextView? = null
    private var result_view:TextView? = null
    private var input_text:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        history_view = findViewById(R.id.history_input)
        result_view = findViewById(R.id.acalc_input)

        val btnList:Array<Int> = arrayOf(
            R.id.keyboard_num0, R.id.keyboard_num1, R.id.keyboard_num2, R.id.keyboard_num3,
            R.id.keyboard_num4, R.id.keyboard_num5, R.id.keyboard_num6, R.id.keyboard_num7,
            R.id.keyboard_num8, R.id.keyboard_num9,
            R.id.keyboard_clear, R.id.keyboard_sign, R.id.keyboard_divide, R.id.keyboard_multiple,
            R.id.keyboard_minus, R.id.keyboard_add, R.id.keyboard_point, R.id.keyboard_equal
        )

        btnList.forEach {
            val id = it
            val t_btn = findViewById<Button>(it)
            t_btn.setOnClickListener{
                this.onClick(id, t_btn.text.toString())
            }
        }
    }

    fun validClear(input: Int): Int {
        val btnText = findViewById<Button>(input).text.toString()

        val rt_zero = btnText == "0"
        val rt_clear = btnText == "C"

        return if (rt_zero || rt_clear) 0 else 1
    }

    fun onClick(btn: Int, inputOp: String) {
        val clearrt = this.validClear(btn)

        if (clearrt == 0) {
            // clear
            if (this.result_view?.text != "0") {
                this.history_view?.text = this.result_view?.text
                this.result_view?.text = "0"
                this.input_text = ""
            }
        } else {
            // no clear
            this.input_text = this.input_text?.plus(inputOp)
            this.result_view?.text = this.input_text
        }
    }
}
