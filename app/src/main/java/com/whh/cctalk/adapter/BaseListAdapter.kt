package com.whh.cctalk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by huscarter@163.com on 2020/6/22
 * <p>
 * 类说明: 基础Adapter
 * <p>
 */
abstract class BaseListAdapter<T> : RecyclerView.Adapter<BaseListAdapter<T>.ListViewHolder> {
    private val list = ArrayList<T>()

    constructor()

    constructor(list: List<T>) {
        this.list.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(getLayout(),parent,false))
    }


    override fun getItemCount(): Int {
        return list.size
    }

    abstract fun getLayout():Int

    /**
     *
     */
    inner class ListViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        //
    }

    /**
     *
     */
//    interface OnBindView<T>{
//        abstract fun onBindViewHolder(itemView:ListAdapter<T>.ListViewHolder, position:Int)
//    }
}
