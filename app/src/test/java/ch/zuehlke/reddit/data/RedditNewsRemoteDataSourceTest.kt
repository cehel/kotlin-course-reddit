package ch.zuehlke.reddit.data

import ch.zuehlke.reddit.data.source.RedditDataSource
import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData

/**
 * Created by celineheldner on 09.03.18.
 */
class RedditNewsRemoteDataSourceTest: RedditDataSource{

    override fun getNews(callback: RedditDataSource.LoadNewsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, permalink: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun savePosts(data: RedditPostsData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePostsWithPermaLink(permaLink: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshNews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllNews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveRedditNews(data: RedditNewsData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMoreNews(callback: RedditDataSource.LoadNewsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}