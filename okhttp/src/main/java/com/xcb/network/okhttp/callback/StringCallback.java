package com.xcb.network.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, String id) throws IOException
    {
        return response.body().string();
    }

}
