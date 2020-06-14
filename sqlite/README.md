# sqlite
simple sqlite database usage, write, read db

# ADB Shell
+ installation
    + `$ sudo apt install android-tools-adb -y`
+ activate
    + `$ adb shell`
+ basic usage
    ```shell
    $ su # switch to super user
    $ cd /data/data/com.ambersun1234.sqlite/databases # enter project database directory
    $ sqlite3 member.db
    sqlite> .schema member # show create table sql command
    ```

# Kotlin Note
+ `SQLiteOpenHelper`
    ```kotlin
    class memberdb(context: Context): SQLiteOpenHelper(context, "member.db", null, 4) {
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

        ...
    }
    ```

+ `writableDatabase`
    ```kotlin
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
    ```
+ `readableDatabase`
    ```kotlin
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
    ```
