package com.example.tcpdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Button server, client;
    private static String[] PERMISSION_STORGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        server = (Button) findViewById(R.id.bt_server);
        client = (Button) findViewById(R.id.bt_client);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_STORGE, 1000);
        }
        server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent();
                intent.setClass(MainActivity.this,ServerActivity.class);
                startActivity(intent);*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FileParseUtils.parseTxtFile(new File("/sdcard/Android/a.txt"));
                    }
                }).start();

            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });
    }
}
