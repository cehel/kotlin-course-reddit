package ch.zuehlke.reddit.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ch.zuehlke.reddit.features.news.overview.adapter.AdapterConstants
import ch.zuehlke.reddit.features.news.overview.adapter.ViewType

/**
 * Created by chsc on 08.11.17.
 */
@Entity
data class RedditNewsData(
        var author: String? = null,
        var title: String? = null,
        var numberOfComments: Int = 0,
        var created: Long = 0,
        var thumbnailUrl: String? = "",
        var url: String? = "",
        @PrimaryKey var id: String = "",
        var permaLink: String = ""

): ViewType{
    override fun getViewType(): Int {
        return AdapterConstants.NEWS
    }
}
