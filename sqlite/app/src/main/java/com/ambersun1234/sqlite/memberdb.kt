package com.ambersun1234.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class memberdb(context: Context): SQLiteOpenHelper(context, "member.db", null, 4) {
    private val table_name = "member"

    private lateinit var mydb: SQLiteDatabase

    companion object {
        val ID_FILED = "id"
        val NAME_FIELD = "name"
        val SEX_FIELD = "sex"
        val ADDRESS_FIELD = "address"
        val IMG_FIELD = "img"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if not exists ${this.table_name}(" +
                "${ID_FILED} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${NAME_FIELD} TEXT NOT NULL, " +
                "${SEX_FIELD} TEXT, " +
                "${IMG_FIELD} BLOB, " +
                "${ADDRESS_FIELD} TEXT);";
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${this.table_name}")
        this.onCreate(db)
    }

    fun getDataInterface(queryString: String, orderString: String): Cursor {
        return this.getData(queryString, orderString)
    }

    fun getData(queryString: String, orderString: String): Cursor {
        this.mydb = this.readableDatabase
        val c = this.mydb.query(
            this.table_name,
            null,
            queryString,
            null,
            null,
            null,
            orderString
        )
        return c
    }

    fun convert2byte(img: Bitmap): ByteArray {
        // https://stackoverflow.com/a/7620610
        var blob = ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
        val bitmapdata = blob.toByteArray();

        return bitmapdata
    }

    fun updateData(
        name: String,
        sex: String,
        address: String,
        img: Bitmap?,
        whereClause: String
    ): Int {
        this.mydb = this.writableDatabase
        val cv = ContentValues()

        cv.put(NAME_FIELD, name)
        cv.put(SEX_FIELD, sex)
        cv.put(ADDRESS_FIELD, address)

        if (img != null) {
            cv.put(IMG_FIELD, this.convert2byte(img))
        }

        return this.mydb.update(
            this.table_name,
            cv,
            whereClause,
            null
        )
    }

    fun addData(
        name: String,
        sex: String,
        address: String,
        img: Bitmap? = null
    ): Long {
        this.mydb = this.writableDatabase
        val cv = ContentValues()
        cv.put(NAME_FIELD, name)
        cv.put(SEX_FIELD, sex)
        cv.put(ADDRESS_FIELD, address)

        if (img != null) {
            cv.put(IMG_FIELD, this.convert2byte(img))
        }

        return this.mydb.insert(this.table_name, null, cv)
    }
}