package ch.zuehlke.sbb.reddit.data.source.remote;

import java.util.List;

import ch.zuehlke.sbb.reddit.data.source.remote.model.news.RedditNewsAPIResponse;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by chsc on 12.11.17.
 */

public interface RedditAPI {

    @GET("{sortOrder}.json")
    Call<RedditNewsAPIResponse> getSortedNews(@Path("sortOrder") String sortOrder, @Query("after") String after,
                                              @Query("limit")String limit);


    @GET("/comments/{title}.json")
    Call<List<RedditPostElement>> getRedditPosts(@Path("title") String title, @Query("sort") String sortOrder);

}
