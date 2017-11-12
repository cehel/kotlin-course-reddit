package ch.zuehlke.sbb.reddit.features.overview.adapter.impl;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ch.zuehlke.sbb.reddit.R;
import ch.zuehlke.sbb.reddit.features.overview.adapter.ViewType;
import ch.zuehlke.sbb.reddit.features.overview.adapter.ViewTypeDelegateAdapter;

/**
 * Created by chsc on 12.11.17.
 */

public class LoadingDelegateAdapter implements ViewTypeDelegateAdapter {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new LoadingViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, ViewType type) {

    }


    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
        }
    }


}
