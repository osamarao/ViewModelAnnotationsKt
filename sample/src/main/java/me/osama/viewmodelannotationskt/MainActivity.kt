package me.osama.viewmodelannotationskt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.osama.vmannotation.BindField

class MainActivity : AppCompatActivity() {

    val billList = listOf(
            Bill("Osama", "4300"),
            Bill("Rao", "1234"),
            Bill("SomeOtherDude", "2434")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.main_recycler_view).run {
            adapter = MainRecyclerViewAdapter(billList)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}

class MainRecyclerViewAdapter(private val listOfObjects: List<Bill>) : RecyclerView.Adapter<MainItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        return MainItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listOfObjects.size
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.bind(listOfObjects[position])
    }
}

data class Bill(val name: String, val amount: String)


class MainItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @BindField(viewIds = ["name", "amount"], viewName = "view")
    fun bind(item: Bill) {
        bindFields(item, itemView)
    }
}