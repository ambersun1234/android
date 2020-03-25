# acalc
Simple android calculator

## Basic
+ acalc can perform basic calculator operation(+, -, \* and /)
+ Support i18n(traditional chinese, english)

## Kotlin note
+ `findViewById<Button>(R.id.mybtn)`
    + R indicate resource :arrow_right: find id = `mybtn`
    + use above id to find view(type `Button`)
+ `setOnClickListener{this.myhandler()}`
    + event handler when click
    + callback function `myhandler`
+ switch case in kotlin
    ```kotlin
    when(condition) {
        0 -> {
            // to something
        }
        else -> {
            Unit
        }
    }
    ```
    + same as other languages `switch case`
    + `Unit` means pass
