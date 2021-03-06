package com.github.snuffix.songapp.recycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.snuffix.songapp.R
import com.github.snuffix.songapp.extensions.inflateView
import com.github.snuffix.songapp.songs.adapter.ViewItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class IncrementalSearchProgressAdapterDelegate : AdapterDelegate<List<ViewItem>>() {

    override fun onBindViewHolder(
        items: List<ViewItem>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>
    ) {
        val progress = items[position] as SearchProgress

        if (progress.show) {
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        } else {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }
    }

    override fun isForViewType(items: List<ViewItem>, position: Int) = items[position] is SearchProgress

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemView = parent.context.inflateView(R.layout.item_progress_row, parent)
        return object : RecyclerView.ViewHolder(itemView) {}
    }
}

class SearchProgress(var show: Boolean = false) : ViewItem

