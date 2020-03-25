package com.ambersun1234.transformtemp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged

class MainActivity : AppCompatActivity() {
    private val transformf2mUnit:Double = 0.3048
    private val transformm2fUnit:Double = 3.28
    private var result_view:TextView? = null
    private var inputGroup:RadioGroup? = null
    private var inputText:EditText? = null

    private var choose:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.result_view = findViewById(R.id.showResult)
        this.inputText = findViewById(R.id.input_val)
        this.inputText!!.addTextChangedListener(
            object :TextWatcher{
                override fun afterTextChanged(
                    s: Editable?) {
                    Unit
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Unit
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int) {
                    calculate()
                }
            } // end implement super function
        )

        this.inputGroup = findViewById(R.id.transformG)
        this.inputGroup?.setOnCheckedChangeListener{
            group, id -> this.calInterface(group, id)
        }
    }

    companion object fun calculate(): Boolean {
        val tmp:String = this.inputText!!.text.toString()
        var result:Double = 0.0
        if (tmp == "") {
            result = 0.0
        } else {
            result = tmp.toDouble()
        }

        when (this.choose) {
            0 -> {
                result *= this.transformm2fUnit
            }
            1 -> {
                result *= this.transformf2mUnit
            }
        }
        this.result_view?.text = result.toString()
        return true
    }

    private fun calInterface(group: RadioGroup, id: Int): Boolean {
        when (id) {
            R.id.m2f_btn -> {
                this.choose = 0
            }
            R.id.f2m_btn -> {
                this.choose = 1
            }
        }
        this.calculate()
        return true
    }
}