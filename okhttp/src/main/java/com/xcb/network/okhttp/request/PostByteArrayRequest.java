package com.xcb.network.okhttp.request;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * desc:post请求数据，内容类型是数组
 * create at: 2016/9/23 11:29
 * create by: yyw
 */
public class PostByteArrayRequest extends OkHttpRequest {

	private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("application/octet-stream");

	private byte[] content;
	private MediaType mediaType;

	public PostByteArrayRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, byte[] content, MediaType
			mediaType, String id) {
		super(url, tag, params, headers, id);
		this.content = content;
		this.mediaType = mediaType;

		if (this.mediaType == null) {
			this.mediaType = MEDIA_TYPE_PLAIN;
		}
	}

	@Override
	protected RequestBody buildRequestBody() {
		if (content == null) {
			return null;
		} else {
			return RequestBody.create(mediaType, content);
		}
	}

	@Override
	protected Request buildRequest(RequestBody requestBody) {
		if (requestBody == null) {
			return builder.get().build();
		} else {
			return builder.post(requestBody).build();
		}
	}
}
