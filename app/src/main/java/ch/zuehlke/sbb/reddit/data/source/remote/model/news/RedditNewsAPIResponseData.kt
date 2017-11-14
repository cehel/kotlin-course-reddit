package ch.zuehlke.sbb.reddit.data.source.remote.model.news

import com.google.gson.annotations.Expose

/**
 * Created by chsc on 12.11.17.
 */

class RedditNewsAPIResponseData {

    @Expose
    var children: List<RedditNewsAPIChildrenResponse>? = null
    @Expose
    var after: String? = null
    @Expose
    var before: String? = null
}
