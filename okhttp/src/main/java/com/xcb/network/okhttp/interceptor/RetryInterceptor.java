package com.xcb.network.okhttp.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * desc:失败重试拦截器
 * create at: 2017/3/7 14:58
 * create by: yyw
 */
public class RetryInterceptor implements Interceptor {
    private int maxRetry;//最大重试次数
    private int retryCount;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = doResponse(chain, request);
        while ((response == null || !response.isSuccessful()) && retryCount < maxRetry) {
            retryCount++;
            response = doResponse(chain, request);
        }
        if (response == null) {
            throw new IOException();
        }
        return response;
    }

    private Response doResponse(Chain chain, Request request) {
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}