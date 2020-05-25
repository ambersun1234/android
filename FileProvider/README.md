# FileProvider
Simple Implicit intent uri and broadcast sender/receiver implementation

# Basic
+ User click on each button to see how these function works

# Kotlin note
+ `Implicit Intent`
    + There are two types of `intent`, `explicit intent` and `implicit intent`. Implicit intent don't name a specific component, but instead declare a general action to perform, which allow component from another app to handle it.

+ `Uri object`
    + the type of the data supplied is generally dictated by `intent's action`
    ```kotlin
    val it = Intent()
    it.data = Uri.parse("mailto:ambersun1019.shawn@gmail.com")
    it.data = Uri.parse("https://facebook.com")
    it.data = Uri.parse("geo:25.047095,121.517308")
    it.data = Uri.parse("sms:0912-345678?body=您好！")
    ```
    + c.f. Intent's action can be various from `Intent.ACTION_SENDTO, Intent.ACTION_VIEW, Intent.ACTION_WEB_SEARCH, Intent.ACTION_CALL... etc.`

+ `get permission`
    ```kotlin
    val per = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.CALL_PHONE)
    // need compare to PackageManager.PERMISSION_GRANTED or not to see if app have the permission to something(in this case we check for CALL_PHONE permission)
    ```

+ `registerReceiver`
    + On android, there are many system broadcast range from battery low, time changed and date changed
    + we can listen on to these broadcast to do some other operation
    + also, we can listen to custom broadcast too
    + `sendReceiver`
        + we have to new an Intent first, and it's action needs to be set to custom name, in my case i set it to `com.ambersun1234.fileprovider.broadcast`
        + then use `sendReceiver` method to broadcast it
        ```kotlin
        private companion object {
            const val MY_MSG = "com.ambersun1234.fileprovider.broadcast"
        }

        // custom send broadcast
        findViewById<Button>(R.id.btn_sendbroadcast).setOnClickListener{
            val it = Intent()
            it.action = MainActivity.MY_MSG
            sendBroadcast(it)
        }
        ```
    + `broadcastHandler` is relatively simple, check whether Intent's action match specific action, then do the correspond operation
    ```kotlin
    private val broadcastHandler = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var toastMsg = ""

            when (intent?.action) {
                Intent.ACTION_TIME_CHANGED -> {
                    toastMsg = "Receive! your system time has changed"
                }

                Intent.ACTION_BATTERY_CHANGED -> {
                    val level = intent.getIntExtra("level", 0)
                    val scale = intent.getIntExtra("scale", 100)
                    toastMsg = "Receive! your battery " + (level * 100 / scale) + "%"
                }

                MainActivity.MY_MSG -> {
                    toastMsg = "Receive! we received $MY_MSG"
                }
            }

            Toast.makeText(
                this@MainActivity,
                toastMsg,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    ```

+ `IntentFilter`
    + use IntentFilter to listen specific Intent action including custom broadcast, this can add one or more action
    + so the final code will be as follows
    ```kotlin
    // register broadcast
    val itf = IntentFilter()
    itf.addAction(Intent.ACTION_TIME_CHANGED)
    itf.addAction(Intent.ACTION_BATTERY_CHANGED)
    itf.addAction(MainActivity.MY_MSG)
    registerReceiver(this.broadcastHandler, itf)
    ```
