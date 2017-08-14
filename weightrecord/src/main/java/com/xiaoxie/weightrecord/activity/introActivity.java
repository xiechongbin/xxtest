package com.xiaoxie.weightrecord.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoxie.weightrecord.R;

import java.util.ArrayList;
import java.util.List;

public class introActivity extends AppCompatActivity {
    private View intro_first;
    private View intro_senond;
    private ViewPager viewPager;
    private List<View> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initView();
        initData();

    }
    private void initView(){
        LayoutInflater layoutInflater = getLayoutInflater().from(this);
        intro_first = layoutInflater.inflate(R.layout.layout_intro_first,null);
        intro_senond = layoutInflater.inflate(R.layout.layout_intro_second,null);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }
    private void initData(){
        list = new ArrayList<View>();
        list.add(intro_first);
        list.add(intro_senond);
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

    }
}
