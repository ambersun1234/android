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
    _operand, _number, _other
}

class MainActivity : AppCompatActivity() {

    private var history_view:TextView? = null
    private var result_view:TextView? = null
    private var input_text:String? = ""
    private var f_num:Double = 0.0
    private var l_num:Double = 0.0
    private var press:Boolean = false
    private var vinit:Boolean = true
    private var oldOp:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        history_view = findViewById(R.id.history_input)
        result_view = findViewById(R.id.acalc_input)

        val btnList:Array<Int> = arrayOf(
            R.id.keyboard_num0, R.id.keyboard_num1, R.id.keyboard_num2, R.id.keyboard_num3,
            R.id.keyboard_num4, R.id.keyboard_num5, R.id.keyboard_num6, R.id.keyboard_num7,
            R.id.keyboard_num8, R.id.keyboard_num9,
            R.id.keyboard_sign, R.id.keyboard_divide, R.id.keyboard_multiple,
            R.id.keyboard_minus, R.id.keyboard_add, R.id.keyboard_point, R.id.keyboard_equal
        )

        btnList.forEach {
            val id = it
            val t_btn = findViewById<Button>(it)
            t_btn.setOnClickListener{
                this.onClick(id, t_btn.text.toString())
            }
        }

        val cbtn = findViewById<Button>(R.id.keyboard_clear)
        cbtn.setOnLongClickListener{
            this.onLongClick()
        }
    }

    fun distinguish(input: Int): keyboard_type {
        val btnText = findViewById<Button>(input).text.toString()

        if (btnText == "+" || btnText == "-" ||
            btnText == "X" || btnText == "/") return keyboard_type._operand
        else if (btnText == "1" || btnText == "2" || btnText == "3" || btnText == "4" || btnText == "5" ||
                 btnText == "6" || btnText == "7" || btnText == "8" || btnText == "9" || btnText == "0")
            return keyboard_type._number
        else return keyboard_type._other
    }

    fun updateSign(): Boolean {
        val tmp = this.result_view?.text.toString().toDouble() * -1
        this.result_view?.text = tmp.toString()
        return true
    }

    fun valueClick(input: String) {
        this.input_text = this.input_text?.plus(input)
        this.result_view?.text = this.input_text
        this.press = true
    }

    fun calculate(): Double {
        var res:Double = 0.0

        when (this.oldOp) {
            "+" -> {
                res = this.f_num + this.l_num
            }
            "-" -> {
                res = this.f_num - this.l_num
            }
            "X" -> {
                res = this.f_num * this.l_num
            }
            "/" -> {
                res = this.f_num / this.l_num
            }
            else -> {
                res = 0.0
            }
        }
        return res
    }

    fun calInterface(nop:String): Double {
        val res:Double = this.calculate()
        this.f_num = res
        this.oldOp = if (nop == "=") "" else nop

        return res
    }

    fun initCheck(op:String): Double {
        if (op == "+" || op == "-") return 0.0
        else return 1.0
    }

    fun onLongClick(): Boolean {
        this.input_text = ""
        this.result_view?.text = this.input_text
        this.history_view?.text = this.input_text

        return true
    }

    fun onClick(btn: Int, inputOp: String) {
        val operText = this.distinguish(btn)

        when (operText) {
            keyboard_type._operand -> {
                if (this.press) {
                    val tmp = this.result_view?.text.toString().toDouble()
                    this.oldOp = if (this.vinit) inputOp else this.oldOp
                    this.l_num = if (this.vinit) this.initCheck(inputOp) else tmp
                    this.f_num = if (this.vinit) tmp else this.f_num

                    this.history_view?.append(this.input_text + inputOp)
                    this.input_text = ""

                    val res:Double = this.calInterface(inputOp)

                    this.result_view?.text = res.toString()
                    this.vinit = false
                    this.press = false
                }
            }

            keyboard_type._number -> {
                this.valueClick(inputOp)
            }

            keyboard_type._other -> {
                when (inputOp) {
                    "+/-" -> {
                        this.updateSign()
                    }

                    "=" -> {
                        if (!this.vinit) {
                            this.l_num = this.result_view?.text.toString().toDouble()
                            val res:Double = this.calInterface(inputOp)
                            this.f_num = res

                            this.history_view?.append(this.result_view?.text)
                            this.result_view?.text = res.toString()
                            this.vinit = true
                            this.input_text = ""
                        }
                    }
                } // end other switch
            }
        }
    }
}
