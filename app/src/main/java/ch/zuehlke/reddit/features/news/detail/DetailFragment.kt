package ch.zuehlke.reddit.features.news.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.di.Injectable
import ch.zuehlke.reddit.features.news.NewsViewModel
import ch.zuehlke.reddit.models.RedditPostsData
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject


/**
 * Created by chsc on 13.11.17.
 */


/*
Create a class DetailFragment that extends the Fragment class and implements Injectable
-> Why Injectable? Look at the class ch.zuehlke.reddit.di.AppInjector:

                if(f is Injectable){
                    AndroidSupportInjection.inject(f)
                }

   This is just a helper so we don't have to inject the fragment to the Dagger graph every time we create a new Fragment

   We will display all the RedditPosts of the clicked RedditNews in a RecyclerView. For this we need a RecyclerView.Adapter,
   which we also create in this exercise. -> Implement it now in the DetailAdapter file. and then inject it with dagger
   -> Hint we already used the RecyclerView in the OverviewFragment.  (https://developer.android.com/guide/topics/ui/layout/recyclerview.html)

   The NewsViewModel takes care of fetching the data from the reppository and exposing it for the View. This is already implemented.
   We just need to inject the ViewModelFactory, with which we can retrieve the ViewModel
   -> Hint we have done this similarly in the overviewFragment

   We inflate the layout in the onCreateView function. The layout file R.layout.fragment_detail already exists.

   We will setup the view in the onViewCreated function
   The layout file already exists: R.layout.fragment_detail

   We will do all other view-related setup in the onViewCreated function:
        With kotlin-extensions you can easily fetch the view elements, defined in the layout-xml, with their ID.
        Setup the RecyclerView.
        Setup the ScrollChildSwipeRefreshLayout. When scrolled up it should reload the redditPosts
        Call the newsViewModel to load the RedditPosts

   In the function onActivity Created we will observe the redditPostData, the LifeData in the NewsViewModel.
   When we call loadRedditPosts this lifeData will be fetched. When there was an update we will add the Posts
   to our RecyclerViewAdapter
   Also add an observer to the NewsViewModel.ViewState and react accordingly




 */
class DetailFragment: Fragment(), Injectable {

    @Inject
    lateinit var mAdapter: DetailAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        redditPostView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            setHasFixedSize(true)
        }

        val newsViewModel = ViewModelProviders.of(activity, viewModelFactory).get(NewsViewModel::class.java)

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val newsViewModel = ViewModelProviders.of(activity, viewModelFactory).get(NewsViewModel::class.java)

        newsViewModel.redditPostData.observe(this, Observer { posts: List<RedditPostsData>? ->
            posts?.let {
                mAdapter.clearAndAddPosts(it)
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
            else -> {
                refreshLayout.isRefreshing = false
                Snackbar.make(view!!, R.string.overview_screen_error_loading_reddit_posts, Snackbar.LENGTH_LONG)
            }
        }
    }




}
