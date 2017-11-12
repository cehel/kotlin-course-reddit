package ch.zuehlke.sbb.reddit.features.overview.adapter.impl;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.zuehlke.sbb.reddit.features.overview.adapter.AdapterConstants;
import ch.zuehlke.sbb.reddit.features.overview.adapter.ViewType;
import ch.zuehlke.sbb.reddit.features.overview.adapter.ViewTypeDelegateAdapter;
import ch.zuehlke.sbb.reddit.models.RedditNewsData;

/**
 * Created by chsc on 12.11.17.
 */

public class RedditOverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ViewType loadingItem = new ViewType() {
        @Override
        public int getViewType() {
            return AdapterConstants.LOADING;
        }
    };

    private final List<ViewType> mItems = new ArrayList<>();
    private final Map<Integer,ViewTypeDelegateAdapter> adapters = new HashMap<>();

    public RedditOverviewAdapter(RedditNewsDelegateAdapter.OnNewsSelectedListener listener){
        adapters.put(AdapterConstants.LOADING,new LoadingDelegateAdapter());
        adapters.put(AdapterConstants.NEWS,new RedditNewsDelegateAdapter(listener));
        mItems.add(loadingItem);
    }

    public void addRedditNews(final List<RedditNewsData> newsData){
        int initPosition = mItems.size() -1;
        mItems.remove(initPosition);
        notifyItemRemoved(initPosition);

        mItems.addAll(newsData);
        mItems.add(loadingItem);
        notifyItemRangeChanged(initPosition,mItems.size() + 1 /* plus loading item */);
    }

    public void clearAndAddNews(List<RedditNewsData> newsData){
        int previousItemSize = mItems.size();
        mItems.clear();
        notifyItemRangeRemoved(0,previousItemSize);
        mItems.addAll(newsData);
        mItems.add(loadingItem);
        notifyItemRangeChanged(0,newsData.size() + 1 /* plus loading item */);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return adapters.get(viewType).onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapters.get(getItemViewType(position)).onBindViewHolder(holder, mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
       return mItems.get(position).getViewType();
    }
}
