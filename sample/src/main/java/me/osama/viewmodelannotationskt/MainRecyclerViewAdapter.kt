package me.osama.viewmodelannotationskt

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class MainRecyclerViewAdapter(private val listOfObjects: List<AKindOfObject>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listOfObjects.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as MainItemViewHolder){
            bind(get(position) as AKindOfObject)
        }
    }

    fun get(atIndex : Int) : Any{
        return listOfObjects[atIndex]
    }
}