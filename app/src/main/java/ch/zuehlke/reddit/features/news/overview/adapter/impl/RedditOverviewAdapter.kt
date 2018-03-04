package ch.zuehlke.reddit.features.news.overview.adapter.impl

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ch.zuehlke.reddit.features.news.overview.AutoUpdateableAdapter
import ch.zuehlke.reddit.features.news.overview.adapter.AdapterConstants
import ch.zuehlke.reddit.features.news.overview.adapter.ViewType
import ch.zuehlke.reddit.features.news.overview.adapter.ViewTypeDelegateAdapter
import ch.zuehlke.reddit.models.RedditNewsData
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by chsc on 12.11.17.
 */

class RedditOverviewAdapter(listener: RedditNewsDelegateAdapter.OnNewsSelectedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AutoUpdateableAdapter {

    private val loadingItem = object : ViewType {
        override val viewType: Int
            get() = AdapterConstants.LOADING
    }

    private val compare = fun(viewType: ViewType, otherViewType: ViewType): Boolean {
        when {
            viewType is RedditNewsData && otherViewType is RedditNewsData -> {
                return viewType.id == otherViewType.id
            }
            viewType is RedditNewsData && otherViewType !is RedditNewsData -> {
                return false
            }
            viewType !is RedditNewsData && otherViewType is RedditNewsData -> {
                return false
            }
            viewType !is RedditNewsData && otherViewType !is RedditNewsData -> {
                return true
            }
            else -> {
                return false
            }
        }
    }


    private var mItems: MutableList<ViewType> by Delegates.observable(mutableListOf(), { _, oldValues, newValues ->
        autoNotify(oldValues, newValues, compare)
    })

    private val adapters = HashMap<Int, ViewTypeDelegateAdapter>()

    init {
        adapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        adapters.put(AdapterConstants.NEWS, RedditNewsDelegateAdapter(listener))
        mItems.add(loadingItem)
    }

    fun addRedditNews(newsData: List<RedditNewsData>) {
        val mergedList = mutableListOf<ViewType>()
        mergedList.apply {
            addAll(mItems)
            addAll(mItems.size - 2, newsData)
        }
        mItems = mergedList
    }

    fun clearAndAddNews(newsData: List<RedditNewsData>) {
        val mergedList = mutableListOf<ViewType>()
        mergedList.apply {
            addAll(newsData)
            add(loadingItem)
        }
        mItems = mergedList

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapters[viewType]!!.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapters[getItemViewType(position)]!!.onBindViewHolder(holder, mItems[position])
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return mItems[position].viewType
    }
}
