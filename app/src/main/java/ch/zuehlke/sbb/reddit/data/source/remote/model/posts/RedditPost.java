package ch.zuehlke.sbb.reddit.data.source.remote.model.posts;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by chsc on 13.11.17.
 */

public final class RedditPost {

    @Expose
    public String id = null;
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


}