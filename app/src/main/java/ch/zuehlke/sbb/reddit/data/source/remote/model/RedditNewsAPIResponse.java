package ch.zuehlke.sbb.reddit.data.source.remote.model;

import com.google.gson.annotations.Expose;

/**
 * Created by chsc on 12.11.17.
 */

public class RedditNewsAPIResponse {

    @Expose
    private RedditNewsAPIResponseData data;
    @Expose
    private String kind;


    public RedditNewsAPIResponseData getData() {
        return data;
    }

    public void setData(RedditNewsAPIResponseData data) {
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
