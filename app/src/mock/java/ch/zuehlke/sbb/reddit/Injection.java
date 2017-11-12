package ch.zuehlke.sbb.reddit;

import android.content.Context;
import android.support.annotation.NonNull;

import ch.zuehlke.sbb.reddit.data.FakeRedditNewsRemoteDataSource;
import ch.zuehlke.sbb.reddit.data.source.RedditNewsRepository;
import ch.zuehlke.sbb.reddit.data.source.local.RedditNewsLocalDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 08.11.17.
 */

public class Injection {


    public static RedditNewsRepository provideRedditNewsRepository(@NonNull Context context) {
        checkNotNull(context);
        return RedditNewsRepository.getInstance(FakeRedditNewsRemoteDataSource.getInstance(),
                RedditNewsLocalDataSource.getInstance(context));
    }
}
