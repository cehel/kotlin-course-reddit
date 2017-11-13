package ch.zuehlke.sbb.reddit.data.source.remote.model.posts;

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

        public final String kind = null;
        public final RedditPost data = null;


        private DataRedditPostElement() {
        }

    }

    // This is a special wrapper because we cannot make java.util.String to be a subclass of the RedditPostElement class
    // Additionally, you can add more methods if necessary
    public static final class ReferenceRedditPostElement extends RedditPostElement {

        public final String reference;

        private ReferenceRedditPostElement(final String reference) {
            this.reference = reference;
        }

    }

}