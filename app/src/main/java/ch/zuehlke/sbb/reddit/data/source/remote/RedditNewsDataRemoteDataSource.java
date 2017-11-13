package ch.zuehlke.sbb.reddit.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.zuehlke.sbb.reddit.Injection;
import ch.zuehlke.sbb.reddit.data.source.RedditDataSource;
import ch.zuehlke.sbb.reddit.data.source.remote.model.news.RedditNewsAPIChildrenResponse;
import ch.zuehlke.sbb.reddit.data.source.remote.model.news.RedditNewsAPIChildrenResponseData;
import ch.zuehlke.sbb.reddit.data.source.remote.model.news.RedditNewsAPIResponse;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPost;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement;
import ch.zuehlke.sbb.reddit.models.RedditNewsData;
import ch.zuehlke.sbb.reddit.models.RedditPostsData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 08.11.17.
 */

public class RedditNewsDataRemoteDataSource implements RedditDataSource {

    private static final String TAG = "RemoteDataSource";
    private String after = "";

    private static RedditNewsDataRemoteDataSource INSTANCE;
    private RedditAPI mRedditAPI;

    // Prevent direct instantiation.
    private RedditNewsDataRemoteDataSource(@NonNull Context context,@NonNull  RedditAPI redditAPI) {
        checkNotNull(context);
        mRedditAPI = checkNotNull(redditAPI,"The reddit api cannot be null");

    }

    public static RedditNewsDataRemoteDataSource getInstance(@NonNull Context context,@NonNull RedditAPI redditAPI) {
        if (INSTANCE == null) {
            INSTANCE = new RedditNewsDataRemoteDataSource(context,redditAPI);
        }
        return INSTANCE;
    }


    @Override
    public void getMoreNews(@NonNull final LoadNewsCallback callback) {
        Call<RedditNewsAPIResponse> call =  mRedditAPI.getSortedNews("hot",after,"10");
        call.enqueue(new Callback<RedditNewsAPIResponse>() {
            @Override
            public void onResponse(Call<RedditNewsAPIResponse> call, Response<RedditNewsAPIResponse> response) {
                after = response.body().getData().getAfter();
                Log.i(TAG, "Recieved reddit response: "+response.body());
                List<RedditNewsData> redditNewsDataList = new ArrayList<>();
                for(RedditNewsAPIChildrenResponse child: response.body().getData().getChildren()){
                    RedditNewsAPIChildrenResponseData data = child.getData();
                    Log.i(TAG,"child date: "+new Date(data.getCreated()));
                    redditNewsDataList.add(new RedditNewsData(data.getAuthor(),data.getTitle(),data.getNum_comments(),data.getCreated(),data.getThumbnail(),data.getUrl(),data.getId(),data.getPermalink()));
                }
                callback.onNewsLoaded(redditNewsDataList);
            }

            @Override
            public void onFailure(Call<RedditNewsAPIResponse> call, Throwable t) {
                Log.e(TAG, "Error while requesting reddit news: ",t);
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getNews(@NonNull final LoadNewsCallback callback) {


        Call<RedditNewsAPIResponse> call =  mRedditAPI.getSortedNews("hot","","10");
        call.enqueue(new Callback<RedditNewsAPIResponse>() {
            @Override
            public void onResponse(Call<RedditNewsAPIResponse> call, Response<RedditNewsAPIResponse> response) {
                after = response.body().getData().getAfter();
                Log.i(TAG, "Recieved reddit response: "+response.body());
                List<RedditNewsData> redditNewsDataList = new ArrayList<>();
                for(RedditNewsAPIChildrenResponse child: response.body().getData().getChildren()){
                    RedditNewsAPIChildrenResponseData data = child.getData();
                    redditNewsDataList.add(new RedditNewsData(data.getAuthor(),data.getTitle(),data.getNum_comments(),data.getCreated(),data.getThumbnail(),data.getUrl(),data.getId(),data.getPermalink()));
                }
                callback.onNewsLoaded(redditNewsDataList);
            }

            @Override
            public void onFailure(Call<RedditNewsAPIResponse> call, Throwable t) {
                Log.e(TAG, "Error while requesting reddit news: ",t);
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPosts(@NonNull final LoadPostsCallback callback, String title) {
        Call<ResponseBody> call = mRedditAPI.getRedditPosts(title, "new");
        call.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                List<RedditPostsData> redditPosts = new ArrayList<>();
                String parentId = null;
                List<RedditPostElement> elements = parseResponseToPostElements(response.body());
                for(RedditPostElement elem : elements){
                   if(elem instanceof RedditPostElement.DataRedditPostElement){
                       RedditPost data = ((RedditPostElement.DataRedditPostElement) elem).data;
                       parentId = data.id;

                       RedditPostsData postData = new RedditPostsData(data.id,parentId,data.author,data.body, data.created_utc, data.depth,data.body_html);
                       redditPosts.add(postData);

                       callback.onPostsLoaded(redditPosts);
                   }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });

    }

    private List<RedditPostElement> parseResponseToPostElements(ResponseBody response){
        List<RedditPostElement> redditPostElements = null;
        try {
            redditPostElements = Injection.gson.fromJson(response.string(), Injection.type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return redditPostElements;

    }

    @Override
    public void refreshNews() {
        after = "";
    }

    @Override
    public void deleteAllNews() {
        // Not supported by Reddit :)
    }

    @Override
    public void saveRedditNews(@NonNull RedditNewsData data) {
        // In this demo app we do not support posting of news, therefore not implemented.
    }
}
