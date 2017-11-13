package ch.zuehlke.sbb.reddit.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPost;
import ch.zuehlke.sbb.reddit.models.RedditNewsData;

/**
 * Created by chsc on 08.11.17.
 */

public interface RedditDataSource {

    interface LoadNewsCallback {

        void onNewsLoaded(List<RedditNewsData> data);

        void onDataNotAvailable();
    }

    interface LoadPostsCallback{

        void onPostsLoaded(List<RedditPost> posts);

        void onDataNotAvailable();
    }



    void getMoreNews(@NonNull LoadNewsCallback callback);

    void getNews(@NonNull LoadNewsCallback callback);

    void getPosts(@NonNull LoadPostsCallback callback);

    void refreshNews();

    void deleteAllNews();

    void saveRedditNews(@NonNull RedditNewsData data);
}
