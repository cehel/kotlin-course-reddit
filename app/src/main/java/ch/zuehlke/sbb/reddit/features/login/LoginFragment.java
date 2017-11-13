package ch.zuehlke.sbb.reddit.features.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.features.overview.OverviewActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 08.11.17.
 */

public class LoginFragment extends Fragment implements LoginContract.View {

    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private LoginContract.Presenter mPresenter;
    private ProgressBar mProgessBar;
    private AppCompatButton mLoginButton;
    private TextInputEditText mUsername;
    private TextInputEditText mPassword;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        mProgessBar = root.findViewById(R.id.progressBar);
        mLoginButton = root.findViewById(R.id.loginButton);
        mUsername = root.findViewById(R.id.username);
        mPassword = root.findViewById(R.id.password);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.login(mUsername.getText().toString(),mPassword.getText().toString());
            }
        });


        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0 && verifyIsEmail(editable.toString())){
                    mUsername.setError(null);
                }else{
                     mUsername.setError(getString(R.string.login_screen_invalid_email));
                }
            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(verifyPasswordLength(editable.toString())){
                    mPassword.setError(null);
                }else{
                    mPassword.setError(getString(R.string.login_screen_invalid_password_length));
                }
            }
        });



        return root;

    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setLoadingIndicator(boolean isActive) {
            mProgessBar.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showInvalidUsername() {
        mUsername.setError(getString(R.string.login_screen_invalid_username));
    }

    @Override
    public void showInvalidPassword() {
        mPassword.setError(getString(R.string.login_screen_invalid_password));
    }


    @Override
    public void showRedditNews() {
        Intent intent = new Intent(getContext(),OverviewActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    private boolean verifyPasswordLength(String password){
        return (!Strings.isNullOrEmpty(password) && password.length() >=6);
    }

    private boolean verifyIsEmail(String email){
        Matcher matcher = android.util.Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();
    }
}
