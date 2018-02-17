package ch.zuehlke.sbb.reddit.data.source.remote.model.news

import com.google.gson.annotations.Expose

/**
 * Created by chsc on 12.11.17.
 */

class RedditNewsAPIChildrenResponse {

    @Expose
    var kind: String? = null

    @Expose
    var data: RedditNewsAPIChildrenResponseData? = null
}
