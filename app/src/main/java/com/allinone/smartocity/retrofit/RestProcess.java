package com.allinone.smartocity.retrofit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import android.widget.Toast;

import com.allinone.smartocity.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestProcess<T> implements Callback<T> {
    public static ProgressHUD progressDialog;
    private final RestCalllback restCallback;
    private final int serviceMode;
    private final Gson gson;

    Context context;
    private Dialog dialog;
    private boolean dialogShown = false;


    public RestProcess(Context context, RestCalllback restCallback, int serviceMode, boolean isDialogShow) {
        this.restCallback = restCallback;
        this.serviceMode = serviceMode;
        this.context = context;
        gson = new GsonBuilder().create();
        if (isDialogShow) {
            try {
                Activity activity = (Activity) context;
                if (!activity.isFinishing()) {
                    setProgressDialog();
                    progressDialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private void setProgressDialog() {
        progressDialog = ProgressHUD.show(context, true, "");
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        //Log.d("onMyResponse", new Gson().toJson(response).toString());

        try {
            Activity activity = (Activity) context;
            if (!activity.isFinishing()) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        if (response.body() == null || response.code() != 200) {
            Toast.makeText(context, context.getString(R.string.please_try_again_later), Toast.LENGTH_SHORT).show();
            return;
        }
      /*  try {
            JSONObject body = new JSONObject(new Gson().toJson(response.body()));
            if (body.getString("status").trim().equalsIgnoreCase(GlobalVariables.STATUS.SESION_EXPIRED)) {
                if (body.getString("message").trim().equalsIgnoreCase(GlobalVariables.STATUS.BLOCKED)) {
//                    Utils.showToast(CoreApp.getInstance(), CoreApp.getInstance().getString(R.string.account_deactivated));
                    SharedStorage.setBooleanPrefs(context, GlobalVariables.CONSTANTS.USER_DEACTIVATED, true);

                    if (serviceMode == GET_PROFILE || serviceMode == ALL_LISTING ||
                            serviceMode == MY_FAV || serviceMode == GET_MY_LISTING || serviceMode == LISTING_FILTER) {
                        dialog = DialogUtils.showFullErrorDialogForDeactivate(context, context.getString(R.string.account_deactivated),
                                new AppDialogInterface() {
                                    @Override
                                    public void positiveResponse(int type, Dialog dialog) {
//                                        intentContactUs();
                                        clearAndExit();
                                        SharedStorage.setBooleanPrefs(context, GlobalVariables.CONSTANTS.USER_DEACTIVATED, true);
                                        context.startActivity(new Intent(context, WebActivity.class).
                                                putExtra(GlobalVariables.CONSTANTS.WEB_PAGE_URL, GlobalVariables.URL.CONTACT_US));
                                    }

                                    @Override
                                    public void negativeResponse(int type) {

                                    }
                                });
                    }
                } else {
                    clearAndExit();
                }
                return;
            } else if (body.getString("status").trim().equalsIgnoreCase(GlobalVariables.STATUS.UNAUTHORIZED)) {
                if (body.getString("message").trim().equalsIgnoreCase(GlobalVariables.STATUS.IN_ACTIVE)) {
                    dialog = DialogUtils.showFullErrorDialogForDeactivate(context, context.getString(R.string.account_deactivated),
                            new AppDialogInterface() {
                                @Override
                                public void positiveResponse(int type, Dialog dialog) {
//                                        intentContactUs();
                                    clearAndExit();
                                    SharedStorage.setBooleanPrefs(context, GlobalVariables.CONSTANTS.USER_DEACTIVATED, true);
                                    context.startActivity(new Intent(context, WebActivity.class).
                                            putExtra(GlobalVariables.CONSTANTS.WEB_PAGE_URL, GlobalVariables.URL.CONTACT_US));
                                }

                                @Override
                                public void negativeResponse(int type) {

                                }
                            });
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        if (restCallback != null)
            restCallback.onResponse(call, response, serviceMode);

    }


    private void clearAndExit() {


    }


    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e("RestProcess", "onFailure");
        try {

            Activity activity = (Activity) context;
            if (!activity.isFinishing()) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            if (t instanceof IOException) {
                Toast.makeText(context, context.getString(R.string.unable_to_connect_to_server), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
            if (restCallback != null)
                restCallback.onFailure(call, t, serviceMode);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}