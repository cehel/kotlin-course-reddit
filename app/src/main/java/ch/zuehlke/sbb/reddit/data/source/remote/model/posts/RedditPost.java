package ch.zuehlke.sbb.reddit.data.source.remote.model.posts;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by chsc on 13.11.17.
 */

public final class RedditPost {

    @Expose
    public List<RedditPostElement> children = null;
    @Expose
    public RedditPostElement replies = null;
    @Expose
    public String author = null;
    @Expose
    public String body = null;
    @Expose
    public long created_utc = 0;
    @Expose
    public int depth = 0;
    @Expose
    public String body_html = null;

    public RedditPost(){}

    public List<RedditPostElement> getChildren() {
        return children;
    }

    public void setChildren(List<RedditPostElement> children) {
        this.children = children;
    }

    public RedditPostElement getReplies() {
        return replies;
    }

    public void setReplies(RedditPostElement replies) {
        this.replies = replies;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getCreated_utc() {
        return created_utc;
    }

    public void setCreated_utc(long created_utc) {
        this.created_utc = created_utc;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getBody_html() {
        return body_html;
    }

    public void setBody_html(String body_html) {
        this.body_html = body_html;
    }
}