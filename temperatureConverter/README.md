# Temperature Converter
Simple temperature converter(Fahrenheit, Celsius and Kelvin)

## Basic
+ User input a temperature, system will reply the correspondent result based on selected unit
+ Support i18n(traditional chinese, english)

## Kotlin note
+ `AdapterView.OnItemSelectedListener`
    + object definition can write outside
    ```kotlin
    private val mySelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            btn_interface(parent?.id)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Unit
        }
    }
    ```
    + call it like
    ```kotlin
    this.spl!!.onItemSelectedListener = this.mySelectedListener
    ```

+ `Map`
    + use map to map something
    ```kotlin
    private var editTextMap:Map<Int, EditText> ?= null
    this.editTextMap = mapOf(
            1 to this.il!!,
            -1 to this.ir!!
        )
    ```

+ `id`
    + in this sub-project, we use id to map different operation,
    + for example
    ```kotlin
    parent: AdapterView<*>?
    // one can use follow to get object id
    parent?.id
    ```

+ `hashCode`
    + apart from the above option to distinguish operation, we can tell whether the two object are the same or not using hashCode to compare, like object's text, if they are the same, that indicate they're identical
    ```kotlin
    if (this.il?.text.hashCode() == s?.hashCode()) 1 else -1
    ```

+ `removeTextChangedListener`
    + to prevent call the handler again after change text, we can remove listener and add it back after operation
    ```kotlin
    private fun cal_init() {
        this.il?.removeTextChangedListener(this.myTextWatcher)
        this.ir?.removeTextChangedListener(this.myTextWatcher)
    }

    private fun cal_remove() {
        this.il?.setSelection(this.il!!.text.length)
        this.ir?.setSelection(this.ir!!.text.length)
        this.il?.addTextChangedListener(this.myTextWatcher)
        this.ir?.addTextChangedListener(this.myTextWatcher)
    }
    ```
