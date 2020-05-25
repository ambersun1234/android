package com.ambersun1234.fileprovider

import android.app.SearchManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private companion object {
        const val MY_MSG = "com.ambersun1234.fileprovider.broadcast"
    }
    private lateinit var btn_arr: Array<Int>
    private val broadcastHandler = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var toastMsg = ""

            when (intent?.action) {
                Intent.ACTION_TIME_CHANGED -> {
                    toastMsg = "Receive! your system time has changed"
                }

                Intent.ACTION_BATTERY_CHANGED -> {
                    val level = intent.getIntExtra("level", 0)
                    val scale = intent.getIntExtra("scale", 100)
                    toastMsg = "Receive! your battery " + (level * 100 / scale) + "%"
                }

                MainActivity.MY_MSG -> {
                    toastMsg = "Receive! we received $MY_MSG"
                }
            }

            Toast.makeText(
                this@MainActivity,
                toastMsg,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val onClickHandler = View.OnClickListener { v ->
        val it = Intent(Intent.ACTION_VIEW)
        val myid = v?.id

        when (myid) {
            R.id.btn_email -> {
                it.action = Intent.ACTION_SENDTO
                it.data = Uri.parse("mailto:ambersun1019.shawn@gmail.com")
                it.putExtra(Intent.EXTRA_CC, "xxx@gmail.com")
                it.putExtra(Intent.EXTRA_SUBJECT, "uri intent test")
                it.putExtra(Intent.EXTRA_TEXT, "testing")
            }

            R.id.btn_web -> {
                it.action = Intent.ACTION_VIEW
                it.data = Uri.parse("https://facebook.com")
            }

            R.id.btn_websearch -> {
                it.action = Intent.ACTION_WEB_SEARCH
                it.putExtra(SearchManager.QUERY, "mysearch")
            }

            R.id.btn_gps -> {
                it.action = Intent.ACTION_VIEW
                it.data = Uri.parse("geo:25.047095,121.517308")
            }

            R.id.btn_text -> {
                it.action = Intent.ACTION_VIEW
                it.data = Uri.parse("sms:0912-345678?body=您好！")
            }

            R.id.btn_tel -> {
                // check permission
                val chk_permission = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.CALL_PHONE)
                val lala = 1

                if (chk_permission != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, android.Manifest.permission.READ_CONTACTS)) {
                        Unit
                    }
                    else {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(android.Manifest.permission.CALL_PHONE),
                            lala
                        )
                    }
                }
                else {
                    it.action = Intent.ACTION_CALL
                    it.data = Uri.parse("tel:0958-967228")
                }
            }
        }

        startActivity(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.btn_arr = arrayOf(
            R.id.btn_email, R.id.btn_tel,
            R.id.btn_gps, R.id.btn_text,
            R.id.btn_web, R.id.btn_websearch
        )
        for (i: Int in this.btn_arr) {
            val tmp = findViewById<Button>(i)
            tmp.setOnClickListener(this.onClickHandler)
        }

        // custom send broadcast
        findViewById<Button>(R.id.btn_sendbroadcast).setOnClickListener{
            val it = Intent()
            it.action = MainActivity.MY_MSG
            sendBroadcast(it)
        }

        // register broadcast
        val itf = IntentFilter()
        itf.addAction(Intent.ACTION_TIME_CHANGED)
        itf.addAction(Intent.ACTION_BATTERY_CHANGED)
        itf.addAction(MainActivity.MY_MSG)
        registerReceiver(this.broadcastHandler, itf)
    }
}
