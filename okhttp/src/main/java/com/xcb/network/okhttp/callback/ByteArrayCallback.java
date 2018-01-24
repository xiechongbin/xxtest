package com.xcb.network.okhttp.callback;

import okhttp3.Response;

/**
 * desc:字节型数组回调接口
 * create at: 2016/9/26 16:15
 * create by: yyw
 */
public abstract class ByteArrayCallback  extends Callback<byte[]>{

	@Override
	public byte[] parseNetworkResponse(Response response, String id) throws Exception {
		return response.body().bytes();
	}
}
