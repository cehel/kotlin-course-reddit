package ch.zuehlke.sbb.reddit.data.source.remote.model;

import com.google.gson.annotations.Expose;

/**
 * Created by chsc on 12.11.17.
 */

public class RedditNewsAPIChildrenResponse {

    @Expose
    private String kind;

    @Expose
    private RedditNewsAPIChildrenResponseData data;

    public RedditNewsAPIChildrenResponseData getData() {
        return data;
    }

    public void setData(RedditNewsAPIChildrenResponseData data) {
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
