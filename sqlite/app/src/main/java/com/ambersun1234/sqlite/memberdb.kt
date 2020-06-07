package com.ambersun1234.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import java.nio.ByteBuffer

class memberdb(context: Context): SQLiteOpenHelper(context, "member.db", null, 4) {
    private val table_name = "member"
    private val ID_FILED = "_id"
    private val NAME_FIELD = "name"
    private val SEX_FIELD = "sex"
    private val ADDRESS_FIELD = "address"
    private val IMG_FIELD = "img"

    private lateinit var mydb: SQLiteDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if not exists ${this.table_name}(" +
                "${this.ID_FILED} INTEGER PRIMARY KEY, " +
                "${this.NAME_FIELD} TEXT NOT NULL, " +
                "${this.SEX_FIELD} TEXT, " +
                "${this.IMG_FIELD} BLOB, " +
                "${this.ADDRESS_FIELD} TEXT);";
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
            arrayOf(this.NAME_FIELD, this.SEX_FIELD, this.ADDRESS_FIELD, this.IMG_FIELD),
            queryString,
            null,
            null,
            null,
            orderString
        )
        return c
    }

    fun convert2byte(img: Bitmap): ByteArray {
        val isize = img.byteCount
        val ibuffer = ByteBuffer.allocate(isize)
        val ibytes = ByteArray(isize)

        img.copyPixelsToBuffer(ibuffer)
        ibuffer.rewind()
        ibuffer.get(ibytes)

        return ibytes
    }

    fun addData(
        name: String,
        sex: String,
        address: String,
        img: Bitmap? = null
    ): Long {
        this.mydb = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("sex", sex)
        cv.put("address", address)

        if (img != null) {
            cv.put("img", this.convert2byte(img))
        }

        return this.mydb.insert(this.table_name, null, cv)
    }
}