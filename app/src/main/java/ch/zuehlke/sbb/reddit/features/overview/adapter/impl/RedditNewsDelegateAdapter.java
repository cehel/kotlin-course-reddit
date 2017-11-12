package ch.zuehlke.sbb.reddit.features.overview.adapter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.features.detail.RedditNewsDetailActivity;
import ch.zuehlke.sbb.reddit.features.overview.adapter.ViewType;
import ch.zuehlke.sbb.reddit.features.overview.adapter.ViewTypeDelegateAdapter;
import ch.zuehlke.sbb.reddit.models.RedditNewsData;
import ch.zuehlke.sbb.reddit.util.DateUtils;

/**
 * Created by chsc on 12.11.17.
 */

public class RedditNewsDelegateAdapter implements ViewTypeDelegateAdapter{

    private final OnNewsSelectedListener mListener;

    public RedditNewsDelegateAdapter(OnNewsSelectedListener listener){
        mListener = listener;
    }

    public interface OnNewsSelectedListener{
        void onNewsSelected(String url);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new RedditNewsViewHolder(parent,parent.getContext());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, ViewType item) {
        if(holder instanceof RedditNewsViewHolder){
            ((RedditNewsViewHolder) holder).bind((RedditNewsData)item);
        }
    }


    private class RedditNewsViewHolder extends RecyclerView.ViewHolder{

        private ImageView mThumbnail = itemView.findViewById(R.id.thumbnail);
        private TextView mTitle = itemView.findViewById(R.id.description);
        private TextView mAuthor = itemView.findViewById(R.id.author);
        private TextView mComments = itemView.findViewById(R.id.comments);
        private TextView mTime = itemView.findViewById(R.id.time);

        private Context mContext;

        public RedditNewsViewHolder(ViewGroup parent,Context context) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_overview, parent, false));
            mContext = context;
        }

        public void bind(final RedditNewsData data){
            mComments.setText(String.valueOf(data.getNumberOfComments()));
            mAuthor.setText(data.getAuthor());
            mTitle.setText(data.getTitle());
            mTime.setText(DateUtils.toFriendlyTime(data.getCreated()));
            if(Strings.isNullOrEmpty(data.getThumbnailUrl())) {
                Picasso.with(mContext).load(R.drawable.reddit_placeholder).into(mThumbnail);
            }else{
                Picasso.with(mContext).load(data.getThumbnailUrl()).into(mThumbnail);
            }

            super.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onNewsSelected(data.getPermaLink());
                }
            });
        }
    }
}
