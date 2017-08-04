package com.yizhantong.xxtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yizhantong.xxtest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * desc:适配器
 * Created by xiaoxie on 2017/8/3.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.myViewHolder> {

    List<String> data;

    /**
     * 点击事件回调监听
     */
    private RecycleViewAdapter.onItemClickListener onItemClickListener;

    public RecycleViewAdapter(List<String> data) {
        this.data = data;
    }

    /**
     * 设置回调监听
     */
    public void setOnItemClickListener(RecycleViewAdapter.onItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycleview_items, parent, false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    /**
     * 将数据与界面进行绑定的操作
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {//长按事件监听
            @Override
            public boolean onLongClick(View view) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                return true; //表示此事件已经消费，不会触发单击事件
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * 删除条目
     */
    public void deleteItems(int position) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 更新条目
     */
    public void updateItems() {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.clear();
        for (int i = 0; i < 10; i++) {
            data.add("第" + i + "行");
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        this.data.clear();
    }

    public void addAllData(List<String> dataList) {
        this.data.addAll(dataList);
        notifyDataSetChanged();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public myViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }

    /**
     * 点击事件回调接口
     */
    public interface onItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}