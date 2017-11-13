package ch.zuehlke.sbb.reddit;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

import ch.zuehlke.sbb.reddit.data.source.RedditRepository;
import ch.zuehlke.sbb.reddit.data.source.local.RedditNewsLocalDataSource;
import ch.zuehlke.sbb.reddit.data.source.remote.RedditAPI;
import ch.zuehlke.sbb.reddit.data.source.remote.RedditNewsDataRemoteDataSource;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ch.zuehlke.sbb.reddit.data.source.remote.RedditElementTypeAdapterFactory.getElementTypeAdapterFactory;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of production implementations for
 * {@link ch.zuehlke.sbb.reddit.data.source.RedditDataSource} at compile time.
 */
public class Injection {

    private static final String REDDIT_END_POINT = "https://www.reddit.com/r/dota2/";

    private static RedditAPI redditAPI;
    private static Retrofit retrofit;

    public static final Type type = new TypeToken<List<RedditPostElement>>() {
    }.getType();

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(getElementTypeAdapterFactory())
            .create();

    public static RedditRepository provideRedditNewsRepository(@NonNull Context context) {
         checkNotNull(context);
        return RedditRepository.getInstance(RedditNewsDataRemoteDataSource.getInstance(context,getRedditAPI(getRetroFit())),
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
                    .registerTypeAdapterFactory(getElementTypeAdapterFactory())
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(REDDIT_END_POINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
