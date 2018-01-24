package com.example.tcpdemo;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * Created by yitop on 2018/1/24.
 * 客户端连接类
 */

public abstract class TcpClient {
    private static final String TAG = TcpClient.class.getSimpleName();
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int INPUT_STREAM_READ_TIMEOUT = 300;
    private Socket mSocket;
    private InputStream is;
    private OutputStream os;

    /**
     * 客户端连接
     */
    public void connect(final String host, final int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket();
                    SocketAddress socketAddress = new InetSocketAddress(host, port);
                    mSocket.connect(socketAddress, CONNECT_TIMEOUT);
                    if (mSocket.isConnected()) {
                        Log.d(TAG, mSocket.getInetAddress().toString() + "is connected");
                        // 设置读流超时时间，必须在获取流之前设置
                        mSocket.setSoTimeout(INPUT_STREAM_READ_TIMEOUT);
                        is = mSocket.getInputStream();
                        os = mSocket.getOutputStream();
                        new ReceiveThread().start();
                    } else {
                        mSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 客户端接收数据线程
     */
    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (mSocket != null && mSocket.isConnected() && is != null) {
                // 读取流
                byte[] data = new byte[0];
                byte[] buf = new byte[1024];
                int len;
                try {
                    while ((len = is.read(buf)) != -1) {
                        byte[] temp = new byte[data.length + len];
                        System.arraycopy(data, 0, temp, 0, data.length);
                        System.arraycopy(buf, 0, temp, data.length, len);
                        data = temp;
                    }
                } catch (IOException e) {
//                    e.printStackTrace();
                }

                // 处理流
                if (data.length != 0) {
                    processData(data);
                }
            }

        }
    }
    /**
     * 发送数据
     */
    public void send(byte[] data) {
        if (mSocket != null && mSocket.isConnected() && os != null) {
            try {
                os.write(data);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String data) {
        send(data.getBytes(Charset.forName("UTF-8")));
    }

    public void close() {
        if (mSocket != null) {
            try {
                os.close();
                os.close();
                mSocket.close();
                is = null;
                os = null;
                mSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    public abstract void processData(byte[] data);
}
