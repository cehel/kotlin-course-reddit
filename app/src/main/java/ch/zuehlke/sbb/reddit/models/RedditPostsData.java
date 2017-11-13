package ch.zuehlke.sbb.reddit.models;

/**
 * Created by celineheldner on 13.11.17.
 */

public class RedditPostsData {
    public String id;
    public String parentId;
    public String author = null;
    public String body = null;
    public long created_utc = 0;
    public int depth = 0;
    public String body_html = null;

    public RedditPostsData(String id, String parentId, String author, String body, long created_utc, int depth, String body_html) {
        this.id = id;
        this.parentId = parentId;
        this.author = author;
        this.body = body;
        this.created_utc = created_utc;
        this.depth = depth;
        this.body_html = body_html;
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

    public long getCreated_utc() {
        return created_utc;
    }

    public int getDepth() {
        return depth;
    }

    public String getBody_html() {
        return body_html;
    }
}
