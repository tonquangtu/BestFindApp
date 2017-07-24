package com.bk.bestfind.api;


import com.bk.bestfind.models.Product;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Dell on 24-Jun-17.
 */
public interface SearchApi {

    @POST("search/run/")
    @Multipart
    Call<List<Product>> search(@Part MultipartBody.Part photo, @Part("category") RequestBody category);


}
