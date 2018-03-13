package ch.zuehlke.reddit.features.news.overview

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.zuehlke.reddit.Injection
import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.features.news.NavigationController
import ch.zuehlke.reddit.features.news.NewsActivity
import ch.zuehlke.reddit.features.news.NewsViewModel
import ch.zuehlke.reddit.features.news.NewsViewModelFactory
import ch.zuehlke.reddit.features.news.overview.adapter.impl.RedditNewsDelegateAdapter.OnNewsSelectedListener
import ch.zuehlke.reddit.features.news.overview.adapter.impl.RedditOverviewAdapter
import ch.zuehlke.reddit.models.RedditNewsData
import kotlinx.android.synthetic.main.fragment_overview.*

/**
 * Created by chsc on 11.11.17.
 */

class OverviewFragment : Fragment() {

    //Injections

    private var mOverviewAdapter: RedditOverviewAdapter? = null
    private var mNavigationController: NavigationController<NewsActivity>? = null


    private val listener = object: OnNewsSelectedListener {
        override fun onNewsSelected(url: String) {


        }
    }



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater!!.inflate(R.layout.fragment_overview, container, false)
        container?.let {
            mNavigationController = NavigationController(this.activity as NewsActivity ,it.id)
        }

        return root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val newsFactory: NewsViewModelFactory = NewsViewModelFactory(redditRepository = Injection.provideRedditNewsRepository(activity))
        val newsViewModel = ViewModelProviders.of(activity, newsFactory).get(NewsViewModel::class.java)

        newsViewModel.viewState.observe(this, Observer { viewState: NewsViewModel.ViewState? -> handleViewState(viewState)  })
        newsViewModel.redditNewsData.observe( this, Observer { newsData: List<RedditNewsData>? -> newsData?.let {  mOverviewAdapter?.clearAndAddNews(it) }})

        newsViewModel.moreRedditNewsData.observe(this, Observer {
            redditNews: List<RedditNewsData>? -> redditNews?.let {  mOverviewAdapter?.addRedditNews(it)}
        })

        mOverviewAdapter = RedditOverviewAdapter(listener)

        redditNewsView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = mOverviewAdapter
            setHasFixedSize(true)
            clearOnScrollListeners()
        }

        val infiniteScrollListener = object : InfiniteScrollListener(redditNewsView.layoutManager as LinearLayoutManager) {
            override fun loadingFunction() {
                newsViewModel.loadMoreRedditNews()
            }
        }

        // Set up progress indicator
        refreshLayout.apply {
            setColorSchemeColors(
                    ContextCompat.getColor(activity, R.color.colorPrimary),
                    ContextCompat.getColor(activity, R.color.colorAccent),
                    ContextCompat.getColor(activity, R.color.colorPrimaryDark))
            setScrollUpChild(redditNewsView)
            setOnRefreshListener {
                infiniteScrollListener.reset()
                newsViewModel.loadRedditNews(true, true)
            }
        }

        redditNewsView.addOnScrollListener(infiniteScrollListener)

    }

    private fun handleViewState(viewState: NewsViewModel.ViewState?){
        when(viewState){
            NewsViewModel.ViewState.LOADING -> refreshLayout.isRefreshing = true
            NewsViewModel.ViewState.NONE -> {
                refreshLayout.isRefreshing = false
                redditNewsView.visibility = View.VISIBLE
                noRedditNewsView.visibility = View.GONE
            }
            NewsViewModel.ViewState.ERROR -> {
                refreshLayout.isRefreshing = false
                Snackbar.make(view!!, R.string.overview_screen_error_loading_reddit_news, Snackbar.LENGTH_LONG)
            }
            NewsViewModel.ViewState.NO_DATA_AVAILABLE -> {
                refreshLayout.isRefreshing = false
                redditNewsView.visibility = View.GONE
                noRedditNewsView.visibility = View.VISIBLE
            }
        }
    }


    companion object {

        fun newInstance(): OverviewFragment {
            return OverviewFragment()
        }
    }

}
