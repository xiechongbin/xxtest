package com.xiaoxie.weightrecord.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.bean.Options;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by xiaoxie on 2017/8/25.
 */
public class AddDataRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private static int VIEW_TYPE_0 = 0;
    private static int VIEW_TYPE_1 = 1;
    private static int VIEW_TYPE_2 = 2;
    private static int VIEW_TYPE_3 = 3;
    private static int VIEW_TYPE_4 = 4;

    private OnItemClickListener clickListener;
    private Options options;
    private List<String> title;

    public AddDataRecycleViewAdapter(Context context, Options options) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.options = options;
        initData();
    }

    private void initData() {
        title = new ArrayList<>();
        if (options == null) {
            return;
        }
        title.add("日期");
        if (options.getAmWeightStatus() == 1) {
            title.add("早间体重");
        }
        if (options.getPmWeightStatus() == 1) {
            title.add("午间体重");
        }
        if (options.getNightWeightStatus() == 1) {
            title.add("晚间体重");
        }
        title.add("平均体重");
        if (options.getBodyFatStatus() == 1) {
            title.add("体脂");
        }
        if (options.getInternalOrgansFatStatus() == 1) {
            title.add("内脏脂肪");
        }
        if (options.getMuscleStatus() == 1) {
            title.add("筋肉");
        }
        if (options.getBoneStatus() == 1) {
            title.add("骨头");
        }
        if (options.getBodyMoistureStatus() == 1) {
            title.add("身体水分");
        }
        if (options.getHeartRateStatus() == 1) {
            title.add("心率");
        }
        if (options.getBmrStatus() == 1) {
            title.add("BMR");
        }
        if (options.getBicepsStatus() == 1) {
            title.add("二头肌");
        }
        if (options.getNeckStatus() == 1) {
            title.add("颈部");
        }
        if (options.getWaistStatus() == 1) {
            title.add("腰");
        }
        if (options.getWristStatus() == 1) {
            title.add("腕");
        }
        if (options.getForearmStatus() == 1) {
            title.add("前臂");
        }
        if (options.getButtocksStatus() == 1) {
            title.add("臀部");
        }
        if (options.getBustStatus() == 1) {
            title.add("胸围");
        }
        if (options.getAbdomenStatus() == 1) {
            title.add("腹部");
        }
        if (options.getThighStatus() == 1) {
            title.add("大腿");
        }
        if (options.getChestStatus() == 1) {
            title.add("胸部");
        }
        if (options.getDietStatus() == 1) {
            title.add("饮食");
        }
        if (options.getActivityStatus() == 1) {
            title.add("活动");
        }
        if (options.getAnnotateStatus() == 1) {
            title.add("注释");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataViewHolder(inflater.inflate(R.layout.layout_add_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        if (holder instanceof DataViewHolder) {
            ((DataViewHolder) holder).tv_title.setText(title.get(position));
        }
    }

/*    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 11 || position == 13) {
            return VIEW_TYPE_0;
        } else if (position == 1 || position == 2 || position == 3) {
            return VIEW_TYPE_1;
        } else if (position == 4 || position == 10 || position == 9 || position == 14) {
            return VIEW_TYPE_2;
        } else if (position == 5 || position == 6 || position == 7 || position == 8) {
            return VIEW_TYPE_3;
        } else {
            return VIEW_TYPE_4;
        }
    }*/

    @Override
    public int getItemCount() {
        return title.size();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_content;

        private DataViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_add_data_title);
            tv_content = itemView.findViewById(R.id.tv_add_data_content);
        }

    }


}
