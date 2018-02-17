package ch.zuehlke.sbb.reddit.data.source.remote.model.news

import com.google.gson.annotations.Expose

/**
 * Created by chsc on 12.11.17.
 */

class RedditNewsAPIChildrenResponseData {

    @Expose
    var author: String? = null

    @Expose
    var title: String? = null

    @Expose
    var num_comments: Int = 0

    @Expose
    var created: Long = 0

    @Expose
    var thumbnail: String? = null

    @Expose
    var url: String? = null

    @Expose
    var id: String? = null

    @Expose
    var permalink: String? = null
}
