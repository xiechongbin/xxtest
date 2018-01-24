package com.example.tcpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcb on 2018/1/24.
 */

public class TcpServer {
    private static final String TAG = TcpServer.class.getSimpleName();
    private static final int INPUT_STREAM_READ_TIMEOUT = 300;

    private ServerSocket serverSocket;//服务端
    private List<Socket> clientList = new ArrayList<>(); //客户端列表
    private Handler handler;

    public TcpServer(Handler handler) {
        this.handler = handler;
    }

    public void listen(final int port) {
        if (serverSocket != null && serverSocket.isClosed()) {

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                    Log.d(TAG, "服务器地址：" + serverSocket.getInetAddress().getHostAddress() + ":" + port);
                    while (serverSocket != null && !serverSocket.isClosed()) {
                        Socket client = serverSocket.accept();
                        if (client.isConnected()) {
                            Log.d(TAG, client.getInetAddress().toString() + " is connected");
                            if (clientList != null) {
                                clientList.add(client);
                                new Thread(new ReceiveRunnable(client, clientList.size() - 1));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 接受客户端消息的子线程
     */
    class ReceiveRunnable implements Runnable {
        private Socket client;
        private int which;

        public ReceiveRunnable(Socket client, int which) {
            this.client = client;
            this.which = which;
        }

        @Override
        public void run() {
            if (client == null) {
                return;
            }
            try {
                client.setSoTimeout(INPUT_STREAM_READ_TIMEOUT);
                InputStream is = null;
                try {
                    is = client.getInputStream();
                    while (client.isConnected()) {
                        byte[] data = new byte[0];
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = is.read(buf)) != -1) {
                            byte[] temp = new byte[data.length + len];
                            System.arraycopy(data, 0, temp, 0, data.length);
                            System.arraycopy(buf, 0, temp, data.length, len);
                            data = temp;
                        }
                        if (data.length > 0) {
                            pushMsgToHandler(client, data);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (SocketException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 推送信息给Handler
     */
    private void pushMsgToHandler(Socket socket, byte[] data) {
        Message message = handler.obtainMessage();
        message.what = 111;
        Bundle b = new Bundle();
        b.putString("ip", socket.getInetAddress().getHostAddress());
        b.putByteArray("data", data);
        message.setData(b);
        handler.sendMessage(message);
    }

    /**
     * 发送数据
     */
    private boolean sendData(int which, byte[] data) {
        if (which < 0 || clientList.size() <= 0) {
            return false;
        }
        Socket client = clientList.get(which);
        if (client != null && client.isConnected()) {
            try {
                OutputStream os = client.getOutputStream();
                os.write(data);
                os.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean sendData(int which, String data) {
        return sendData(which, data.getBytes(Charset.forName("UTF-8")));
    }

    /**
     * 获得客户端数量
     */
    public int getClientCount() {
        return clientList.size();
    }

    /**
     * 获得客户端
     */
    public Socket getClient(int which) {
        return which < 0 || which >= clientList.size() ? null : clientList.get(which);
    }

    /**
     * 关闭
     */
    public void close() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                serverSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Socket s : clientList) {
            try {
                s.close();
                s = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clientList.clear();
    }
}
