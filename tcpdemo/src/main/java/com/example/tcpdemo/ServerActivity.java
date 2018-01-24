package com.example.tcpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.nio.charset.Charset;

public class ServerActivity extends AppCompatActivity {
    private Button bt_create_server;
    private Button bt_s_send;
    private EditText ed_s_input;
    private TextView tv_s_content;
    private static final int PORT = 10800;
    private static final int TIMEOUT = 10000;//超时时间

    private TcpServer tcpServer;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        bt_create_server = (Button) findViewById(R.id.bt_create_server);
        bt_s_send = (Button) findViewById(R.id.bt_s_send);
        ed_s_input = (EditText) findViewById(R.id.ed_s_input);
        tv_s_content = (TextView) findViewById(R.id.tv_s_content);
        handler = new MyHandler(this);
        bt_create_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createServer();
            }
        });
        bt_s_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tcpServer == null) {
                    return;
                }
                for (int i = 0; i < tcpServer.getClientCount(); i++) {
                    tcpServer.sendData(i, ed_s_input.getText().toString());
                }

            }
        });
    }

    /**
     * 创建服务端
     */
    private void createServer() {
        tcpServer = new TcpServer(handler);
        tcpServer.listen(PORT);
    }

    private static class MyHandler extends Handler {
        private SoftReference<ServerActivity> reference;
        private ServerActivity activity;

        public MyHandler(ServerActivity serverActivity) {
            reference = new SoftReference<>(serverActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (reference.get() == null) {
                return;
            }
            activity = reference.get();
            if (msg.what == 111) {
                Bundle bundle = msg.getData();
                String ip = bundle.getString("ip");
                byte[] data = bundle.getByteArray("data");
                activity.tv_s_content.setText("收到来自" + ip + "的消息>" + new String(data, Charset.forName("UTF-8")));
            }
        }
    }
}
