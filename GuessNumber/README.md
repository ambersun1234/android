# Guess Number
Simple number guessing game

## Basic
+ User input a positive number, system reply whether the number is larger or smaller than result
+ System prompt user's input and game round on each round
+ Provide play again operation
+ Support i18n(traditional chinese, english)

## Kotlin note
+ `MotionEvent.ACTION_UP`
    + key event when press gesture finished
    + [Motion Event - Action up](https://developer.android.com/reference/android/view/MotionEvent#ACTION_UP)
+ `KEYCODE_ENTER`
    + android.view.KeyEvent.*
    + distinguish which key been pressed
+ `Editable`
    + This is the interface for text whose content and markup can be changed (as opposed to immutable text like Strings)
    + [Editable](https://developer.android.com/reference/kotlin/android/text/Editable)
+ `getString(R.string.prompt)`
    + method use to get i18n string in `strings.xml`
    + parameter: resource string name
