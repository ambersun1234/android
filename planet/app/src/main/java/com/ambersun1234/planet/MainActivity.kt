package com.ambersun1234.planet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rview: RecyclerView
    private lateinit var rviewAdapter: RecyclerView.Adapter<*>
    private lateinit var rviewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // https://stackoverflow.com/questions/36576442/recycler-view-showing-single-item

        rview = findViewById(R.id.myrview)
        rview.setHasFixedSize(true)
        rviewManager = LinearLayoutManager(this)
        rviewAdapter = myAdapter(
            resources.getStringArray(R.array.planets_zh),
            resources.getStringArray(R.array.planets_en)
        )

        rview.layoutManager = rviewManager
        rview.adapter = rviewAdapter
    }
}
