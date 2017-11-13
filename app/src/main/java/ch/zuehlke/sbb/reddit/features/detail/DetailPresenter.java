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
import java.util.Collection;
import java.util.List;

import ch.zuehlke.sbb.reddit.Injection;
import ch.zuehlke.sbb.reddit.data.source.RedditRepository;
import ch.zuehlke.sbb.reddit.data.source.remote.RedditAPI;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPost;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement;
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

    private static final Type type = new TypeToken<List<RedditPostElement>>(){}.getType();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(getElementTypeAdapterFactory())
            .create();



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

        mRedditAPI.getRedditPosts(mRedditUrl,"new").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG,"response: "+response);


                try {
                    final List<RedditPostElement> redditPostElements = gson.fromJson(response.body().string(), type);
                    List<RedditPost> parsedElements = flattenRetrofitResponse(redditPostElements);
                    if(mDetailView.isActive()){
                        mDetailView.showRedditPosts(parsedElements);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private List<RedditPost> flattenRetrofitResponse(List<RedditPostElement> response){
        List<RedditPost> flatten = new ArrayList<>();
        for (RedditPostElement redditPostElement : response) {
            if(redditPostElement instanceof RedditPostElement.DataRedditPostElement){
                RedditPostElement.DataRedditPostElement dataElement = (RedditPostElement.DataRedditPostElement) redditPostElement;
                if(dataElement.data != null){
                    if(!Strings.isNullOrEmpty(dataElement.data.body_html)){
                        flatten.add(dataElement.data);
                    }else{
                        flatten.addAll(recursivlyParseResponse(dataElement));
                    }
                }
            }
        }
        return flatten;
    }

    private List<RedditPost> recursivlyParseResponse(RedditPostElement.DataRedditPostElement dataRedditPostElement){
        List<RedditPost> posts = new ArrayList<>();
        for (RedditPostElement child : dataRedditPostElement.data.children) {
            if(child instanceof RedditPostElement.DataRedditPostElement){
                RedditPostElement.DataRedditPostElement childData = (RedditPostElement.DataRedditPostElement) child;
                if(childData.data != null){
                    posts.add(childData.data);
                    if(childData.data.replies != null && childData.data.replies instanceof RedditPostElement.DataRedditPostElement){
                        posts.addAll(recursivlyParseResponse((RedditPostElement.DataRedditPostElement) childData.data.replies));
                    }
                }
            }

        }
        return posts;
    }



}
