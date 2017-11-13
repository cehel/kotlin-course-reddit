package ch.zuehlke.sbb.reddit.data.source;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ch.zuehlke.sbb.reddit.models.RedditNewsData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 08.11.17.
 */

public class RedditRepository implements RedditDataSource {

    private static RedditRepository INSTANCE = null;

    private final RedditDataSource mRedditNewsRemoteDataSource;

    private final RedditDataSource mRedditNewsLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, RedditNewsData> mCacheNews;

    /**
     * Marks the cache as invalid, to force an update the next time redditPost is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;


    // Prevent direct instantiation.
    private RedditRepository(@NonNull RedditDataSource newsRemoteDataSource,
                             @NonNull RedditDataSource newsLocalDataSource) {
        mRedditNewsRemoteDataSource = checkNotNull(newsRemoteDataSource);
        mRedditNewsLocalDataSource = checkNotNull(newsLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param newsLocalDataSource the backend redditPost source
     * @param newsLocalDataSource  the device storage redditPost source
     * @return the {@link RedditRepository} instance
     */
    public static RedditRepository getInstance(RedditDataSource newsRemoteDataSource,
                                               RedditDataSource newsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new RedditRepository(newsRemoteDataSource, newsLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(RedditDataSource, RedditDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }



    @Override
    public void getMoreNews(LoadNewsCallback callback) {
        checkNotNull(callback);
        addNewsFromRemoteDataSource(callback);
    }

    private void addNewsFromRemoteDataSource(@NonNull final LoadNewsCallback callback) {
        mRedditNewsRemoteDataSource.getMoreNews(new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<RedditNewsData> news) {
                refreshCache(news);
                updateLocalDataSource(news);
                callback.onNewsLoaded(new ArrayList<>(mCacheNews.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getNews(@NonNull final LoadNewsCallback callback) {
        checkNotNull(callback);
        mRedditNewsRemoteDataSource.refreshNews();
        // Respond immediately with cache if available and not dirty
        if (mCacheNews != null && !mCacheIsDirty) {
            callback.onNewsLoaded(new ArrayList<>(mCacheNews.values()));
            return;
        }

        //TOOD add online check, if not available go local.

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new redditPost from the network.
            getNewsFromRemoteDataSource(new LoadNewsCallback() {
                @Override
                public void onNewsLoaded(List<RedditNewsData> data) {
                    for(RedditNewsData newsData : data){
                        saveRedditNews(newsData);
                    }
                    refreshCache(data);
                    callback.onNewsLoaded(new ArrayList<RedditNewsData>(mCacheNews.values()));
                }

                @Override
                public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                }
            });
        } else {
            // Query the local storage if available. If not, query the network.
            mRedditNewsLocalDataSource.getNews(new LoadNewsCallback() {
                @Override
                public void onNewsLoaded(List<RedditNewsData> tasks) {
                    refreshCache(tasks);
                    callback.onNewsLoaded(new ArrayList<>(mCacheNews.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getNewsFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void refreshNews() {
        mCacheIsDirty = true;
        mRedditNewsRemoteDataSource.refreshNews();
    }

    @Override
    public void deleteAllNews() {
        mRedditNewsRemoteDataSource.deleteAllNews(); // Although we call deleteAllNews() on the remote datasource, it is not implemented.
        mRedditNewsLocalDataSource.deleteAllNews();

        if (mCacheNews == null) {
            mCacheNews = new LinkedHashMap<>();
        }
        mCacheNews.clear();
    }

    @Override
    public void saveRedditNews(@NonNull RedditNewsData data) {
        checkNotNull(data);

        mRedditNewsLocalDataSource.saveRedditNews(data);
        mRedditNewsRemoteDataSource.saveRedditNews(data); // Although we call saveRedditNews() on the remote datasource, it is not implemented.
        // Do in memory cache update to keep the app UI up to date
        if (mCacheNews == null) {
            mCacheNews = new LinkedHashMap<>();
        }
        mCacheNews.put(data.getId(), data);
    }

    private void getNewsFromRemoteDataSource(@NonNull final LoadNewsCallback callback) {
        mRedditNewsRemoteDataSource.getNews(new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<RedditNewsData> news) {
                refreshCache(news);
                refreshLocalDataSource(news);
                callback.onNewsLoaded(new ArrayList<>(mCacheNews.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<RedditNewsData> news) {
        if (mCacheNews == null) {
            mCacheNews = new LinkedHashMap<>();
        }
        mCacheNews.clear();
        for (RedditNewsData data : news) {
            mCacheNews.put(data.getId(), data);
        }
        mCacheIsDirty = false;
    }

    private void updateLocalDataSource(List<RedditNewsData> news){
        for (RedditNewsData data : news) {
            mRedditNewsLocalDataSource.saveRedditNews(data);
        }
    }

    private void refreshLocalDataSource(List<RedditNewsData> news) {
        mRedditNewsLocalDataSource.deleteAllNews();
        for (RedditNewsData data : news) {
             mRedditNewsLocalDataSource.saveRedditNews(data);
        }
    }
}
