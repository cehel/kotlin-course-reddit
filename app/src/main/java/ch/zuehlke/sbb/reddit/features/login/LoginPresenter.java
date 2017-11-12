package ch.zuehlke.sbb.reddit.features.login;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 08.11.17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull  LoginContract.View view){
        this.mLoginView = checkNotNull(view,"LoginView cannot be null");
        view.setPresenter(this);
    }

    @Override
    public void start() {
        // Do nothing here, as we don't load any data
    }

    @Override
    public void login(final String userEmail, final String password) {
        mLoginView.setLoadingIndicator(true);
        // Simulate a 'long' network call to verify the credentials
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        if(mLoginView.isActive()){

                            boolean hasError = false;
                            if(!userEmail.equals("a")){
                                mLoginView.showInvalidUsername();
                                hasError = true;
                            }

                            if(!password.equals("a")){
                                mLoginView.showInvalidPassword();
                                hasError = true;
                            }

                            if(!hasError){
                                mLoginView.showRedditNews();
                            }
                            mLoginView.setLoadingIndicator(false);
                        }
                        return null;
                    }
                }.doInBackground();
            }
        },1000);

    }
}
