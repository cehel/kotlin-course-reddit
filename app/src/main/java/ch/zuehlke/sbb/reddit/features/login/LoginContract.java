package ch.zuehlke.sbb.reddit.features.login;

import ch.zuehlke.sbb.reddit.BasePresenter;
import ch.zuehlke.sbb.reddit.BaseView;

/**
 * Created by chsc on 08.11.17.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean isActive);

        void showInvalidUsername();

        void showInvalidPassword();

        void showRedditNews();
    }

    interface Presenter extends BasePresenter{

        void login(String userEmail,String password);
    }
}
