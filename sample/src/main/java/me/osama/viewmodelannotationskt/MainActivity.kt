package me.osama.viewmodelannotationskt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayListOfAKindOfObject = arrayListOf(
                AKindOfObject("Osama", "4300"),
                AKindOfObject("Rao", "1234"),
                AKindOfObject("SomeOtherDude", "2434")
        )
        with ( findViewById<RecyclerView>(R.id.main_recycler_view)){
            adapter = MainRecyclerViewAdapter(arrayListOfAKindOfObject)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
