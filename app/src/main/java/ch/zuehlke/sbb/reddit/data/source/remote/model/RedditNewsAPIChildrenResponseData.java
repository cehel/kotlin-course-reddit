package ch.zuehlke.sbb.reddit.data.source.remote.model;

import com.google.gson.annotations.Expose;

/**
 * Created by chsc on 12.11.17.
 */

public class RedditNewsAPIChildrenResponseData {

    @Expose
    private String author;

    @Expose
    private String title;

    @Expose
    private int num_comments;

    @Expose
    private long created_utc;

    @Expose
    private String thumbnail;

    @Expose
    private String url;

    @Expose
    private String id;

    @Expose
    private String permalink;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }

    public long getCreated() {
        return created_utc;
    }

    public void setCreated(long created) {
        this.created_utc = created;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }
}
