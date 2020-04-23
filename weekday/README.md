# Weekday
simple weekday choose based on simple adapter

## Basic
+ User choose their favorite day, system show prompt

## Kotlin note
+ `R.layout.support_simple_spinner_dropdown_item`
    + system default layout
    + specify how system gonna layout
+ `ArrayAdapter`
    + use array adapter to give listview data
    ```kotlin
    val lv = findViewById<ListView>(R.id.mylistview)

    lv.adapter = ArrayAdapter<String>(
        this,
        R.layout.support_simple_spinner_dropdown_item,
        resources.getStringArray(R.array.weekday_list)
    )
    ```
+ `SimpleAdapter`
    + another way to implement listview
    + we need to first create a new layout using `LinearLayout`, then add the element you want
    + then add source element to dataList, finally using SimpleAdapter to initialize whole listview
    ```kotlin
    val dataList: ArrayList<Map<String, Any>> = ArrayList()

        for (i in 0..6) {
            val tmap = hashMapOf<String, Any>()
            tmap.put("myimg", if (i % 2 == 0) android.R.drawable.btn_star else android.R.drawable.btn_radio)
            tmap.put("mytext", resources.getStringArray(R.array.weekday_list)[i])
            tmap.put("mytext2", resources.getStringArray(R.array.weekday_list_en)[i])
            dataList.add(tmap)
        }

        this.myadapter = SimpleAdapter(
            this,
            dataList,
            R.layout.listview_pic,
            arrayOf("myimg", "mytext", "mytext2"),
            intArrayOf(R.id.myimageView, R.id.mytextView, R.id.mytextView2)
        )
    ```
