package com.phungthanhquan.bookapp.Model.ConnectAPI;


public class DownloadAPI {
    private static final String BASE_URL = "https://sachvui.com";
    public static DownloadMethodAPI getApiDownload(){
        return BuildAPI.getClient(BASE_URL).create(DownloadMethodAPI.class);
    }
}
