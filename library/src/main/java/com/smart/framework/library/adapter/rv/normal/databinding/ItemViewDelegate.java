package com.smart.framework.library.adapter.rv.normal.databinding;


import android.databinding.ViewDataBinding;

/**
 * Created by zhy on 16/6/22.
 * RecyclerView的ItemViewDelegate，每种Item类型对应一个ItemViewDelegete
 */
public interface ItemViewDelegate<T,B extends ViewDataBinding>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(B viewBinding, ViewHolder.BindingHolder holder, T t, int position);

}
