package ch.zuehlke.reddit.features.news

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ch.zuehlke.reddit.data.source.RedditDataSource
import ch.zuehlke.reddit.data.source.RedditRepository
import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import kotlinx.coroutines.experimental.async
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by celineheldner on 28.02.18.
 */


class NewsViewModel @Inject constructor(
        private val redditRepository: RedditRepository,
        @Named("io-scheduler") private val ioScheduler: Scheduler = Schedulers.io(),
        @Named("main-scheduler") private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
): ViewModel(){

    private val mutableRedditNewsData: MutableLiveData<MutableList<RedditNewsData>> = MutableLiveData<MutableList<RedditNewsData>>().apply { emptyList<RedditNewsData>() }
    val redditNewsData: LiveData<MutableList<RedditNewsData>> = mutableRedditNewsData


    private val mutableMoreRedditNewsData: MutableLiveData<MutableList<RedditNewsData>> = MutableLiveData<MutableList<RedditNewsData>>().apply { emptyList<RedditNewsData>() }
    val moreRedditNewsData: LiveData<MutableList<RedditNewsData>> = mutableMoreRedditNewsData

    private val mutableViewState: MutableLiveData<ViewState> = MutableLiveData()
    val viewState: LiveData<ViewState> = mutableViewState

    private val mutableRedditPostData: MutableLiveData<MutableList<RedditPostsData>> = MutableLiveData<MutableList<RedditPostsData>>().apply { mutableListOf<RedditPostsData>() }
    val redditPostData: LiveData<MutableList<RedditPostsData>> = mutableRedditPostData

    private var currentSubscription: PageSubscriber? = null

    private var currentPostUrl: String? = null

    enum class ViewState { LOADING, NONE, NO_DATA_AVAILABLE, ERROR }

    init {
        loadRedditNews(true, true)
    }

    abstract class PageSubscriber : ResourceSubscriber<List<RedditNewsData>>() {
        override fun onStart() {
            nextPage()
        }

        fun nextPage() {
            request(1) // <<- Generate a single request
        }
    }

    fun loadMoreRedditNews() {
        currentSubscription?.nextPage()
    }

    fun loadRedditNews(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            mutableViewState.postValue(ViewState.LOADING)
        }

        if (forceUpdate || currentSubscription == null) {
            val newSubscription = object : PageSubscriber() {
                var isMore = false
                override fun onComplete() {
                    mutableViewState.setValue(ViewState.NO_DATA_AVAILABLE)
                }

                override fun onNext(t: List<RedditNewsData>?) {
                    mutableViewState.setValue(ViewState.NONE)
                    if (t != null) {
                        if(isMore)
                            mutableMoreRedditNewsData.setValue(t.toMutableList())
                        else {
                            isMore = true
                            mutableRedditNewsData.setValue(t.toMutableList())
                        }
                    } else {
                        mutableViewState.setValue(ViewState.NO_DATA_AVAILABLE)
                    }
                }

                override fun onError(t: Throwable?) {
                    mutableViewState.postValue(ViewState.ERROR)
                }
            }
            currentSubscription?.dispose()
            currentSubscription = redditRepository.news
                    .subscribeOn(ioScheduler)
                    .observeOn(mainScheduler, false, 1)
                    .subscribeWith(newSubscription)
        }
    }


    fun loadRedditPosts() {
        async {
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

    }

    fun setRedditUrl(redditUrl: String) {
        this.currentPostUrl = redditUrl
    }

}