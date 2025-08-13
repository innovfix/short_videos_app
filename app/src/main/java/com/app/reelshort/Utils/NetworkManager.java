package com.app.reelshort.Utils;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.app.reelshort.R;
import com.app.reelshort.UI.Adapter.ReelsAdapter;
import com.app.reelshort.databinding.DialogNoInternetBinding;
import com.app.reelshort.databinding.DialogServerErrorBinding;

public class NetworkManager extends BroadcastReceiver {
    private static NetworkManager instance;
    private Activity currentActivity;
    private final Application application;
    private Dialog dialog;

    private NetworkManager(Application application) {
        this.application = application;
    }


    public static synchronized NetworkManager getInstance(Application application) {
        if (instance == null) {
            instance = new NetworkManager(application);
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            application.registerReceiver(instance, filter);
        }
        return instance;
    }

    public void setCurrentActivity(Activity activity) {
        this.currentActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isConnected(context)) {
            showNoInternetDialog(isConnect -> {

            });
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface OnConnectedListener {
        void Connect(boolean isConnect);
    }

    public void showNoInternetDialog(OnConnectedListener onConnectedListener) {
        if (currentActivity != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (dialog == null || !dialog.isShowing()) {

                            if (ReelsAdapter.Companion.getInstance() != null) {
                                ReelsAdapter.Companion.getInstance().pauseAllPlayers();
                            }

                            dialog = new Dialog(currentActivity, R.style.CustomDialog95Percent);
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                            Window window = dialog.getWindow();
                            if (window != null) {
                                window.setGravity(Gravity.CENTER);
                                window.setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            DialogNoInternetBinding dialogNoInternetBinding = DialogNoInternetBinding.inflate(currentActivity.getLayoutInflater());
                            dialog.setContentView(dialogNoInternetBinding.getRoot());

                            dialogNoInternetBinding.btnRetry.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (isConnected(currentActivity)) {
                                        onConnectedListener.Connect(true);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                            }
                                        }, 1000);
                                    }
                                }
                            });
                            dialog.show();
                        }

                    } catch (Exception e) {
                    }
                }
            });
        }
    }


    private Dialog dialog2;

    public void showServerErrorDialog(OnConnectedListener onConnectedListener) {
        if (currentActivity != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (dialog2 == null || !dialog2.isShowing()) {

                            if (ReelsAdapter.Companion.getInstance() != null) {
                                ReelsAdapter.Companion.getInstance().pauseAllPlayers();
                            }

                            dialog2 = new Dialog(currentActivity, R.style.CustomDialog95Percent);
                            dialog2.setCancelable(false);
                            dialog2.setCanceledOnTouchOutside(false);
                            Window window = dialog2.getWindow();
                            if (window != null) {
                                window.setGravity(Gravity.CENTER);
                                window.setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            DialogServerErrorBinding dialog2NoInternetBinding = DialogServerErrorBinding.inflate(currentActivity.getLayoutInflater());
                            dialog2.setContentView(dialog2NoInternetBinding.getRoot());

                            dialog2NoInternetBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (isConnected(currentActivity)) {
                                        onConnectedListener.Connect(true);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog2.dismiss();
                                            }
                                        }, 1000);
                                    }
                                }
                            });
                            dialog2.show();
                        }

                    } catch (Exception e) {
                    }
                }
            });
        }
    }

}
