package ch.zuehlke.sbb.reddit.data.source.remote.model.posts

import ch.zuehlke.sbb.reddit.AbstractTestCase
import ch.zuehlke.sbb.reddit.data.source.remote.RedditElementTypeAdapterFactory.Companion.elementTypeAdapterFactory
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import junit.framework.Assert
import org.junit.Test
import java.io.IOException


/**
 * Created by chsc on 13.11.17.
 */
class RedditPostRedditElementTypeAdapterFactoryTest : AbstractTestCase() {

    @Test
    @Throws(IOException::class)
    fun parseComments() {
        val jsonFile = readJsonFile("reddit_comments.json")
        Assert.assertNotNull(jsonFile)

        val redditPostElements = gson.fromJson<List<RedditPostElement>>(jsonFile, type)
        Assert.assertEquals(2, redditPostElements.size)
        Assert.assertTrue(redditPostElements[1] is RedditPostElement.DataRedditPostElement)
        val element = redditPostElements[1] as RedditPostElement.DataRedditPostElement
        Assert.assertEquals(33, element.data!!.children!!.size)
        dump(redditPostElements)

    }

    companion object {


        private val type = object : TypeToken<List<RedditPostElement>>() {

        }.type


        private val gson = GsonBuilder()
                .registerTypeAdapterFactory(elementTypeAdapterFactory)
                .create()
    }


}