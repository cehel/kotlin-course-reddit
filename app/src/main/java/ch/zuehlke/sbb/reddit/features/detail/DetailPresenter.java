package ch.zuehlke.sbb.reddit.features.detail;

import android.support.annotation.NonNull;
import android.util.Log;

import ch.zuehlke.sbb.reddit.data.source.RedditRepository;
import ch.zuehlke.sbb.reddit.data.source.remote.RedditAPI;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 13.11.17.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private static final String TAG = "DetailPresenter";

    private final String mRedditUrl;
    private DetailContract.View mDetailView;
    private RedditRepository mRepository;
    private RedditAPI mRedditAPI;

    public DetailPresenter(@NonNull  DetailContract.View detailView, @NonNull RedditRepository repository, @NonNull String redditUrl, RedditAPI redditAPI){
            mRepository = checkNotNull(repository, "The repository cannot be null");
            mDetailView = checkNotNull(detailView,"The DetailView cannot be null");
            checkNotNull(redditUrl, "The reddit url cannot be null");
            mRedditAPI = checkNotNull(redditAPI, "RedditAPI cannot be null");
            mRedditUrl = checkNotNull(redditUrl,"The reddit url cannot be null");
            detailView.setPresenter(this);
    }

    @Override
    public void start() {
            loadRedditPosts(mRedditUrl);
    }

    @Override
    public void loadRedditPosts(String url) {
        mRedditAPI.getRedditPosts(mRedditUrl,"new").enqueue(new Callback<RedditPostElement>() {
            @Override
            public void onResponse(Call<RedditPostElement> call, Response<RedditPostElement> response) {
                Log.i(TAG,"response: "+response);
            }

            @Override
            public void onFailure(Call<RedditPostElement> call, Throwable t) {

            }
        });
    }
}
