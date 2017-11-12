package ch.zuehlke.sbb.reddit.features.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.util.ActivityUtils;

/**
 * Created by chsc on 08.11.17.
 */

public class LoginActivity extends AppCompatActivity {

    private LoginPresenter mLoginPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);


        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }

        // Create the presenter
        mLoginPresenter = new LoginPresenter(loginFragment);
    }
}
