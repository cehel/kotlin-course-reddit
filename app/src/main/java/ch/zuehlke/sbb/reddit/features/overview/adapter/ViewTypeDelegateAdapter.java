package ch.zuehlke.sbb.reddit.features.overview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by chsc on 12.11.17.
 */

public interface ViewTypeDelegateAdapter {

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(RecyclerView.ViewHolder holder,ViewType item);
}
