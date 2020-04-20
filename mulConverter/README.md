# mulConverter
A simple multi unit converter

# Basic
+ User choose length or area and then choose unit to calculate
+ The result will last until user press enter

# Kotlin note
+ `View.VISIBLE`
    + to enable/disable view by setting `visibility` to `VISIBLE` or `INVISIBLE`
+ `setOnKeyListener`
    + to catch user keycode event, use this listener to set handler
    ```kotlin
    private fun handle(v: View, keycode: Int, event: KeyEvent): Boolean {
        if (keycode == KEYCODE_ENTER) {
            this.calculate()
        }
        return false
    }
    ```
    + in the above code, we catch `KEYCODE_ENTER` to execute `this.calculate` function, however, if you `return true` in the end, the input won't show on the EditText, it need to `return false`
+ `floating point arithmetic`
    + in other programming language, if you want to divide variable a with 2, you simple write `a / 2`. But with kotlin, it seems like `a / 2.0` is the correct way to do floating point arithmetic, otherwise the result will always be `0`
