package com.phungthanhquan.bookapp.Model.ConnectAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadMethodAPI {

    @Streaming
    @GET
    Call<ResponseBody> downLoadBook(@Url String fileUrl);

    @POST("ExrateXML.aspx")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> ConvertToDollar();
}
