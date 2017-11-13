package ch.zuehlke.sbb.reddit.features.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPost;
import ch.zuehlke.sbb.reddit.models.RedditPostsData;
import ch.zuehlke.sbb.reddit.util.DateUtils;

/**
 * Created by chsc on 13.11.17.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder>{


    private List<RedditPostsData> mItems = new ArrayList<>();
    private Context mContext;
    public DetailAdapter(Context context){
        mContext = context;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        RedditPostsData post = mItems.get(position);

        holder.mUsername.setText(post.author);
        holder.mText.setText(post.body);
        holder.mCreated.setText(DateUtils.friendlyTime(post.createdUtc));
        setDepthPadding(holder.itemView,post.depth);
    }

    public void clearAndAddPosts(List<RedditPostsData> newsData){
        int previousItemSize = mItems.size();
        mItems.clear();
        notifyItemRangeRemoved(0,previousItemSize);
        mItems.addAll(newsData);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void setDepthPadding(View view, int depth){
        float normalPadding = 5f;
        float leftPadding= normalPadding + (depth * 10f);

        int normalDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, normalPadding, mContext.getResources().getDisplayMetrics());
        int leftDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftPadding, mContext.getResources().getDisplayMetrics());
        view.setPadding(leftDp, normalDp, normalDp, normalDp);
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder{

        private TextView mUsername;
        private TextView mCreated;
        private TextView mText;

        public DetailViewHolder(View itemView) {
            super(itemView);
            mUsername = itemView.findViewById(R.id.username);
            mCreated = itemView.findViewById(R.id.created);
            mText = itemView.findViewById(R.id.text);
        }
    }
}
