package ch.zuehlke.sbb.reddit.features.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ch.zuehlke.sbb.reddit.Injection;
import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.util.ActivityUtils;

/**
 * Created by chsc on 11.11.17.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_REDDIT_NEWS_URL = "redditNewsUrl";
    private static final String TAG = "DetailActivity";

    private DetailPresenter mOverviewPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);

        String redditUrl = getIntent().getStringExtra(EXTRA_REDDIT_NEWS_URL);
        Log.i(TAG,"RedditUrl: "+redditUrl);
        DetailFragment detailFragment =
                (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (detailFragment == null) {
            // Create the fragment
            detailFragment = DetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), detailFragment, R.id.contentFrame);
        }

        // Create the presenter
        mOverviewPresenter = new DetailPresenter(detailFragment, Injection.provideRedditNewsRepository(this),redditUrl,Injection.getRedditAPI(Injection.getRetroFit()));
    }
}
