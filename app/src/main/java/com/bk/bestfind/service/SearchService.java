package com.bk.bestfind.service;

import android.content.Context;
import android.net.Uri;

import com.bk.bestfind.api.SearchApi;
import com.bk.bestfind.application.BaseApplication;
import com.bk.bestfind.models.Product;
import com.bk.bestfind.utils.Utils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 24-Jun-17.
 */
public class SearchService {

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public void search(Uri photoUri, final SearchListener searchListener) {

        SearchApi service =
                ServiceGenerator.createService(SearchApi.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        Context context = BaseApplication.getContext();
//        File file = FileUtils.getFile(context, photoUri);
        File file = new File(Utils.getPathFile(photoUri));

        // create RequestBody instance from file
        RequestBody filePart =
                RequestBody.create(
                        MediaType.parse(context.getContentResolver().getType(photoUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part photo =
                MultipartBody.Part.createFormData("photo", file.getName(), filePart);

        String categoryString = "Áo nữ";
        RequestBody category =
                RequestBody.create(
                        MultipartBody.FORM, categoryString);

        // finally, execute the request
        Call<List<Product>> call = service.search( photo, category);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {

                if (response.code() == 200) {

                    if (searchListener != null) {
                        searchListener.complete(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                if (searchListener != null) {
                    searchListener.fail(t);
                }
            }
        });
    }

    public void search(String pathFile, String categoryString, final SearchListener searchListener) {

        SearchApi service =
                ServiceGenerator.createService(SearchApi.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        Context context = BaseApplication.getContext();
//        File file = FileUtils.getFile(context, photoUri);
        File file = new File(pathFile);

        // create RequestBody instance from file
        RequestBody filePart =
                RequestBody.create(
                        MediaType.parse(MULTIPART_FORM_DATA),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part photo =
                MultipartBody.Part.createFormData("photo", file.getName(), filePart);

        RequestBody category =
                RequestBody.create(
                        MultipartBody.FORM, categoryString);

        // finally, execute the request
        Call<List<Product>> call = service.search( photo, category);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {

                if (response.code() == 200) {

                    if (searchListener != null) {
                        searchListener.complete(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                if (searchListener != null) {
                    searchListener.fail(t);
                }
            }
        });
    }


}
