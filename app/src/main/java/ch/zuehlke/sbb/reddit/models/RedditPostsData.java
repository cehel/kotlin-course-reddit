package ch.zuehlke.sbb.reddit.models;

/**
 * Created by celineheldner on 13.11.17.
 */

public class RedditPostsData {
    public String id;
    public String parentId;
    public String author = null;
    public String body = null;
    public long createdUtc = 0;
    public int depth = 0;
    public long ordering = -1L;
    public String body_html = null;
    public String parentPermaLink = null;

    public RedditPostsData(String id, String parentId, String author, String body, long created_utc, int depth, String body_html, String parentPermaLink, long ordering) {
        this.id = id;
        this.parentId = parentId;
        this.author = author;
        this.body = body;
        this.createdUtc = created_utc;
        this.depth = depth;
        this.body_html = body_html;
        this.parentPermaLink = parentPermaLink;
        this.ordering = ordering;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public int getDepth() {
        return depth;
    }

    public String getBody_html() {
        return body_html;
    }

    public long getOrdering() {
        return ordering;
    }

    public String getParentPermaLink() {
        return parentPermaLink;
    }
}
