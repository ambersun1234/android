# Planet
simple weekday choose based on simple adapter

## Basic
+ User choose planet, system reply

## Kotlin note
+ `RecyclerView`
    + add support library
    + dependencies add `implementation 'androidx.recyclerview:recyclerview:1.1.0'`
    + add another layout file to implement(use `LinearLayout`)
    + create new class to extend `RecyclerView.Adapter` class
        ```kotlin
        class myAdapter(
            private val zhDataset: Array<String>,
            private val enDataset: Array<String>) : RecyclerView.Adapter<myAdapter.myViewHolder>() {

        }
        ```
    + class must implement 3 function
        ```kotlin
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
            val txview: View = LayoutInflater.from(parent.context).inflate(
                R.layout.re_layout,
                parent,
                false
            )

            return myViewHolder(txview)
        }

        override fun onBindViewHolder(holder: myViewHolder, position: Int) {
            holder.d_en.setText(enDataset[position])
            holder.d_zh.setText(zhDataset[position])
        }

        override fun getItemCount() = zhDataset.size
        ```
    + `onCreateViewHolder` need to construct `RecyclerView.ViewHolder` thus we need to declare a new class which extends `RecyclerView.ViewHolder`
        ```kotlin
        class myViewHolder : RecyclerView.ViewHolder {
            constructor(tview: View) : super(tview) {

            }
        }
        ```
+ `class constructor`
    + there are 2 ways to write constructor in kotlin
        + one is write parameter behind class declaration using brackets(e.g. myAdapter class)
        + another is to call self defined constructor with super class constructor(e.g. myViewHolder class)
+ `laterinit`
    + laterinit use in just `var`
    + laterinit can't use in `null object`, or java's basic type(e.g. Int, Long)
    + laterinit can put in anywhere and can initialize multiple times
    + c.f. `by lazy`
+ how to handle object, connect to layout manager and attach adapter
    ```kotlin
    private lateinit var rview: RecyclerView
    private lateinit var rviewAdapter: RecyclerView.Adapter<*>
    private lateinit var rviewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rview = findViewById(R.id.myrview)
        rview.setHasFixedSize(true)
        rviewManager = LinearLayoutManager(this)
        rviewAdapter = myAdapter(
            resources.getStringArray(R.array.planets_zh),
            resources.getStringArray(R.array.planets_en)
        )

        rview.layoutManager = rviewManager
        rview.adapter = rviewAdapter
    }
    ```
