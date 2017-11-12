package ch.zuehlke.sbb.reddit.models;

import ch.zuehlke.sbb.reddit.features.overview.adapter.AdapterConstants;
import ch.zuehlke.sbb.reddit.features.overview.adapter.ViewType;

/**
 * Created by chsc on 08.11.17.
 */

public class RedditNewsData implements ViewType {

    private String author;
    private String title;
    private int numberOfComments;
    private long created;
    private String thumbnailUrl;
    private String url;
    private String id;
    private String permaLink;

    public RedditNewsData(String author, String title, int numberOfComments, long created, String thumbnailUrl, String url, String id, String permaLink) {
        this.author = author;
        this.title = title;
        this.numberOfComments = numberOfComments;
        this.created = created;
        this.thumbnailUrl = thumbnailUrl;
        this.url = url;
        this.id = id;
        this.permaLink = permaLink;
    }

    public RedditNewsData(){}

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

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    @Override
    public int getViewType() {
        return AdapterConstants.NEWS;
    }
}
