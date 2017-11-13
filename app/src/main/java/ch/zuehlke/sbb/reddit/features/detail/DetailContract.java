package ch.zuehlke.sbb.reddit.features.detail;

import java.util.List;

import ch.zuehlke.sbb.reddit.BasePresenter;
import ch.zuehlke.sbb.reddit.BaseView;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPost;

/**
 * Created by chsc on 13.11.17.
 */

public interface DetailContract {

    interface View extends BaseView<Presenter>{
        void showRedditPosts(List<RedditPost> posts);
    }

    interface Presenter extends BasePresenter{
        void loadRedditPosts(String url);
    }
}
