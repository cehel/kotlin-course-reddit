package ch.zuehlke.reddit.models

/**
 * Created by celineheldner on 13.11.17.
 */

data class RedditPostsData(
        val id: String,
        val parentId: String?,
        val author: String?,
        val body: String?,
        val created_utc: Long,
        val depth: Int,
        val body_html: String?,
        val parentPermaLink: String?,
        val ordering: Long
)
