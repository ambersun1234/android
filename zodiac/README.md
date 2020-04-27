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
            private val nameArr: ArrayList<String>,
            private val dateArr: ArrayList<String>,
            private val starArr: ArrayList<String>,
            private val imgArr: ArrayList<Int>,
            private val mcontext: Context
        ) : RecyclerView.Adapter<myAdapter.myViewHolder>() {

            companion object {
                lateinit var ccontext: Context
            }

            init {
                ccontext = mcontext
            }

            ...
            ccontext.resources.getStringArray(R.array.star_en)[index]
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
