package ch.zuehlke.sbb.reddit.data.source.remote.model.posts;

/**
 * Created by chsc on 13.11.17.
 */

// Not an interface by design: it's most likely there is just two known redditPost types
public abstract class RedditPostElement {

    // So we can control they instantiation
    private RedditPostElement() {
    }

    // ... any convenient code, visitor pattern stuff here, etc ..

    public static RedditPostElement reference(final String reference) {
        return new ReferenceRedditPostElement(reference);
    }

    public static final class DataRedditPostElement extends RedditPostElement {

        public final String kind = null;
        public final RedditPost data = null;

        // Gson does requires neither constructors nor making them non-private
        private DataRedditPostElement() {
        }

    }

    // This is a special wrapper because we cannot make java.util.String to be a subclass of the RedditPostElement class
    // Additionally, you can add more methods if necessary
    public static final class ReferenceRedditPostElement extends RedditPostElement {

        public final String reference;

        // But anyway, control the way it's instantiated within the enclosing class
        private ReferenceRedditPostElement(final String reference) {
            this.reference = reference;
        }

    }

}