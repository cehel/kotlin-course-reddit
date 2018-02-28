package ch.zuehlke.sbb.reddit.features.news

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ch.zuehlke.sbb.reddit.data.source.RedditDataSource
import ch.zuehlke.sbb.reddit.data.source.RedditRepository
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData

/**
 * Created by celineheldner on 28.02.18.
 */


class NewsViewModel(private val redditRepository: RedditRepository): ViewModel(){

    private val mutableRedditNewsData: MutableLiveData<MutableList<RedditNewsData>> = MutableLiveData<MutableList<RedditNewsData>>().apply { emptyList<RedditNewsData>() }
    val redditNewsData: LiveData<MutableList<RedditNewsData>> = mutableRedditNewsData


    private val mutableMoreRedditNewsData: MutableLiveData<MutableList<RedditNewsData>> = MutableLiveData<MutableList<RedditNewsData>>().apply { emptyList<RedditNewsData>() }
    val moreRedditNewsData: LiveData<MutableList<RedditNewsData>> = mutableMoreRedditNewsData

    private val mutableViewState: MutableLiveData<ViewState> = MutableLiveData()
    val viewState: LiveData<ViewState> = mutableViewState

    private val mutableRedditPostData: MutableLiveData<MutableList<RedditPostsData>> = MutableLiveData<MutableList<RedditPostsData>>().apply { mutableListOf<RedditPostsData>() }
    val redditPostData : LiveData<MutableList<RedditPostsData>> = mutableRedditPostData

    private var currentPostUrl: String? = null

    enum class ViewState{LOADING, NONE, NO_DATA_AVAILABLE, ERROR }

    init {
        loadRedditNews(true,true)
    }


     fun loadMoreRedditNews() {
        redditRepository.getMoreNews(object : RedditDataSource.LoadNewsCallback {
            override fun onNewsLoaded(news: List<RedditNewsData>) {
                mutableMoreRedditNewsData.postValue(news.toMutableList())
            }

            override fun onDataNotAvailable() {
                mutableViewState.postValue(ViewState.NO_DATA_AVAILABLE)
            }
        })

    }


     fun loadRedditNews(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            mutableViewState.postValue(ViewState.LOADING)
        }
        if (forceUpdate) {
            redditRepository.refreshNews()
        }

        redditRepository.getNews(object : RedditDataSource.LoadNewsCallback {
            override fun onNewsLoaded(news: List<RedditNewsData>) {
                if (showLoadingUI) {
                    mutableViewState.postValue(ViewState.NONE)
                }
                if (news.isEmpty()){
                    mutableViewState.postValue(ViewState.NO_DATA_AVAILABLE)
                } else {
                    mutableRedditNewsData.postValue(news.toMutableList())
                }
            }

            override fun onDataNotAvailable() {
                mutableViewState.postValue(ViewState.ERROR)
            }
        })
    }


    fun loadRedditPosts() {
        mutableViewState.postValue(ViewState.LOADING)

        redditRepository.getPosts(object : RedditDataSource.LoadPostsCallback {
            override fun onPostsLoaded(posts: List<RedditPostsData>) {
                mutableViewState.postValue(ViewState.NONE)
                mutableRedditPostData.postValue(posts.toMutableList())
            }

            override fun onDataNotAvailable() {
               mutableViewState.postValue(ViewState.NO_DATA_AVAILABLE)
            }
        }, currentPostUrl!!)
    }

    fun setRedditUrl(redditUrl: String){
        this.currentPostUrl = redditUrl
    }



}