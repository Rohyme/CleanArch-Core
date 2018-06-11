package com.tripl3dev.presentation.base.baseAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by mahmoud on 6/7/2017.
 */

public interface HolderInterface {

    public RecyclerView.ViewHolder getHolder(ViewGroup view);

    public void getViewData(RecyclerView.ViewHolder holder, int postion);

    public int listsize();

}
