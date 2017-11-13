package ch.zuehlke.sbb.reddit.data;

import android.support.annotation.NonNull;

import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

import ch.zuehlke.sbb.reddit.data.source.RedditDataSource;
import ch.zuehlke.sbb.reddit.models.RedditNewsData;

/**
 * Created by chsc on 08.11.17.
 */

public class FakeRedditNewsRemoteDataSource implements RedditDataSource {

    private static FakeRedditNewsRemoteDataSource INSTANCE;

    private static final Map<String, RedditNewsData> REDDIT_NEWS_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private FakeRedditNewsRemoteDataSource() {}

    public static FakeRedditNewsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeRedditNewsRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getNews(@NonNull LoadNewsCallback callback) {
        callback.onNewsLoaded(Lists.newArrayList(REDDIT_NEWS_SERVICE_DATA.values()));
    }

    @Override
    public void refreshNews() {
        // Not required because the {@link ch.zuehlke.sbb.reddit.data.source.RedditRepository} handles the logic of refreshing the
        // news from all the available data sources.
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
