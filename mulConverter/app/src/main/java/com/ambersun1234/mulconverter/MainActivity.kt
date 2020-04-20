package com.ambersun1234.mulconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var sp: Spinner ?= null
    private var rgl: RadioGroup ?= null
    private var rgl2: RadioGroup ?= null
    private var linput: EditText ?= null
    private var rinput: EditText ?= null

    private val mySelectHandler = object: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            Unit
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (spinner.selectedItem.toString() == "長度") {
                rgl?.visibility = View.VISIBLE
                rgl2?.visibility = View.INVISIBLE
            } else {
                rgl?.visibility = View.INVISIBLE
                rgl2?.visibility = View.VISIBLE
            }

            calculate()
        }
    }

    private val myBtnHandler = object: RadioGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
            calculate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.sp = findViewById(R.id.spinner)
        this.rgl = findViewById(R.id.rgLength)
        this.rgl2 = findViewById(R.id.rg2Length)
        this.linput = findViewById(R.id.lvalue)
        this.rinput = findViewById(R.id.rvalue)

        // check first item
        this.rgl?.check(R.id.ft2in)
        this.rgl2?.check(R.id.a2b)
        // spinner array adapter
        this.sp?.adapter = ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                resources.getStringArray(R.array.spinnerArr)
        )

        // listener
        this.linput?.setOnKeyListener{v, keyCode, event -> this.handle(v, keyCode, event)}
        this.sp!!.onItemSelectedListener = this.mySelectHandler
        this.rgl!!.setOnCheckedChangeListener(this.myBtnHandler)
        this.rgl2!!.setOnCheckedChangeListener(this.myBtnHandler)
    }

    private fun handle(v: View, keycode: Int, event: KeyEvent): Boolean {
        if (keycode == KEYCODE_ENTER) {
            this.calculate()
        }
        return false
    }

    private fun calculate() {
        val lv = this.linput!!.text.toString()
        var tmp: Double = 0.0
        if (lv != "") {
            tmp = lv.toDouble()
        }

        when (this.sp!!.selectedItem.toString()) {
            "長度" -> when (this.rgl!!.checkedRadioButtonId) {
                R.id.ft2in -> {
                    this.rinput!!.setText((tmp * (1.0 / 12.0)).toString())
                }

                R.id.ft2yd -> {
                    this.rinput!!.setText((tmp * (1.0 / 3.0)).toString())
                }

                R.id.in2ft -> {
                    this.rinput!!.setText((tmp * 12).toString())
                }

                R.id.in2yd -> {
                    this.rinput!!.setText((tmp / 36.0).toString())
                }

                R.id.yd2ft -> {
                    this.rinput!!.setText((tmp * 3.0).toString())
                }

                R.id.yd2in -> {
                    this.rinput!!.setText((tmp * 36.0).toString())
                }
            }
            "面積" -> when (this.rgl2!!.checkedRadioButtonId) {
                R.id.a2b -> {
                    this.rinput!!.setText((tmp * 0.325).toString())
                }
                R.id.b2a -> {
                    this.rinput!!.setText((tmp * 3.3058).toString())
                }
            }
            // end out when
        }
    }
}
