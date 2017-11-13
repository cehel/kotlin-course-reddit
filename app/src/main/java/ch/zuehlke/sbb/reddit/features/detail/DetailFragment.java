package ch.zuehlke.sbb.reddit.features.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPost;
import ch.zuehlke.sbb.reddit.features.overview.InfiniteScrollListener;
import ch.zuehlke.sbb.reddit.features.overview.ScrollChildSwipeRefreshLayout;
import ch.zuehlke.sbb.reddit.models.RedditPostsData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 13.11.17.
 */

public class DetailFragment extends Fragment implements DetailContract.View {

    private static final String TAG = "DetailFragment";

    private DetailContract.Presenter mPresenter;
    private DetailAdapter mAdapter;
    private RecyclerView mPostView;
    private View mNoPostView;


    public DetailFragment() {
        // Requires empty public constructor
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DetailAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        mNoPostView = root.findViewById(R.id.noRedditPostVIew);
        mPostView = root.findViewById(R.id.redditPostView);
        mPostView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPostView.setAdapter(mAdapter);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );


        swipeRefreshLayout.setScrollUpChild(mPostView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               mPresenter.loadRedditPosts();
            }
        });


        mPostView.setHasFixedSize(true);
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull DetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showRedditPosts(List<RedditPostsData> posts) {
        Log.i(TAG,"Got "+posts.size() + " posts");
        mAdapter.clearAndAddPosts(posts);
    }

    @Override
    public void showRedditNewsLoadingError() {
        Snackbar.make(getView(),R.string.overview_screen_error_loading_reddit_posts,Snackbar.LENGTH_LONG);
    }

    @Override
    public void setLoadingIndicator(final boolean isActive) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = getView().findViewById(R.id.refreshLayout);
        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(isActive);
            }
        });
    }
}
