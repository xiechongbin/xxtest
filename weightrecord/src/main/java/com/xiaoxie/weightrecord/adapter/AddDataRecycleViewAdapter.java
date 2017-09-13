package com.xiaoxie.weightrecord.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.bean.BodyData;
import com.xiaoxie.weightrecord.bean.Options;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
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

    private List<String> title;
    private List<String> contents;

    private OnItemClickListener clickListener;

    private int weightCount = 0;
    private int fatCount = 0;
    private int limbsCount = 0;
    private int otherCount = 0;
    private int anoCount = 0;
    private Options options;
    private BodyData bodyData;
    private HashMap<Integer, String> stringHashMap;

    public AddDataRecycleViewAdapter(Context context, Options options, BodyData bodyData) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.options = options;
        this.bodyData = bodyData;
        initData();
    }

    public void updateOptions(Options options) {
        this.options = options;
        initData();
    }

    private void initString() {
        if (stringHashMap == null) {
            stringHashMap = new HashMap<>();
        }
        stringHashMap.put(R.string.label_date, context.getString(R.string.label_date));
        stringHashMap.put(R.string.label_weight_morning, context.getString(R.string.label_weight_morning));
        stringHashMap.put(R.string.label_weight_noon, context.getString(R.string.label_weight_noon));
        stringHashMap.put(R.string.label_weight_night, context.getString(R.string.label_weight_night));
        stringHashMap.put(R.string.label_weight_avg, context.getString(R.string.label_weight_avg));
        stringHashMap.put(R.string.label_visceral_fat, context.getString(R.string.label_visceral_fat));
        stringHashMap.put(R.string.label_muscle, context.getString(R.string.label_muscle));
        stringHashMap.put(R.string.label_bones, context.getString(R.string.label_bones));
        stringHashMap.put(R.string.label_bmr, context.getString(R.string.label_bmr));
        stringHashMap.put(R.string.label_body_water, context.getString(R.string.label_body_water));
        stringHashMap.put(R.string.label_heart_rate, context.getString(R.string.label_heart_rate));
        stringHashMap.put(R.string.label_diet, context.getString(R.string.label_diet));
        stringHashMap.put(R.string.label_activity, context.getString(R.string.label_activity));
        stringHashMap.put(R.string.label_bicep, context.getString(R.string.label_bicep));
        stringHashMap.put(R.string.label_neck, context.getString(R.string.label_neck));
        stringHashMap.put(R.string.label_waist, context.getString(R.string.label_waist));
        stringHashMap.put(R.string.label_wrist, context.getString(R.string.label_wrist));
        stringHashMap.put(R.string.label_hips, context.getString(R.string.label_hips));
        stringHashMap.put(R.string.label_forearm, context.getString(R.string.label_forearm));
        stringHashMap.put(R.string.label_bust, context.getString(R.string.label_bust));
        stringHashMap.put(R.string.label_belly, context.getString(R.string.label_belly));
        stringHashMap.put(R.string.label_chest, context.getString(R.string.label_chest));
        stringHashMap.put(R.string.label_thighs, context.getString(R.string.label_thighs));
        stringHashMap.put(R.string.label_whr, context.getString(R.string.label_whr));
        stringHashMap.put(R.string.label_comment, context.getString(R.string.label_comment));
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
            ((RatingViewHolder) holder).ratingBar.setStepSize(1f);
            ((RatingViewHolder) holder).ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    setBodyData(position, String.valueOf((int) v));
                }
            });
        } else if (holder instanceof EditViewHolder) {
            setBodyData(position, ((EditViewHolder) holder).editText.getText().toString());
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
            if (title.get(position).equals(context.getString(R.string.label_activity)) || title.get(position).equals(context.getString(R.string.label_diet))) {
                return VIEW_TYPE_1;
            } else if (title.get(position).equals(context.getString(R.string.label_comment))) {
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

    public String getTitleWithPosition(int position) {
        if (title == null) {
            return null;
        }
        if (title.isEmpty()) {
            return null;
        }
        if (title.size() <= position) {
            return "out of range";
        }
        return title.get(position);
    }

    public void updateInputContent(String str, int position) {
        setBodyData(position, str);
        String unit = contents.get(position);
        if (!TextUtils.isEmpty(unit)) {
            if (unit.contains("cm")) {
                unit = "cm";
            } else if (unit.contains("kg")) {
                unit = "kg";
            } else if (unit.contains("%")) {
                unit = "%";
            } else {
                unit = "";
            }
        }
        contents.set(position, str + unit);
        this.notifyDataSetChanged();

    }

    public void setBodyData(int position, String str) {
        String tit = title.get(position);
        if (TextUtils.isEmpty(tit))
            return;
        if (tit.equals(context.getString(R.string.label_date))) {
            bodyData.setDate(str);
        } else if (tit.equals(context.getString(R.string.label_weight_morning))) {
            bodyData.setAmWeight(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_weight_noon))) {
            bodyData.setPmWeight(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_weight_night))) {
            bodyData.setNightWeight(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_weight_avg))) {
            bodyData.setAverageWeight(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_fat))) {
            bodyData.setBodyFat(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_visceral_fat))) {
            bodyData.setInternalOrgansFat(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_muscle))) {
            bodyData.setMuscle(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_bones))) {
            bodyData.setBone(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_bmr))) {
            bodyData.setBmr(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_body_water))) {
            bodyData.setBodyMoisture(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_heart_rate))) {
            bodyData.setHeartRate(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_diet))) {
            bodyData.setDiet(Integer.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_activity))) {
            bodyData.setActivity(Integer.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_bicep))) {
            bodyData.setBiceps(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_neck))) {
            bodyData.setNeck(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_waist))) {
            bodyData.setWaist(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_wrist))) {
            bodyData.setWrist(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_hips))) {
            bodyData.setButtocks(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_forearm))) {
            bodyData.setForearm(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_bust))) {
            bodyData.setBust(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_chest))) {
            bodyData.setChest(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_belly))) {
            bodyData.setAbdomen(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_thighs))) {
            bodyData.setThigh(Float.valueOf(str));
        } else if (tit.equals(context.getString(R.string.label_whr))) {

        } else if (tit.equals(context.getString(R.string.label_comment))) {
            bodyData.setAnnotate(str);
        }
    }

    public BodyData getBodyData() {
        return bodyData;
    }
}
