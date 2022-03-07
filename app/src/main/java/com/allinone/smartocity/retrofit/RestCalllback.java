package com.allinone.smartocity.retrofit;

import retrofit2.Call;
import retrofit2.Response;

public interface RestCalllback<T> {

    void onFailure(Call<T> call, Throwable t, int serviceMode);

    void onResponse(Call<T> call, Response<T> response, int serviceMode);
}