package ch.zuehlke.sbb.reddit.data.source.remote;

import ch.zuehlke.sbb.reddit.data.source.remote.model.RedditNewsAPIResponse;
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

}
