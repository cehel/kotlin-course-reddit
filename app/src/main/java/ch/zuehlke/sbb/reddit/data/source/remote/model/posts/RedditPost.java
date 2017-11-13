package ch.zuehlke.sbb.reddit.data.source.remote.model.posts;

import java.util.List;

/**
 * Created by chsc on 13.11.17.
 */

public final class RedditPost {

    public List<RedditPostElement> children = null;
    public RedditPostElement replies = null;
    public String author = null;
    public String body = null;
    public long created_utc = 0;
    public int depth = 0;
    public String body_html = null;

}