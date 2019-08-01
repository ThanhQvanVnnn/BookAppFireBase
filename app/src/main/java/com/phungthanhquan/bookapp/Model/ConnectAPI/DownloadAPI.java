package com.phungthanhquan.bookapp.Model.ConnectAPI;


import android.content.Context;

public class DownloadAPI {
    private static final String BASE_URL = "https://www.vietcombank.com.vn/exchangerates/";
    public static DownloadMethodAPI getApiDownload(Context context){
        return BuildAPI.getClient(BASE_URL,context).create(DownloadMethodAPI.class);
    }
}
