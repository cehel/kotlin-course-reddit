package ch.zuehlke.sbb.reddit.features.overview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.features.detail.RedditNewsDetailActivity;
import ch.zuehlke.sbb.reddit.features.overview.adapter.impl.RedditNewsDelegateAdapter;
import ch.zuehlke.sbb.reddit.features.overview.adapter.impl.RedditOverviewAdapter;
import ch.zuehlke.sbb.reddit.models.RedditNewsData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 11.11.17.
 */

public class OverviewFragment extends Fragment implements OverviewContract.View {

    private OverviewContract.Presenter mOverviewPresenter;
    private RedditOverviewAdapter mOverviewAdapter;
    private View mNoNewsView;
    private RecyclerView mNewsView;


    private RedditNewsDelegateAdapter.OnNewsSelectedListener listener = new RedditNewsDelegateAdapter.OnNewsSelectedListener() {
        @Override
        public void onNewsSelected(String url) {

        }
    };

    public OverviewFragment() {
        // Requires empty public constructor
    }

    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_overview, container, false);
        mNoNewsView = root.findViewById(R.id.noRedditNewsView);
        mNewsView = root.findViewById(R.id.redditNewsView);
        mNewsView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNewsView.setAdapter(mOverviewAdapter);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        final InfiniteScrollListener infiniteScrollListener = new InfiniteScrollListener((LinearLayoutManager) mNewsView.getLayoutManager()) {
            @Override
            public void loadingFunction() {
                mOverviewPresenter.loadMoreRedditNews();
            }
        };
        swipeRefreshLayout.setScrollUpChild(mNewsView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                infiniteScrollListener.reset();
                mOverviewPresenter.loadRedditNews(false);
            }
        });


        mNewsView.setHasFixedSize(true);
        mNewsView.clearOnScrollListeners();
        mNewsView.addOnScrollListener(infiniteScrollListener);

        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOverviewAdapter = new RedditOverviewAdapter(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mOverviewPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull OverviewContract.Presenter presenter) {
        mOverviewPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean isActive() {
        return isAdded();
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

    @Override
    public void showRedditNews(List<RedditNewsData> redditNews) {
        mOverviewAdapter.clearAndAddNews(redditNews);
        mNewsView.setVisibility(View.VISIBLE);
        mNoNewsView.setVisibility(View.GONE);
    }

    @Override
    public void addRedditNews(final List<RedditNewsData> redditNews) {
        mOverviewAdapter.addRedditNews(redditNews);
    }

    @Override
    public void showRedditNewsLoadingError() {
        Snackbar.make(getView(),R.string.overview_screen_error_loading_reddit_news,Snackbar.LENGTH_LONG);
    }


    @Override
    public void showNoNews() {
        mNewsView.setVisibility(View.GONE);
        mNoNewsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRedditNewsDetails(String redditNewsId) {
        Intent intent = new Intent(getContext(), RedditNewsDetailActivity.class);
        intent.putExtra(RedditNewsDetailActivity.EXTRA_REDDIT_NEWS_ID, redditNewsId);
        startActivity(intent);
    }

}
