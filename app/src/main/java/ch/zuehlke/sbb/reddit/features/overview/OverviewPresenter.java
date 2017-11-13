package ch.zuehlke.sbb.reddit.features.overview;


import android.support.annotation.NonNull;

import java.util.List;

import ch.zuehlke.sbb.reddit.data.source.RedditDataSource;
import ch.zuehlke.sbb.reddit.data.source.RedditRepository;
import ch.zuehlke.sbb.reddit.models.RedditNewsData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 11.11.17.
 */

public class OverviewPresenter implements OverviewContract.Presenter {

    private final OverviewContract.View mOverviewView;
    private boolean mFirstLoad = true;
    private RedditRepository mRedditRepository;

    public OverviewPresenter(@NonNull OverviewContract.View view,@NonNull RedditRepository redditRepository){
        mOverviewView = checkNotNull(view,"OverviewView cannot be null");
        mRedditRepository = checkNotNull(redditRepository,"RedditRepository cannot be null");
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadRedditNews(false);
    }

    @Override
    public void loadRedditNews(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadRedditNews(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;

    }


    @Override
    public void showRedditNews(RedditNewsData redditNewsData) {
        checkNotNull(redditNewsData, "redditNewsData cannot be null!");
        mOverviewView.showRedditNewsDetails(redditNewsData.getId());
    }


    @Override
    public void loadMoreRedditNews() {
        mRedditRepository.getMoreNews(new RedditDataSource.LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<RedditNewsData> data) {
                // The view may not be able to handle UI updates anymore
                if (!mOverviewView.isActive()) {
                    return;
                }
                processTasks(data,true);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mOverviewView.isActive()) {
                    return;
                }
                mOverviewView.showRedditNewsLoadingError();
            }
        });

    }

     private void loadRedditNews(final boolean forceUpdate, final boolean showLoadingUI) {
         if (showLoadingUI) {
             mOverviewView.setLoadingIndicator(true);
         }
         if (forceUpdate) {
             mRedditRepository.refreshNews();
         }

         mRedditRepository.getNews(new RedditDataSource.LoadNewsCallback() {
             @Override
             public void onNewsLoaded(List<RedditNewsData> newsDataList) {
                 // The view may not be able to handle UI updates anymore
                 if (!mOverviewView.isActive()) {
                     return;
                 }
                 if (showLoadingUI) {
                     mOverviewView.setLoadingIndicator(false);
                 }

                 processTasks(newsDataList,false);
             }

             @Override
             public void onDataNotAvailable() {
                 // The view may not be able to handle UI updates anymore
                 if (!mOverviewView.isActive()) {
                     return;
                 }
                 mOverviewView.showRedditNewsLoadingError();
             }
         });
    }

    private void processTasks(List<RedditNewsData> news,boolean added) {
        if (news.isEmpty()) {
            // Show a message indicating there are no news for that filter type.
            processEmptyTasks();
        } else {
            // Show the list of news
            if(added){
                mOverviewView.addRedditNews(news);
            }else{
                mOverviewView.showRedditNews(news);
            }

        }
    }



    private void processEmptyTasks() {
        mOverviewView.showNoNews();
    }
}
