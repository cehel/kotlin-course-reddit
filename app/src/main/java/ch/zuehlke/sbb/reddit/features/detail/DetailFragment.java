package ch.zuehlke.sbb.reddit.features.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPost;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 13.11.17.
 */

public class DetailFragment extends Fragment implements DetailContract.View {

    private static final String TAG = "DetailFragment";

    private DetailContract.Presenter mPresenter;


    public DetailFragment() {
        // Requires empty public constructor
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
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
    public void showRedditPosts(List<RedditPost> posts) {
        Log.i(TAG,"Got "+posts.size() + " posts");
    }
}
