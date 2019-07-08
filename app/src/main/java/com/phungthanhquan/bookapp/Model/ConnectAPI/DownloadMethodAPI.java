package com.phungthanhquan.bookapp.Model.ConnectAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadMethodAPI {

    @Streaming
    @GET
    Call<ResponseBody> downLoadBook(@Url String fileUrl);
}
