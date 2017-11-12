package ch.zuehlke.sbb.reddit;

import android.support.annotation.NonNull;

/**
 * Created by chsc on 08.11.17.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(@NonNull T presenter);


    boolean isActive();
}
