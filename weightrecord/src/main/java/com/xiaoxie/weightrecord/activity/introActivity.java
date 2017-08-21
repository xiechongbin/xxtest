package com.xiaoxie.weightrecord.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.BlockedNumberContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.CustomDialog;
import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.bean.PersonData;
import com.xiaoxie.weightrecord.interfaces.DialogClickListener;
import com.xiaoxie.weightrecord.view.CircleView;
import com.xiaoxie.weightrecord.view.DashBoardView;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class introActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private View intro_first;
    private View intro_second;
    private ViewPager viewPager;
    private List<View> list;

    private CircleView ll_kg;
    private CircleView ll_lb;
    private CircleView ll_st;
    private CircleView ll_cm;
    private CircleView ll_in;

    private ImageView img_back;
    private ImageView img_dot1;
    private ImageView img_dot2;
    private ImageView img_next;
    private Bitmap bitmap_25;
    private Bitmap bitmap_30;

    private LinearLayout ll_weight;
    private LinearLayout ll_height;
    private LinearLayout ll_sex;
    private LinearLayout ll_birthday;

    private TextView tv_sex;
    private TextView tv_height;
    private TextView tv_weight;
    private TextView tv_birthday;
    private PersonData personData;
    private DashBoardView dashBoardView;

    private myHandler handler = new myHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        initFirstPage();
        initSecondPage();
        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<>();
        list.add(intro_first);
        list.add(intro_second);
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
                container.removeView(list.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }
        };
        viewPager.setAdapter(adapter);
        personData = new PersonData();
    }

    /**
     * 初始化第一个界面
     */
    private void initFirstPage() {
        LayoutInflater layoutInflater = getLayoutInflater().from(this);
        intro_first = layoutInflater.inflate(R.layout.layout_intro_first, null);
        intro_second = layoutInflater.inflate(R.layout.layout_intro_second, null);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        ll_kg = intro_first.findViewById(R.id.ll_kg);
        ll_lb = intro_first.findViewById(R.id.ll_lb);
        ll_st = intro_first.findViewById(R.id.ll_st);
        ll_cm = intro_first.findViewById(R.id.ll_cm);
        ll_in = intro_first.findViewById(R.id.ll_in);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_dot1 = (ImageView) findViewById(R.id.img_fistpage);
        img_dot2 = (ImageView) findViewById(R.id.img_secondpages);
        img_next = (ImageView) findViewById(R.id.img_next);

        ll_kg.setColor(this.getResources().getColor(R.color.color_f3a11e));
        ll_lb.setColor(this.getResources().getColor(R.color.color_27ae60));
        ll_st.setColor(this.getResources().getColor(R.color.color_27ae60));
        ll_cm.setColor(this.getResources().getColor(R.color.color_27ae60));
        ll_in.setColor(this.getResources().getColor(R.color.color_27ae60));

        ll_kg.setOnClickListener(this);
        ll_lb.setOnClickListener(this);
        ll_st.setOnClickListener(this);
        ll_cm.setOnClickListener(this);
        ll_in.setOnClickListener(this);

        img_back.setOnClickListener(this);
        img_next.setOnClickListener(this);

        viewPager.setOnPageChangeListener(this);

        img_back.setVisibility(View.INVISIBLE);
        bitmap_25 = creatBitmap(25, 25);
        bitmap_30 = creatBitmap(30, 30);
        img_dot1.setImageBitmap(bitmap_30);
        img_dot2.setImageBitmap(bitmap_25);
    }

    private void initSecondPage() {
        ll_sex = intro_second.findViewById(R.id.ll_sex);
        ll_weight = intro_second.findViewById(R.id.ll_weight);
        ll_height = intro_second.findViewById(R.id.ll_height);
        ll_birthday = intro_second.findViewById(R.id.ll_birthday);

        tv_sex = intro_second.findViewById(R.id.tv_sex);
        tv_weight = intro_second.findViewById(R.id.tv_weight);
        tv_height = intro_second.findViewById(R.id.tv_height);
        tv_birthday = intro_second.findViewById(R.id.tv_birthday);
        dashBoardView = intro_second.findViewById(R.id.dashboardView);

        ll_sex.setOnClickListener(this);
        ll_weight.setOnClickListener(this);
        ll_height.setOnClickListener(this);
        ll_birthday.setOnClickListener(this);
    }

    private Bitmap creatBitmap(int x, int y) {
        Bitmap bitmap = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawOval(rectF, paint);
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_kg:
                ll_kg.setColor(this.getResources().getColor(R.color.color_f3a11e));
                ll_lb.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_st.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_cm.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_in.setColor(this.getResources().getColor(R.color.color_27ae60));
                break;
            case R.id.ll_lb:
                ll_kg.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_lb.setColor(this.getResources().getColor(R.color.color_f3a11e));
                ll_st.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_cm.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_in.setColor(this.getResources().getColor(R.color.color_27ae60));
                break;
            case R.id.ll_st:
                ll_kg.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_lb.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_st.setColor(this.getResources().getColor(R.color.color_f3a11e));
                ll_cm.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_in.setColor(this.getResources().getColor(R.color.color_27ae60));
                break;
            case R.id.ll_cm:
                ll_kg.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_lb.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_st.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_cm.setColor(this.getResources().getColor(R.color.color_f3a11e));
                ll_in.setColor(this.getResources().getColor(R.color.color_27ae60));
                break;
            case R.id.ll_in:
                ll_kg.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_lb.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_st.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_cm.setColor(this.getResources().getColor(R.color.color_27ae60));
                ll_in.setColor(this.getResources().getColor(R.color.color_f3a11e));
                break;
            case R.id.img_back:
                viewPager.setCurrentItem(0);
                break;
            case R.id.img_next:
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_sex:
                showSexDialog();
                break;
            case R.id.ll_birthday:
                showBirthdayDialog();
                break;
            case R.id.ll_height:
                showHeightDialog();
                break;
            case R.id.ll_weight:
                showWeightDialog();
                break;
        }
    }

    /**
     * 性别展示对话框
     */
    private void showSexDialog() {
        final CustomDialog.SexBuilder builder = new CustomDialog.SexBuilder(this);
        builder.setOnSexDialogOnclickListener(new CustomDialog.SexBuilder.SexDialogOnClickListener() {
            @Override
            public void OnConfirmed(String sex) {
                if (TextUtils.isEmpty(sex))
                    return;
                tv_sex.setText(sex);
                personData.setSex(sex);
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.create().show();
    }

    /**
     * 体重展示对话框
     */
    private void showWeightDialog() {
        final CustomDialog.WeightAndHeightBuilder builder = new CustomDialog.WeightAndHeightBuilder(this);
        builder.setOnWeightDialogOnclickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String weight) {
                if (TextUtils.isEmpty(weight))
                    return;
                tv_weight.setText(weight);
                personData.setWeight(Float.valueOf(weight));
                startAnimation();
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.create().show();
    }

    /**
     * 身高展示对话框
     */
    private void showHeightDialog() {
        final CustomDialog.WeightAndHeightBuilder builder = new CustomDialog.WeightAndHeightBuilder(this, true);
        builder.setOnWeightDialogOnclickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String height) {
                if (TextUtils.isEmpty(height))
                    return;
                tv_height.setText(height);
                personData.setHeight(Float.valueOf(height));
                startAnimation();
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.create().show();
    }

    /**
     * 生日展示对话框
     */
    private void showBirthdayDialog() {
        final CustomDialog.BirthdayBuilder builder = new CustomDialog.BirthdayBuilder(this);
        builder.setOnBirthdayDialogOnclickListener(new DialogClickListener() {
            @Override
            public void OnConfirmed(String date) {
                if (TextUtils.isEmpty(date))
                    return;
                tv_birthday.setText(date);
                personData.setBirthday(date);
            }

            @Override
            public void OnCanceled() {
            }
        });
        builder.create().show();
    }

    /**
     * 开始动画
     */
    private void startAnimation() {
        if (personData.getWeight() <= 0 || personData.getHeight() <= 0)
            return;
        personData.setBmi(calcuateBMI(personData.getWeight(), personData.getHeight()));
        final float bmi = personData.getBmi();
        dashBoardView.setProgress(bmi);
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                float tmp = 0;
                while (tmp < bmi) {
                    try {
                        tmp = tmp + 0.5f;
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        message.what = 100;
                        bundle.putFloat("bmi", tmp);
                        message.setData(bundle);
                        Thread.sleep(10);
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (tmp >= bmi) {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    message.what = 101;
                    bundle.putFloat("bmi", bmi);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        }).start();*/
    }

    /**
     * 计算bmi公式
     *
     * @param weight
     * @param height
     * @return
     */
    private float calcuateBMI(float weight, float height) {
        height = height / 100;
        float bmi = weight / (height * height);
        Log.d("bmi", "weight = " + weight + ">>height = " + height + ">>bmi = " + bmi);
        return bmi;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            img_back.setVisibility(View.INVISIBLE);
            img_dot1.setImageBitmap(bitmap_30);
            img_dot2.setImageBitmap(bitmap_25);
        } else if (position == 1) {
            img_back.setVisibility(View.VISIBLE);
            img_dot1.setImageBitmap(bitmap_25);
            img_dot2.setImageBitmap(bitmap_30);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class myHandler extends Handler {
        WeakReference<introActivity> reference;

        public myHandler(introActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final introActivity activity = reference.get();
            if (activity != null) {
                if (msg.what == 100) {
                    Bundle bundle = msg.getData();
                } else if (msg.what == 101) {
                    Bundle bundle = msg.getData();
                    float bmi = bundle.getFloat("bmi");
                }
            }
        }
    }
}
