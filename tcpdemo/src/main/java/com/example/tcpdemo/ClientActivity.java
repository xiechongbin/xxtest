package com.example.tcpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.nio.charset.Charset;

public class ClientActivity extends AppCompatActivity {
    private Button bt_connect;
    private Button bt_c_send;
    private TcpClient client;
    private MyHandler handler;
    private TextView tv_content;
    private EditText editTextIp, editTextPort, ed_c_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        handler = new MyHandler(this);
        client = new TcpClient() {
            @Override
            public void processData(byte[] data) {
                Message message = new Message();
                message.obj = data;
                message.what = 112;
                handler.sendMessage(message);
            }
        };
        bt_connect = (Button) findViewById(R.id.bt_connect);
        bt_c_send = (Button) findViewById(R.id.bt_c_send);
        tv_content = (TextView) findViewById(R.id.tv_content);
        editTextIp = (EditText) findViewById(R.id.ed_ip);
        editTextPort = (EditText) findViewById(R.id.ed_port);
        ed_c_content = (EditText) findViewById(R.id.ed_c_content);

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = editTextIp.getText().toString();
                if (TextUtils.isEmpty(ip)) {
                    return;
                }
                String port = editTextPort.getText().toString();
                if (TextUtils.isEmpty(ip)) {
                    return;
                }
                client.connect(ip, Integer.valueOf(port));
            }
        });
        bt_c_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.send(ed_c_content.getText().toString());
            }
        });
    }

    private static class MyHandler extends Handler {
        private SoftReference<ClientActivity> softReference;
        private ClientActivity activity;

        public MyHandler(ClientActivity clientActivity) {
            softReference = new SoftReference<>(clientActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (softReference.get() == null) {
                return;
            }
            activity = softReference.get();
            if (msg.what == 112) {
                byte[] data = (byte[]) msg.obj;
                String content = new String(data, Charset.forName("UTF-8"));
                activity.tv_content.setText(content);
            }
        }
    }
}
