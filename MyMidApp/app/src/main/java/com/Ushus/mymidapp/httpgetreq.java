package com.Ushus.mymidapp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;



public class httpgetreq {

    public static String GET(OkHttpClient client, String url) throws IOException
    {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return  response.body().string();

    }
}
