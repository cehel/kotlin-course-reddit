package ch.zuehlke.sbb.reddit.data.source.remote.model.posts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import ch.zuehlke.sbb.reddit.AbstractTestCase;

import static ch.zuehlke.sbb.reddit.data.source.remote.RedditElementTypeAdapterFactory.getElementTypeAdapterFactory;

/**
 * Created by chsc on 13.11.17.
 */
public class RedditPostRedditElementTypeAdapterFactoryTest extends AbstractTestCase{


    private static final Type type = new TypeToken<List<RedditPostElement>>() {
    }.getType();


    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(getElementTypeAdapterFactory())
            .create();

    @Test
    public void parseComments() throws IOException {
        String jsonFile = readJsonFile("reddit_comments.json");
        Assert.assertNotNull(jsonFile);

        final List<RedditPostElement> redditPostElements = gson.fromJson(jsonFile, type);
        Assert.assertEquals(2,redditPostElements.size());
        Assert.assertTrue(redditPostElements.get(1) instanceof RedditPostElement.DataRedditPostElement);
        RedditPostElement.DataRedditPostElement element = (RedditPostElement.DataRedditPostElement) redditPostElements.get(1);
        Assert.assertEquals(33,element.data.children.size());
        dump(redditPostElements);

    }


}