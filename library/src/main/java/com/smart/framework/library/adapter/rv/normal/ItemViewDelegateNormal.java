package com.smart.framework.library.adapter.rv.normal;


import com.smart.framework.library.adapter.rv.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 * RecyclerView的ItemViewDelegate，每种Item类型对应一个ItemViewDelegete
 */
public interface ItemViewDelegateNormal<T> {


    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder.BindingHolder holder, T t, int position);


}
