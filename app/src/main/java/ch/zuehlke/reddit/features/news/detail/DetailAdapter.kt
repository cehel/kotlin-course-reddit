package ch.zuehlke.reddit.features.news.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.common.friendlyTime
import ch.zuehlke.reddit.models.RedditPostsData

/**
 * Created by chsc on 13.11.17.
 */
/*
    Create the Class DetailAdapter, which extends the RecyclerView.Adapter<DetailAdapter.DetailViewHolder>
    The DetailAdapter.DetailViewHolder is an inner class which we implement.
    It holds 3TextViews:
        userNameTV
        createdTV
        postTV
    Initialize those textviews in the init function by finding them in the layout file: item_detail.xml (username, created, text)

    The DetailAdapter needs a field with a list of RedditPostData

    Inflate the layout in the onCreateViewHolder (item_detail.xml)

    In the onBindViewHolder we can retrieve the RedditPostsData from the list with the given position.
    Here we connect the RedditPostsData to our viewholder. -Set the texts of the three TextViews

    Add a public function wich allows to replace the content of the List of RedditPostsData.
    It takes as an argument a list of RedditPostsData und notifies the adapter accordingly:
    -> Hint use notifyDataSetChanged at the end

    The function getItemCount returns the amount of elements in our RedditPostsData list

    -> Let this class be instantiated by dagger in a new Module: 'DetailFragmentModule'. Make it FragmentScoped

 */

