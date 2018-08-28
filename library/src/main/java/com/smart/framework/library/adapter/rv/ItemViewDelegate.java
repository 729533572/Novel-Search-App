package com.smart.framework.library.adapter.rv;


/**
 * Created by zhy on 16/6/22.
 * RecyclerView的ItemViewDelegate，每种Item类型对应一个ItemViewDelegete
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder.BindingHolder holder, T t, int position);

}
