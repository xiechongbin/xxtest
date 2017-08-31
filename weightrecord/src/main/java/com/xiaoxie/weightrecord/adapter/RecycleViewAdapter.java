package com.xiaoxie.weightrecord.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.interfaces.OnItemClickListener;
import com.xiaoxie.weightrecord.utils.SharePrefenceUtils;
import com.xiaoxie.weightrecord.utils.Utils;

/**
 * desc:
 * Created by xiaoxie on 2017/8/25.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private static int VIEW_TYPE_0 = 0;
    private static int VIEW_TYPE_1 = 1;
    private static int VIEW_TYPE_2 = 2;
    private static int VIEW_TYPE_3 = 3;
    private static int VIEW_TYPE_4 = 4;
    private static int MAX_COUNT = 15;

    private OnItemClickListener clickListener;

    public RecycleViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_0) {
            return new TitleViewHolder(inflater.inflate(R.layout.layout_item0, parent, false));
        } else if (viewType == VIEW_TYPE_1) {
            return new CommonViewHolder(inflater.inflate(R.layout.layout_item1, parent, false));
        } else if (viewType == VIEW_TYPE_2) {
            return new Common1ViewHolder(inflater.inflate(R.layout.layout_item2, parent, false));
        } else if (viewType == VIEW_TYPE_3) {
            return new SwitchViewHolder(inflater.inflate(R.layout.layout_item3, parent, false));
        } else if (viewType == VIEW_TYPE_4) {
            return new ThemeViewHolder(inflater.inflate(R.layout.layout_item4, parent, false));
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        if (holder instanceof TitleViewHolder) {
            if (position == 0) {
                ((TitleViewHolder) holder).tv_item0_title.setText(R.string.label_general);
            } else if (position == 11) {
                ((TitleViewHolder) holder).tv_item0_title.setText(R.string.label_theme);
            } else if (position == 13) {
                ((TitleViewHolder) holder).tv_item0_title.setText(R.string.label_about);
            }
        } else if (holder instanceof CommonViewHolder) {
            if (position == 1) {
                ((CommonViewHolder) holder).tv_item1_title.setText(R.string.label_backup);
                ((CommonViewHolder) holder).imageView.setImageResource(R.drawable.ic_entrance);
            } else if (position == 2) {
                ((CommonViewHolder) holder).tv_item1_title.setText(R.string.label_remind);
                ((CommonViewHolder) holder).imageView.setImageResource(R.drawable.ic_entrance);
            } else if (position == 3) {
                ((CommonViewHolder) holder).tv_item1_title.setText(R.string.label_lang);
                ((CommonViewHolder) holder).imageView.setImageResource(R.drawable.ic_entrance);
            }
        } else if (holder instanceof Common1ViewHolder) {
            if (position == 4) {
                String firstDay = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_FIRST_DAY_OF_WEEK, context.getString(R.string.monday));
                ((Common1ViewHolder) holder).tv_item2_title.setText(R.string.label_first_day_week);
                ((Common1ViewHolder) holder).tv_item2_content.setText(firstDay);
                ((Common1ViewHolder) holder).imageView.setImageResource(R.drawable.ic_entrance);
            } else if (position == 10) {
                String inputType = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_WEIGHT_INPUT_TYPE, context.getString(R.string.input_direct));
                ((Common1ViewHolder) holder).tv_item2_title.setText(R.string.label_weight_picker);
                ((Common1ViewHolder) holder).tv_item2_content.setText(inputType);
                ((Common1ViewHolder) holder).imageView.setImageResource(R.drawable.ic_entrance);
            } else if (position == 9) {
                String unit_weight = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_WEIGHT_UNIT, context.getString(R.string.e_kg));
                ((Common1ViewHolder) holder).tv_item2_title.setText(R.string.label_measure);
                ((Common1ViewHolder) holder).tv_item2_content.setText(unit_weight);
                ((Common1ViewHolder) holder).imageView.setVisibility(View.GONE);
            } else if (position == 14) {
                ((Common1ViewHolder) holder).tv_item2_title.setText(R.string.label_version);
                ((Common1ViewHolder) holder).tv_item2_content.setText(Utils.getVersion(context));
                ((Common1ViewHolder) holder).imageView.setVisibility(View.GONE);
            }
        } else if (holder instanceof SwitchViewHolder) {
            if (position == 5) {
                ((SwitchViewHolder) holder).tv_item3_title.setText(R.string.label_lock);
                boolean hasPassword = SharePrefenceUtils.getBoolean(context, SharePrefenceUtils.KEY_HAS_PASSWORD, false);
                ((SwitchViewHolder) holder).toggleButton.setBackgroundResource(hasPassword ? R.drawable.switch_on : R.drawable.switch_off);
                ((SwitchViewHolder) holder).toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((SwitchViewHolder) holder).toggleButton.isChecked()) {

                        } else {
                            SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_HAS_PASSWORD, false);
                            SharePrefenceUtils.setString(context, SharePrefenceUtils.KEY_PASSWORD, "");//制空
                        }
                    }
                });
            } else if (position == 6) {
                boolean autoBMI = SharePrefenceUtils.getBoolean(context, SharePrefenceUtils.KEY_EXPORT_BMI_AUTO, false);
                ((SwitchViewHolder) holder).toggleButton.setBackgroundResource(autoBMI ? R.drawable.switch_on : R.drawable.switch_off);
                ((SwitchViewHolder) holder).tv_item3_title.setText(R.string.label_fat_cat_auto);
                ((SwitchViewHolder) holder).toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((SwitchViewHolder) holder).toggleButton.isChecked()) {
                            SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_EXPORT_BMI_AUTO, true);
                        } else {
                            SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_EXPORT_BMI_AUTO, false);
                        }
                    }
                });
            } else if (position == 7) {
                boolean autoBMR = SharePrefenceUtils.getBoolean(context, SharePrefenceUtils.KEY_EXPORT_BMR_AUTO, false);
                ((SwitchViewHolder) holder).toggleButton.setBackgroundResource(autoBMR ? R.drawable.switch_on : R.drawable.switch_off);
                ((SwitchViewHolder) holder).tv_item3_title.setText(R.string.label_bmr_cat_auto);
                ((SwitchViewHolder) holder).toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((SwitchViewHolder) holder).toggleButton.isChecked()) {
                            SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_EXPORT_BMR_AUTO, true);
                        } else {
                            SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_EXPORT_BMR_AUTO, false);
                        }
                    }
                });
            } else if (position == 8) {
                boolean audio = SharePrefenceUtils.getBoolean(context, SharePrefenceUtils.KEY_AUDIO, false);
                ((SwitchViewHolder) holder).toggleButton.setBackgroundResource(audio ? R.drawable.switch_on : R.drawable.switch_off);
                ((SwitchViewHolder) holder).tv_item3_title.setText(R.string.label_sound_effect);
                ((SwitchViewHolder) holder).toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((SwitchViewHolder) holder).toggleButton.isChecked()) {
                            SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_AUDIO, true);
                        } else {
                            SharePrefenceUtils.setBoolean(context, SharePrefenceUtils.KEY_AUDIO, false);
                        }
                    }
                });
            }

        } else if (holder instanceof ThemeViewHolder) {
            if (position == 12) {
                ((ThemeViewHolder) holder).theme_green.setImageResource(R.color.color_theme_green);
                ((ThemeViewHolder) holder).theme_blue.setImageResource(R.color.color_theme_blue);
                ((ThemeViewHolder) holder).theme_purple.setImageResource(R.color.color_theme_purple);
                ((ThemeViewHolder) holder).theme_pink.setImageResource(R.color.color_theme_pink);
                ((ThemeViewHolder) holder).theme_green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (clickListener != null) {
                            clickListener.onItemClick(position, R.id.img_item4_theme_green);
                        }
                    }
                });
                ((ThemeViewHolder) holder).theme_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (clickListener != null) {
                            clickListener.onItemClick(position, R.id.img_item4_theme_blue);
                        }
                    }
                });
                ((ThemeViewHolder) holder).theme_purple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (clickListener != null) {
                            clickListener.onItemClick(position, R.id.img_item4_theme_purple);
                        }
                    }
                });
                ((ThemeViewHolder) holder).theme_pink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (clickListener != null) {
                            clickListener.onItemClick(position, R.id.img_item4_theme_pink);
                        }
                    }
                });
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onItemClick(position, 0);
                }
            }
        });
    }

    @Override
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
    }

    @Override
    public int getItemCount() {
        return MAX_COUNT;
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item0_title;

        private TitleViewHolder(View itemView) {
            super(itemView);
            tv_item0_title = itemView.findViewById(R.id.tv_item0_title);
        }

    }

    private class CommonViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item1_title;
        private ImageView imageView;

        private CommonViewHolder(View itemView) {
            super(itemView);
            tv_item1_title = itemView.findViewById(R.id.tv_item1_title);
            imageView = itemView.findViewById(R.id.img_item1_next);
        }

    }

    public class Common1ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item2_title;
        public TextView tv_item2_content;
        private ImageView imageView;

        private Common1ViewHolder(View itemView) {
            super(itemView);
            tv_item2_title = itemView.findViewById(R.id.tv_item2_title);
            tv_item2_content = itemView.findViewById(R.id.tv_item2_content);
            imageView = itemView.findViewById(R.id.img_item2_next);
        }

    }

    public class SwitchViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item3_title;
        public ToggleButton toggleButton;

        private SwitchViewHolder(View itemView) {
            super(itemView);
            tv_item3_title = itemView.findViewById(R.id.tv_item3_title);
            toggleButton = itemView.findViewById(R.id.img_item3_toggle_button);
        }

    }

    private class ThemeViewHolder extends RecyclerView.ViewHolder {
        private ImageView theme_green;
        private ImageView theme_blue;
        private ImageView theme_purple;
        private ImageView theme_pink;

        private ThemeViewHolder(View itemView) {
            super(itemView);
            theme_green = itemView.findViewById(R.id.img_item4_theme_green);
            theme_blue = itemView.findViewById(R.id.img_item4_theme_blue);
            theme_purple = itemView.findViewById(R.id.img_item4_theme_purple);
            theme_pink = itemView.findViewById(R.id.img_item4_theme_pink);
        }

    }
}
