# Zodiac
Simple Zodiac app, based on recycler view

## Basic
+ User choose date and input name, system show correspondent zodiac picture and user's info on recycler view screen

## Kotlin note
+ `OnEditorActionListener`
    + another way to listen keyboard
    + layout file need to add `android:imeOptions="actionDone"`
    ```kotlin
    private val myActionListener = object: TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                storeInfo()
                createR()
                return true
            }
            return false
        }
    }
    ```
+ `resources`
    + to use resources in other file, you'll need to pass `context` to file
    + in kotlin, pass `baseContext` to class and use it as follows
        ```kotlin
        class myAdapter(
            var nameArr: ArrayList<String>,
            var dateArr: ArrayList<String>,
            var starArr: ArrayList<String>,
            var imgArr: ArrayList<Int>,
            private val mcontext: Context
        ) : RecyclerView.Adapter<myAdapter.myViewHolder>() {
            /* extends recycler view adapter
             * implement all 3 methods: onCreateViewHolder, onBindViewHolder, getItemCount
             * implementation should subclass recyclerview ViewHolder
             */

            companion object {
                lateinit var ccontext: Context
                val imgList: IntArray = intArrayOf(
                    R.mipmap.capricorn, R.mipmap.aquarius, R.mipmap.pisces,
                    R.mipmap.aries, R.mipmap.taurus, R.mipmap.gemini,
                    R.mipmap.cancer, R.mipmap.leo, R.mipmap.virgo,
                    R.mipmap.libra, R.mipmap.scorpio, R.mipmap.sagittarius
                )
            }

            init {
                ccontext = mcontext
            }
        ```
    + reference: [Using getResources() in non-activity class
](https://stackoverflow.com/a/7666630)
+ `setImageResource`
    + to set image to `ImageView`, simply run
    ```kotlin
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val imgList: IntArray = intArrayOf(
            R.mipmap.capricorn, R.mipmap.aquarius, R.mipmap.pisces,
            R.mipmap.aries, R.mipmap.taurus, R.mipmap.gemini,
            R.mipmap.cancer, R.mipmap.leo, R.mipmap.virgo,
            R.mipmap.libra, R.mipmap.scorpio, R.mipmap.sagittarius
        )

        holder.d_img.setImageResource(imgList[this.imgArr[position]])

        ...
    }
    ```

    ```kotlin
    if (intent.extras != null) {
            this.ex = intent.extras!!
            // retrieve data from activity
            this.name_arr = this.ex.getStringArrayList("name")!!
            this.date_arr = this.ex.getStringArrayList("date")!!
            this.star_arr = this.ex.getStringArrayList("star")!!
            this.img_arr = this.ex.getIntegerArrayList("img")!!
        }
    ```
+ `Intent`
    + to pass data between **activity**, specify from which activity to which activity
    + in order to prevent data loss back from activity, we need to pass data between each other instead of one way data transfer
    ```kotlin
    private val myClickListener = object: View.OnClickListener {
        override fun onClick(v: View?) {
            val it = Intent(this@MainActivity, ShowActivity::class.java)
            it.putStringArrayListExtra("name", name_arr)
            it.putStringArrayListExtra("date", date_arr)
            it.putStringArrayListExtra("star", star_arr)
            it.putIntegerArrayListExtra("img" , img_arr)
            startActivityForResult(it, 0x111)
        }
    }
    ```
+ `AlertDialog`
    + system build in alert dialog
    + to close a dialog, use `dialog.dismiss()`
    + buttons
        + `Neutral` :arrow_right: always placed at extreme left
        + `Negative` :arrow_right: packed together with postive, align left
        + `Positive` :arrow_right: packed together with postive, align right

    ```kotlin
    private val dialogHandler = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }
        }

    fun deleteInfo(v: View): Boolean {
            val ad_builder = AlertDialog.Builder(v.context)
            ad_builder.setTitle(ccontext.resources.getString(R.string.deleteConfirm))
            ad_builder.setCancelable(false)
            ad_builder.setPositiveButton(
                ccontext.resources.getString(R.string.yes), this.deleteDialogHandler
            )
            ad_builder.setNegativeButton(
                ccontext.resources.getString(R.string.no), this.dialogHandler
            )
            ad_builder.create().show()

            return true
        }
    ```
+ `DatePickerDialog`
    + system build in choose date dialog
    + note: according to the [documentation](https://stackoverflow.com/a/4467894), DatePickerDialog **month will start at 0** instead of 1
    ```kotlin
    private val dateSetListener = object: DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // set selected to TextView
            id.text = dayOfMonth.toString()
            im.text = (month + 1).toString()
        }
    }

    private val dateBtnListener = object: View.OnClickListener {
        override fun onClick(v: View?) {
            DatePickerDialog(
                this@MainActivity,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
    ```
+ `notifyDataSetChanged`
    + if data in RecyclerView has changed, manually call this function(inside or outside), it will refresh
