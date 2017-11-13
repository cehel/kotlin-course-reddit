package ch.zuehlke.sbb.reddit.data.source.remote.model.posts;

import com.google.gson.annotations.Expose;

/**
 * Created by chsc on 13.11.17.
 */


public abstract class RedditPostElement {


    private RedditPostElement() {
    }



    public static RedditPostElement reference(final String reference) {
        return new ReferenceRedditPostElement(reference);
    }



    public static final class DataRedditPostElement extends RedditPostElement {

        @Expose
        public final String kind = null;
        @Expose
        public final RedditPost data = null;

        public String getKind() {
            return kind;
        }

        public RedditPost getData() {
            return data;
        }

        public DataRedditPostElement() {
        }

    }

    // This is a special wrapper because we cannot make java.util.String to be a subclass of the RedditPostElement class
    // Additionally, you can add more methods if necessary
    public static final class ReferenceRedditPostElement extends RedditPostElement {

        @Expose
        public final String reference;

        private ReferenceRedditPostElement(final String reference) {
            this.reference = reference;
        }

    }

}