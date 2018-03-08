package ch.zuehlke.reddit.features.news.overview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

/**
 * Created by chsc on 04.03.18.
 */
interface AutoUpdateableAdapter {

    fun <T> RecyclerView.Adapter<*>.autoNotify(oldItems: List<T>, newItems: List<T>, compareFunction: (T, T) -> Boolean) {
        val diffUtil = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    compareFunction(oldItems[oldItemPosition], newItems[newItemPosition])

            override fun getOldListSize() = oldItems.size

            override fun getNewListSize() = newItems.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    oldItems[oldItemPosition] == newItems[newItemPosition]


        })
        return diffUtil.dispatchUpdatesTo(this)
    }
}