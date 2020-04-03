package com.ambersun1234.temperatureconverter

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private var spl:Spinner ?= null
    private var spr:Spinner ?= null
    private var il:EditText ?= null
    private var ir:EditText ?= null

    private var editTextMap:Map<Int, EditText> ?= null
    private var spinnerMap:Map<Int, Spinner>   ?= null

    private val myTextWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Unit
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            Unit
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            text_interface(s)
        }
    }

    private val mySelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            btn_interface(parent?.id)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Unit
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.spl = findViewById(R.id.spinnerLeft)
        this.spr = findViewById(R.id.spinnerRight)
        this.il  = findViewById(R.id.inputLeft)
        this.ir  = findViewById(R.id.inputRight)

        this.il!!.addTextChangedListener(this.myTextWatcher)
        this.ir!!.addTextChangedListener(this.myTextWatcher)
        // https://stackoverflow.com/a/56996020

        this.spl!!.onItemSelectedListener = this.mySelectedListener
        this.spr!!.onItemSelectedListener = this.mySelectedListener

        this.editTextMap = mapOf(
            1 to this.il!!,
            -1 to this.ir!!
        )
        this.spinnerMap = mapOf(
            1 to this.spl!!,
            -1 to this.spr!!
        )
        // end of create function
    }

    private fun convert2Kelvin(case: String, input: Double): Double {
        var result: Double = 0.0

        when (case) {
            // Fahrenheit
            resources.getStringArray(R.array.temperatureList)[0] ->
                result = (input + 459.67) * (5.0 / 9.0)

            // Celsius
            resources.getStringArray(R.array.temperatureList)[1] ->
                result = input + 273.15

            // Kelvin
            resources.getStringArray(R.array.temperatureList)[2] ->
                result = input
        }
        return result
    }

    private fun convert2Fahrenheit(case: String, input: Double): Double {
        var result: Double = 0.0

        when (case) {
            // Fahrenheit
            resources.getStringArray(R.array.temperatureList)[0] ->
                result = input

            // Celsius
            resources.getStringArray(R.array.temperatureList)[1] ->
                result = (input * (9.0 / 5.0)) + 32.0

            // Kelvin
            resources.getStringArray(R.array.temperatureList)[2] ->
                result = (input * (9.0 / 5.0)) - 459.67
        }
        return result
    }

    private fun convert2Celsius(case: String, input: Double): Double {
        var result: Double = 0.0

        when (case) {
            // Fahrenheit
            resources.getStringArray(R.array.temperatureList)[0] ->
                result = (5.0 / 9.0) * (input - 32.0)

            // Celsius
            resources.getStringArray(R.array.temperatureList)[1] ->
                result = input

            // Kelvin
            resources.getStringArray(R.array.temperatureList)[2] ->
                result = input - 273.15
        }
        return result
    }

    private fun cal_init() {
        this.il?.removeTextChangedListener(this.myTextWatcher)
        this.ir?.removeTextChangedListener(this.myTextWatcher)
    }

    private fun cal_remove() {
        this.il?.setSelection(this.il!!.text.length)
        this.ir?.setSelection(this.ir!!.text.length)
        this.il?.addTextChangedListener(this.myTextWatcher)
        this.ir?.addTextChangedListener(this.myTextWatcher)
    }

    private fun cal_format(input: Double): String {
        val df = DecimalFormat("#.###")
        return df.format(input).toString()
    }

    private fun btn_interface(btn: Int?) {
        // check which input field
        // need to pass opposite side of input
        // since btn need to change self
        this.calculate(if (btn == this.spl?.id) -1 else 1)
    }

    private fun text_interface(s: CharSequence?) {
        // check which input field
        this.calculate(if (this.il?.text.hashCode() == s?.hashCode()) 1 else -1)
    }

    private fun calculate(input: Int): Boolean {
        this.cal_init()

        val inputResult: String = (this.editTextMap!![input] ?: error("")).text.toString()
        val inputVal: Double = if (inputResult == "" || inputResult == "-") 0.0 else inputResult.toDouble()

        if ((this.spinnerMap!![input] ?: error("")).selectedItem.toString() ==
            (this.spinnerMap!![input * -1] ?: error("")).selectedItem.toString()) {
            // same spinner
            (this.editTextMap!![input * -1] ?: error("")).setText(inputResult)
        } else {
            // different spinner choose
            when ((this.spinnerMap!![input * -1] ?: error("")).selectedItem.toString()) {
                resources.getStringArray(R.array.temperatureList)[0] -> {
                    // f
                    val result = this.convert2Fahrenheit(
                        (this.spinnerMap!![input] ?: error("")).selectedItem.toString(),
                        inputVal
                    )
                    this.editTextMap!![input * -1]?.setText(this.cal_format(result))
                }

                resources.getStringArray(R.array.temperatureList)[1] -> {
                    // c
                    val result = this.convert2Celsius(
                        (this.spinnerMap!![input] ?: error("")).selectedItem.toString(),
                        inputVal
                    )
                    this.editTextMap!![input * -1]?.setText(this.cal_format(result))
                }

                resources.getStringArray(R.array.temperatureList)[2] -> {
                    // k
                    val result = this.convert2Kelvin(
                        (this.spinnerMap!![input] ?: error("")).selectedItem.toString(),
                        inputVal
                    )
                    this.editTextMap!![input * -1]?.setText(this.cal_format(result))
                }
            }
        }

        this.cal_remove()
        return true
    }
}
