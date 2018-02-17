package ch.zuehlke.sbb.reddit.models

/**
 * Created by celineheldner on 13.11.17.
 */

class RedditPostsData(var id: String, var parentId: String?, author: String?, body: String?, created_utc: Long, depth: Int, body_html: String?, parentPermaLink: String?, ordering: Long) {
    var author: String? = null
    var body: String? = null
    var createdUtc: Long = 0
    var depth = 0
    var ordering = -1L
    var body_html: String? = null
    var parentPermaLink: String? = null

    init {
        this.author = author
        this.body = body
        this.createdUtc = created_utc
        this.depth = depth
        this.body_html = body_html
        this.parentPermaLink = parentPermaLink
        this.ordering = ordering
    }
}
