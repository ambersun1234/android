# Length Converter
Simple length unit converter

## Basic
+ Input positive number for type conversion(meter, feet)
+ System reply result immediately and show on screen

## Kotlin note
+ `TextWatcher`
    + `addTextChangedListener`
    + we have to implement all three functions, `afterTextChanged`, `beforeTextChanged` and `onTextChanged`
    + it is possible to call user-defined function inside override function
    + however, can't use `this.myfunction`, it can't find myfunction
+ `companion object`
    + static function or class variable
    + kotlin doesn't have static
    + [kotlin companion object](https://medium.com/@tomazwang/%E9%82%A3%E4%BA%9B-kotlin-%E4%B8%AD%E7%9A%84%E9%9D%9C%E6%85%8B%E4%BA%8B-407139a17e5b)
