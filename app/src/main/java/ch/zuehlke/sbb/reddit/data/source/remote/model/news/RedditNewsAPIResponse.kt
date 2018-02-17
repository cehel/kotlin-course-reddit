package ch.zuehlke.sbb.reddit.data.source.remote.model.news

import com.google.gson.annotations.Expose

/**
 * Created by chsc on 12.11.17.
 */

class RedditNewsAPIResponse {

    @Expose
    var data: RedditNewsAPIResponseData? = null
    @Expose
    var kind: String? = null
}
