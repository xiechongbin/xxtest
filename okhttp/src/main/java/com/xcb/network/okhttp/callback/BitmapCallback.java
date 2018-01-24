package com.xcb.network.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class BitmapCallback extends Callback<Bitmap> {
	@Override
	public Bitmap parseNetworkResponse(Response response, String id) throws Exception {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
 		return BitmapFactory.decodeStream(response.body().byteStream(),null,options);
	}

	@Override
	public void onError(Call call, Exception e, String id) {

	}

	@Override
	public void onResponse(Bitmap response, String id) {

	}
}
