package com.xcb.network.okhttp.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T> {
    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request, String id) {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter(String id) {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total, String id) {

    }

    /**
     * if you parse reponse code in parseNetworkResponse, you should make this method return true.
     *
     * @param response
     * @return
     */
    public boolean validateReponse(Response response, String id) {
        return response.isSuccessful();
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response, String id) throws Exception;


    public abstract void onError(Call call, Exception e, String id);

    public abstract void onResponse(T response, String id);


    public static Callback CALLBACK_DEFAULT = new Callback() {

        @Override
        public Object parseNetworkResponse(Response response, String id) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, String id) {

        }

        @Override
        public void onResponse(Object response, String id) {

        }
    };

}
