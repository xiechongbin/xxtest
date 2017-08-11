package com.xiaoxie.weightrecord.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.view.CircleView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleView ll_kg;
    private CircleView ll_lb;
    private CircleView ll_st;
    private CircleView ll_cm;
    private CircleView ll_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intro_first);
        initView();
    }

    private void initView() {
        ll_kg = (CircleView) findViewById(R.id.ll_kg);
        ll_lb = (CircleView) findViewById(R.id.ll_lb);
        ll_st = (CircleView) findViewById(R.id.ll_st);
        ll_cm = (CircleView) findViewById(R.id.ll_cm);
        ll_in = (CircleView) findViewById(R.id.ll_in);

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
        }
    }
}
