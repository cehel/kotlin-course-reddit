package ch.zuehlke.sbb.reddit.features.overview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ch.zuehlke.sbb.reddit.Injection;
import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.util.ActivityUtils;

/**
 * Created by chsc on 11.11.17.
 */

public class OverviewActivity extends AppCompatActivity {

    private OverviewPresenter mOverviewPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);


        OverviewFragment overviewFragment =
                (OverviewFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (overviewFragment == null) {
            // Create the fragment
            overviewFragment = OverviewFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), overviewFragment, R.id.contentFrame);
        }

        // Create the presenter
        mOverviewPresenter = new OverviewPresenter(overviewFragment, Injection.provideRedditNewsRepository(this));
    }
}
