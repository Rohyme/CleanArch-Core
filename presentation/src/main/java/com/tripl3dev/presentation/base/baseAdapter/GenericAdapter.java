package com.tripl3dev.presentation.base.baseAdapter;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by mahmoud on 6/7/2017.
 */

public class GenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int LOAD_MORE_VIEW_TYPE = -1;
    HolderInterface holderInterface;
    private View loadMoreView;
    private int maxItemsPerPage;
    private ObservableBoolean hasLoadMore = new ObservableBoolean(false);

    public GenericAdapter() {

    }

    public GenericAdapter(HolderInterface holderInterface) {
        this.holderInterface = holderInterface;
    }


    GenericHolder setLoadingMoreView(Context context) {
        if (loadMoreView == null) {
            RelativeLayout.LayoutParams containerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 70);
            RelativeLayout relativeLayout = new RelativeLayout(context);
            relativeLayout.setLayoutParams(containerParams);
            RelativeLayout.LayoutParams progressParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            ProgressBar progressBar = new ProgressBar(context);
            progressBar.setLayoutParams(progressParams);
            relativeLayout.addView(progressBar);
            progressParams.addRule(Gravity.CENTER);
            return new GenericHolder(relativeLayout);
        } else {
            return new GenericHolder(loadMoreView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOAD_MORE_VIEW_TYPE) {
            return setLoadingMoreView(parent.getContext());
        } else {
            return holderInterface.getHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holderInterface.getViewData(holder, position);
    }


    @Override
    public int getItemCount() {
        if (hasLoadMore.get()) {
            return holderInterface.listsize() + 1;
        } else {
            return holderInterface.listsize();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasLoadMore.get() && position == getItemCount() - 1) {
            return LOAD_MORE_VIEW_TYPE;
        } else {
            return 1;
        }
    }

    public void stopLoadingMore(boolean stopIt) {
        if (stopIt) {
            hasLoadMore.set(false);
        } else {
            hasLoadMore.set(true);
        }
    }

    public void hasLoadMore(Boolean hasLoadMore) {
        this.hasLoadMore.set(hasLoadMore);
    }

    public void setMaxItemsPerPage(int maxItemsPerPage) {
        this.maxItemsPerPage = maxItemsPerPage;
    }

    public void setCustomLoadMoreView(View loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    public void loadingComplete() {
        hasLoadMore.set(false);
        notifyItemChanged(getItemCount());
    }

}
