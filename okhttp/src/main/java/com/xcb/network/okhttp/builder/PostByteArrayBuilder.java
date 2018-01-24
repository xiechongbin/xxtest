package com.xcb.network.okhttp.builder;

import com.xcb.network.okhttp.request.PostByteArrayRequest;
import com.xcb.network.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * desc:post请求，传递数据类型为byte数组
 * create at: 2016/9/23 13:42
 * create by: yyw
 */
public class PostByteArrayBuilder extends OkHttpRequestBuilder<PostByteArrayBuilder> {
	private byte[] content;
	private MediaType mediaType;


	public PostByteArrayBuilder content(byte[] content) {
		this.content = content;
		return this;
	}

	public PostByteArrayBuilder mediaType(MediaType mediaType) {
		this.mediaType = mediaType;
		return this;
	}

	@Override
	public RequestCall build() {
		return new PostByteArrayRequest(url, tag, params, headers, content, mediaType, id).build();
	}
}
