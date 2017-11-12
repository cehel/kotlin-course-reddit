package ch.zuehlke.sbb.reddit.features.overview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;

/**
 * Created by chsc on 12.11.17.
 */

public abstract class InfiniteScrollListener extends OnScrollListener {

    private static String TAG = "InfiniteScrollListener";

    private int  previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 2;
    private int firstVisibleItem = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;

    private LinearLayoutManager mLayoutManager;

    public InfiniteScrollListener(LinearLayoutManager manager){
        mLayoutManager = manager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(dy > 0){
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLayoutManager.getItemCount();
            firstVisibleItem = mLayoutManager.findFirstCompletelyVisibleItemPosition();
            if(loading){
                if(totalItemCount > previousTotal){
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if(!loading && (totalItemCount - visibleItemCount)>=(firstVisibleItem+visibleThreshold)){
                Log.i(TAG, "End reached");
            }
            if(!loading){
                loadingFunction();
                loading = true;
            }

        }
    }

    public abstract void loadingFunction();
}
