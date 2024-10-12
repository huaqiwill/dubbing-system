package com.moyin.tts.utils;

import java.io.File;
import java.util.Map;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections4.MapUtils;

/**
 * @author mobvoi
 * @date 2023/06/07
 **/
@Slf4j
public class OkHttpUtil {

    private OkHttpUtil() {
    }

    public static final MediaType APP_JSON = MediaType.get("application/json; charset=utf-8");
    public static final MediaType APP_OCTET_STREAM = MediaType.get("application/octet-stream");
    public static final MediaType TEXT_PLAIN = MediaType.get("text/plain; charset=utf-8");
    public static final MediaType MULTI_FORM = MediaType.get("multipart/form-data");

    public static Request buildGet(String url, Map<String, String> headers,
                                   Map<String, String> params) {
        HttpUrl.Builder urlBuilder = HttpUrl.get(url).newBuilder();
        if (MapUtils.isNotEmpty(params)) {
            params.forEach(urlBuilder::addQueryParameter);
        }

        Request.Builder requestBuilder = new Request.Builder();
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(requestBuilder::addHeader);
        }

        return requestBuilder
                .url(urlBuilder.build())
                .build();
    }

    public static Request buildPostJson(String url, Map<String, String> headers, String params) {
        return buildPost(url, headers, params, APP_JSON);
    }

    public static Request buildPostText(String url, Map<String, String> headers, String params) {
        return buildPost(url, headers, params, TEXT_PLAIN);
    }

    public static Request buildPost(String url, Map<String, String> headers, String params,
                                    MediaType mediaType) {
        RequestBody body = RequestBody.create(mediaType, params);

        Request.Builder requestBuilder = new Request.Builder();
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(requestBuilder::addHeader);
        }

        return requestBuilder
                .url(url)
                .post(body)
                .build();
    }

    public static Request buildFormData(String url, Map<String, String> params,
                                        Map<String, File> fileMap, String method) {
        //创建请求对象
        MultipartBody.Builder builder = new MultipartBody.
                Builder().setType(MultipartBody.FORM);

        if (MapUtils.isNotEmpty(params)) {
            params.forEach(builder::addFormDataPart);
        }

        if (MapUtils.isNotEmpty(fileMap)) {
            fileMap.forEach((key, value) -> builder.addFormDataPart(key, value.getName(),
                    RequestBody.create(APP_OCTET_STREAM, value)));
        }

        MultipartBody formBody = builder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        switch (method) {
            case "POST":
                requestBuilder.post(formBody);
                break;
            case "PUT":
                requestBuilder.put(formBody);
                break;
            case "DELETE":
                requestBuilder.delete(formBody);
                break;
            default:
                throw new UnsupportedOperationException(method);
        }
        return requestBuilder.build();
    }

    @SneakyThrows
    public static String execute(OkHttpClient client, Request request) {
        //执行获取响应
        Response response = client.newCall(request).execute();
        String result = (response.body() != null ? response.body().string() : null);
        if (response.isSuccessful()) {
            return result;
        }
        log.error("execute error,url:{},msg:{}", request.url(), result);
        return null;
    }

    @SneakyThrows
    public static Response executeResponse(OkHttpClient client, Request request) {
        //执行获取响应
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("execute error,url:{},msg:{}", request.url(), response.message());
        }
        return response;
    }
}
