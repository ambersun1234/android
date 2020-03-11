package com.ambersun1234.acalc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*

enum class keyboard_type {
    clear, calculate, equal, input, sign
}

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

    fun distinguish(input: Int): keyboard_type {
        val btnText = findViewById<Button>(input).text.toString()

        if (btnText == "C") return keyboard_type.clear
        else if (btnText == "=") return keyboard_type.equal
        else if (btnText == "+/-") return keyboard_type.sign
        else if (btnText == "0") {
            if (this.input_text == "") return keyboard_type.clear
            else return keyboard_type.input
        }
        else return keyboard_type.input
    }

    fun updateSign(): Boolean {
        if (this.input_text != "") {
            val strFirst = this.input_text!!.first()
            if (strFirst != '-') {
                val tmp = "-" + this.input_text
                this.input_text = tmp
                this.result_view?.text = tmp
            } else {
                this.input_text = this.input_text!!.substring(1)
                this.result_view?.text = this.result_view?.text!!.substring(1)
            }
        }
        return true
    }

    fun calResult(): Boolean {
        return true
    }

    fun onClick(btn: Int, inputOp: String) {
        val clearrt = this.distinguish(btn)

        when (clearrt) {
           keyboard_type.clear -> {
               // clear
               this.history_view?.text = this.result_view?.text
               this.result_view?.text = "0"
               this.input_text = ""
           }
           keyboard_type.sign -> {
               this.updateSign()
           }
           keyboard_type.calculate -> {
               this.calResult()
           }
           keyboard_type.input -> {
               this.input_text = this.input_text?.plus(inputOp)
               this.result_view?.text = this.input_text
           }
           keyboard_type.equal -> {
               // press equal
               this.calResult()
           }
        }
    }
}
