package com.xcb.network.okhttp.builder;

import com.xcb.network.okhttp.OkHttpUtils;
import com.xcb.network.okhttp.request.OtherRequest;
import com.xcb.network.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
