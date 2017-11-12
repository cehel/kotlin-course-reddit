package ch.zuehlke.sbb.reddit.data.source.remote.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by chsc on 12.11.17.
 */

public class RedditNewsAPIResponseData {

    @Expose
    private List<RedditNewsAPIChildrenResponse> children;
    @Expose
    private String after;
    @Expose
    private String before;

    public List<RedditNewsAPIChildrenResponse> getChildren() {
        return children;
    }

    public void setChildren(List<RedditNewsAPIChildrenResponse> children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
