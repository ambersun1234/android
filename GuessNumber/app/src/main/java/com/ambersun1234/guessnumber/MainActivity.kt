package com.ambersun1234.guessnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

enum class input_type {
    _del, _number, _other, _enter
}

class MainActivity : AppCompatActivity() {
    private var answer:Int = 0
    private var counter:Int = 0
    private var init:Boolean = false
    private var gp:TextView? = null
    private var ni:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.gp = findViewById(R.id.guess_prompt)
        this.ni = findViewById(R.id.number_input)
        findViewById<Button>(R.id.again_btn).setOnClickListener{
            this.reinit()
        }
        this.ni?.setOnKeyListener{v, keyCode, event -> this.handle(v, keyCode, event)}
        this.reinit()
    }

    private fun distinguish(keycode: Int): input_type {
        return if (keycode == KEYCODE_DEL) input_type._del
        else if (keycode == KEYCODE_ENTER) input_type._enter
        else if (keycode == KEYCODE_0 || keycode == KEYCODE_1 || keycode == KEYCODE_2 ||
                keycode == KEYCODE_3 || keycode == KEYCODE_4 || keycode == KEYCODE_5 ||
                keycode == KEYCODE_6 || keycode == KEYCODE_7 || keycode == KEYCODE_8 ||
                keycode == KEYCODE_9)
            input_type._number
        else input_type._other
    }

    private fun check(tmp: Int): Int {
        return if (tmp == this.answer) 0
        else if (tmp <= this.answer) -1
        else 1
    }

    private fun handle(myview:View, keycode:Int, myevent:KeyEvent): Boolean {
        val tmp = myevent.unicodeChar.toChar().toString()
        if (this.init) return true // already complete round

        if (myevent.action == MotionEvent.ACTION_UP) {
            when (this.distinguish(keycode)) {
                input_type._number -> {
                    this.ni?.text?.append(tmp)
                }
                input_type._del -> {
                    this.ni?.text = this.ni?.text?.dropLast(1) as Editable?
                }
                input_type._enter -> {
                    var tmp: String = "";
                    // check correction
                    val input = this.ni?.text.toString().toInt()
                    when (this.check(input)) {
                        0 -> {
                            // correct
                            this.init = true
                            tmp = getString(R.string.correct)
                        }
                        1 -> {
                            // too big
                            tmp = getString(R.string.tb)
                            this.counter += 1
                        }
                        -1 -> {
                            // too small
                            tmp = getString(R.string.ts)
                            this.counter += 1
                        }
                    }
                    tmp += "   " + getString(R.string.inputPrompt) + input.toString() + "\n" +
                            getString(R.string.timePrompt1) + " " +
                            this.counter.toString() + " " +
                            getString(R.string.timePrompt2)
                    this.gp?.text = tmp
                    this.ni?.text?.clear()
                }
                else -> Unit
            }
        }
        return true
    }

    private fun generate() {
        this.answer = (1..100).random()
    }

    private fun reinit() {
        this.gp?.text = getString(R.string.initPrompt)
        this.counter = 0
        this.init = false
        this.generate()
    }
}
