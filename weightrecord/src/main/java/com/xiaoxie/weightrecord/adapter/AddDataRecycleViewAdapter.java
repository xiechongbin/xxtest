package com.xiaoxie.weightrecord.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.bean.Options;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.PrimaryKey;

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

    private List<String> title;
    private List<String> contents;

    private OnItemClickListener clickListener;

    private int weightCount = 0;
    private int fatCount = 0;
    private int limbsCount = 0;
    private int otherCount = 0;
    private int anoCount = 0;
    private Options options;

    public AddDataRecycleViewAdapter(Context context, Options options) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.options = options;
        initData();
    }

    public void updateOptions(Options options) {
        this.options = options;
        initData();
    }

    public void initData() {
        if (options == null)
            return;
        title = new ArrayList<>();
        contents = new ArrayList<>();
        title.clear();
        contents.clear();

        weightCount = 0;
        fatCount = 0;
        limbsCount = 0;
        otherCount = 0;
        anoCount = 0;
        title.add(context.getString(R.string.label_date));
        contents.add(Utils.getCurrentDate());
        weightCount++;
        if (options.getAmWeightStatus() == 1) {
            title.add(context.getString(R.string.label_weight_morning));
            contents.add("--kg");
            weightCount++;
        }
        if (options.getPmWeightStatus() == 1) {
            title.add(context.getString(R.string.label_weight_noon));
            contents.add("--kg");
            weightCount++;
        }
        if (options.getNightWeightStatus() == 1) {
            title.add(context.getString(R.string.label_weight_night));
            contents.add("--kg");
            weightCount++;
        }
        title.add(context.getString(R.string.label_weight_avg));
        contents.add("--kg");

        weightCount++;
        if (options.getBodyFatStatus() == 1) {
            title.add(context.getString(R.string.label_fat));
            contents.add("--");
            fatCount++;
        }
        if (options.getInternalOrgansFatStatus() == 1) {
            title.add(context.getString(R.string.label_visceral_fat));
            contents.add("--%");
            fatCount++;
        }
        if (options.getMuscleStatus() == 1) {
            title.add(context.getString(R.string.label_muscle));
            contents.add("--%");
            fatCount++;
        }
        if (options.getBoneStatus() == 1) {
            title.add(context.getString(R.string.label_bones));
            contents.add("--%");
            fatCount++;
        }
        if (options.getBodyMoistureStatus() == 1) {
            title.add(context.getString(R.string.label_body_water));
            contents.add("--%");
            fatCount++;
        }
        if (options.getHeartRateStatus() == 1) {
            title.add(context.getString(R.string.label_heart_rate));
            contents.add("BMP");
            fatCount++;
        }
        if (options.getBmrStatus() == 1) {
            title.add(context.getString(R.string.label_bmr));
            contents.add("--");
            fatCount++;
        }
        if (options.getBicepsStatus() == 1) {
            title.add(context.getString(R.string.label_bicep));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getNeckStatus() == 1) {
            title.add(context.getString(R.string.label_neck));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getWaistStatus() == 1) {
            title.add(context.getString(R.string.label_waist));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getWristStatus() == 1) {
            title.add(context.getString(R.string.label_wrist));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getForearmStatus() == 1) {
            title.add(context.getString(R.string.label_forearm));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getButtocksStatus() == 1) {
            title.add(context.getString(R.string.label_hips));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getBustStatus() == 1) {
            title.add(context.getString(R.string.label_bust));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getAbdomenStatus() == 1) {
            title.add(context.getString(R.string.label_belly));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getThighStatus() == 1) {
            title.add(context.getString(R.string.label_thighs));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getChestStatus() == 1) {
            title.add(context.getString(R.string.label_chest));
            contents.add("cm");
            limbsCount++;
        }
        if (options.getDietStatus() == 1) {
            title.add(context.getString(R.string.label_diet));
            contents.add("5");
            otherCount++;
        }
        if (options.getActivityStatus() == 1) {
            title.add(context.getString(R.string.label_activity));
            contents.add("5");
            otherCount++;
        }
        if (options.getAnnotateStatus() == 1) {
            title.add(context.getString(R.string.label_comment));
            contents.add("annotate");
            anoCount++;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_0) {
            return new DataViewHolder(inflater.inflate(R.layout.layout_add_data_item0, parent, false));
        } else if (viewType == VIEW_TYPE_1) {
            return new RatingViewHolder(inflater.inflate(R.layout.layout_add_data_item1, parent, false));
        } else if (viewType == VIEW_TYPE_2) {
            return new EditViewHolder(inflater.inflate(R.layout.layout_add_data_item2, parent, false));
        } else {
            return new MoreChoiceViewHolder(inflater.inflate(R.layout.layout_add_data_item3, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        if (holder instanceof DataViewHolder) {
            ((DataViewHolder) holder).tv_title.setText(title.get(position));
            ((DataViewHolder) holder).tv_content.setText(contents.get(position));
            ((DataViewHolder) holder).rootView0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onItemClick(position, 0);
                    }
                }
            });
        } else if (holder instanceof RatingViewHolder) {
            ((RatingViewHolder) holder).tv_title.setText(title.get(position));
        } else if (holder instanceof EditViewHolder) {

        } else if (holder instanceof MoreChoiceViewHolder) {
            ((MoreChoiceViewHolder) holder).rootView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onItemClick(position, R.id.root_view3);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= title.size()) {
            return VIEW_TYPE_3;
        } else {
            if (title.get(position).equals("活动") || title.get(position).equals("饮食")) {
                return VIEW_TYPE_1;
            } else if (title.get(position).equals("注释")) {
                return VIEW_TYPE_2;
            } else {
                return VIEW_TYPE_0;
            }
        }

    }

    @Override
    public int getItemCount() {
        return title.size() + 1;
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_content;
        private RelativeLayout rootView0;

        private DataViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_add_data_title);
            tv_content = itemView.findViewById(R.id.tv_add_data_content);
            rootView0 = itemView.findViewById(R.id.root_view0);
        }

    }

    private class RatingViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private RatingBar ratingBar;

        private RatingViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_add_data_title1);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

    }

    private class EditViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;


        private EditViewHolder(View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.edit_ano);
        }

    }

    private class MoreChoiceViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout rootView3;

        private MoreChoiceViewHolder(View itemView) {
            super(itemView);
            rootView3 = itemView.findViewById(R.id.root_view3);
        }

    }

    /**
     * @return 返回体重相关的条目的数量, 日期也归于此类
     */
    public int getWeightItemCounts() {
        return weightCount;
    }

    public int getFatItemCount() {
        return fatCount;
    }

    public int getLimbsCount() {
        return limbsCount;
    }

    public int getOtherCount() {
        return otherCount;
    }

    public int getAnoCount() {
        return anoCount;
    }

    public void updateInputContent(String str, int position) {
        contents.set(position, str);
        this.notifyDataSetChanged();

    }
}
