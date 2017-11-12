package ch.zuehlke.sbb.reddit.features.overview;

import java.util.List;

import ch.zuehlke.sbb.reddit.BasePresenter;
import ch.zuehlke.sbb.reddit.BaseView;
import ch.zuehlke.sbb.reddit.models.RedditNewsData;

/**
 * Created by chsc on 11.11.17.
 */

public interface OverviewContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isActive);

        void showRedditNews(List<RedditNewsData> redditNews);

        void addRedditNews(List<RedditNewsData> redditNews);

        void showRedditNewsLoadingError();

        void showNoNews();

        void showRedditNewsDetails(String redditNewsId);

    }

    interface Presenter extends BasePresenter {

        void loadRedditNews(boolean forceUpdate);

        void loadMoreRedditNews();

        void showRedditNews(RedditNewsData redditNewsData);


    }
}
