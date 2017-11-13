package ch.zuehlke.sbb.reddit.features.detail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ch.zuehlke.sbb.reddit.data.source.RedditDataSource;
import ch.zuehlke.sbb.reddit.data.source.RedditRepository;
import ch.zuehlke.sbb.reddit.data.source.remote.RedditAPI;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPost;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement;
import ch.zuehlke.sbb.reddit.models.RedditPostsData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ch.zuehlke.sbb.reddit.data.source.remote.RedditElementTypeAdapterFactory.getElementTypeAdapterFactory;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 13.11.17.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private static final Type type = new TypeToken<List<RedditPostElement>>() {
    }.getType();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(getElementTypeAdapterFactory())
            .create();


    private static final String TAG = "DetailPresenter";

    private final String mRedditUrl;
    private DetailContract.View mDetailView;
    private RedditRepository mRepository;
    private RedditAPI mRedditAPI;

    public DetailPresenter(@NonNull DetailContract.View detailView, @NonNull RedditRepository repository, @NonNull String redditUrl, RedditAPI redditAPI) {
        mRepository = checkNotNull(repository, "The repository cannot be null");
        mDetailView = checkNotNull(detailView, "The DetailView cannot be null");
        checkNotNull(redditUrl, "The reddit url cannot be null");
        mRedditAPI = checkNotNull(redditAPI, "RedditAPI cannot be null");
        mRedditUrl = checkNotNull(redditUrl, "The reddit url cannot be null");
        detailView.setPresenter(this);
    }

    @Override
    public void start() {
        loadRedditPosts();
    }


    @Override
    public void loadRedditPosts() {
        loadRedditPosts(mRedditUrl);
    }


    public void loadRedditPosts(String url) {
        if (mDetailView.isActive()) {
            mDetailView.setLoadingIndicator(true);
        }

        mRepository.getPosts(new RedditDataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<RedditPostsData> posts) {
                if (mDetailView.isActive()) {
                    mDetailView.setLoadingIndicator(false);
                    mDetailView.showRedditPosts(posts);
                }

            }

            @Override
            public void onDataNotAvailable() {
                if (mDetailView.isActive()) {
                    mDetailView.showRedditNewsLoadingError();
                }

            }
        }, mRedditUrl);

    }

}
