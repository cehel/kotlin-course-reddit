package ch.zuehlke.sbb.reddit;

import android.app.Application;

/**
 * Created by chsc on 12.11.17.
 */

public class RedditApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injection.init();
    }
}
