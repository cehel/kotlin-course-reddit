package ch.zuehlke.sbb.reddit;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import ch.zuehlke.sbb.reddit.data.source.RedditNewsRepository;
import ch.zuehlke.sbb.reddit.data.source.local.RedditNewsLocalDataSource;
import ch.zuehlke.sbb.reddit.data.source.remote.RedditAPI;
import ch.zuehlke.sbb.reddit.data.source.remote.RedditNewsDataRemoteDataSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of production implementations for
 * {@link ch.zuehlke.sbb.reddit.data.source.RedditDataSource} at compile time.
 */
public class Injection {

    private static final String REDDIT_END_POINT = "https://www.reddit.com/r/dota2/";

    private static RedditAPI redditAPI;
    private static Retrofit retrofit;

    public static RedditNewsRepository provideRedditNewsRepository(@NonNull Context context) {
        checkNotNull(context);
        return RedditNewsRepository.getInstance(RedditNewsDataRemoteDataSource.getInstance(context,getRedditAPI(getRetroFit())),
                RedditNewsLocalDataSource.getInstance(context));
    }

    public static RedditAPI getRedditAPI(Retrofit retrofit){
        if(redditAPI ==null){
            redditAPI = retrofit.create(RedditAPI.class);
        }
        return redditAPI;
    }

    public static Retrofit getRetroFit(){
        if(retrofit == null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(REDDIT_END_POINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
