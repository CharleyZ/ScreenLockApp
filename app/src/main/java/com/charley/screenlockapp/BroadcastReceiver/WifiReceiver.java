package com.charley.screenlockapp.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;

/**
 * Created by junchi on 9/13/2016.
 */
public class WifiReceiver  extends BroadcastReceiver {
    WifiManager wifiManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        final String action = intent.getAction();

        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            final int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
            switch(state) {
                case WifiManager.WIFI_STATE_DISABLED:
                    if (! wifiManager.isWifiEnabled()){
                        new CountDownTimer(12*1000,1000){

                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                wifiManager.setWifiEnabled(true);
                            }
                        }.start();

                    }
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    break;
                default:
                    break;

            }

        }
    }
}
