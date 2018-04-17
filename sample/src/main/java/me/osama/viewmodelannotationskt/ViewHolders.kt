package me.osama.viewmodelannotationskt

import android.support.v7.widget.RecyclerView
import android.view.View
import me.osama.vmannotation.BindField

abstract class BaseViewHolder<T>(val itemViewHere: View) : RecyclerView.ViewHolder(itemViewHere) {

    var boundItem: T? = null

    open fun bind(item: T) {
        boundItem = item
    }
}

class MainItemViewHolder(itemView: View) : BaseViewHolder<AKindOfObject>(itemView) {
    @BindField(viewIds = ["name", "amount"], viewName = "itemViewHere")
    override fun bind(item: AKindOfObject) {
        bindFields(item, itemViewHere)
    }
}