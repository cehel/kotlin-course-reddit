package ch.zuehlke.reddit.features.news.detail

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
import ch.zuehlke.reddit.features.news.NewsViewModel
import ch.zuehlke.reddit.features.news.NewsViewModelFactory
import ch.zuehlke.reddit.models.RedditPostsData
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * Created by chsc on 13.11.17.
 */

class DetailFragment: Fragment() {


    private var mAdapter: DetailAdapter? = null

    companion object {

        private val TAG = "DetailFragment"

        fun newInstance(): DetailFragment {
            val detailFragment = DetailFragment()
            return detailFragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        redditPostView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            setHasFixedSize(true)
        }

        val newsFactory: NewsViewModelFactory = NewsViewModelFactory(redditRepository = Injection.provideRedditNewsRepository(activity))
        val newsViewModel = ViewModelProviders.of(activity, newsFactory).get(NewsViewModel::class.java)

        // Set up progress indicator
        refreshLayout.apply {
            setColorSchemeColors(
                    ContextCompat.getColor(activity, R.color.colorPrimary),
                    ContextCompat.getColor(activity, R.color.colorAccent),
                    ContextCompat.getColor(activity, R.color.colorPrimaryDark)
            )
            setScrollUpChild(redditPostView)
            setOnRefreshListener { newsViewModel.loadRedditPosts() }
        }

        newsViewModel.loadRedditPosts()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = DetailAdapter(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val newsFactory: NewsViewModelFactory = NewsViewModelFactory(redditRepository = Injection.provideRedditNewsRepository(activity))
        val newsViewModel = ViewModelProviders.of(activity, newsFactory).get(NewsViewModel::class.java)

        newsViewModel.redditPostData.observe(this, Observer { posts: List<RedditPostsData>? ->
            posts?.let {
                mAdapter?.clearAndAddPosts(it)
            }
         })

        newsViewModel.viewState.observe(this, Observer {
            viewstate: NewsViewModel.ViewState? -> handleViewState(viewstate)
        })

    }

    fun handleViewState(viewState: NewsViewModel.ViewState?){
        when(viewState){
            NewsViewModel.ViewState.LOADING -> refreshLayout.isRefreshing = true
            NewsViewModel.ViewState.NONE -> refreshLayout.isRefreshing = false
            NewsViewModel.ViewState.NO_DATA_AVAILABLE -> {
                refreshLayout.isRefreshing = false
                Snackbar.make(view!!, R.string.overview_screen_error_loading_reddit_posts, Snackbar.LENGTH_LONG)
            }
        }
    }




}
