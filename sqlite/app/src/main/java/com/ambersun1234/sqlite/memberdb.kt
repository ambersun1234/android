package com.ambersun1234.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class memberdb(context: Context): SQLiteOpenHelper(context, "member.db", null, 4) {
    private val table_name = "member"

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if not exists ? (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "sex TEXT," +
                "address TEXT);";
        db?.execSQL(sql, arrayOf(this.table_name))
        db?.close()
    }

    fun addData(
        name: String,
        sex: String,
        address: String
    ) {
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("sex", sex)
        cv.put("address", address)
        writableDatabase.insert(this.table_name, null, cv)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}