package com.charley.screenlockapp.BroadcastReceiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

/**
 * Created by junchi on 9/13/2016.
 */
public class BluetoothReceiver extends BroadcastReceiver {
    BluetoothAdapter mBtAdapter;

    @Override
    public void onReceive(Context context, Intent intent) {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        final String action = intent.getAction();

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch(state) {
                case BluetoothAdapter.STATE_OFF:
                    if(! mBtAdapter.isEnabled())
                        new CountDownTimer(12*1000,1000){

                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                mBtAdapter.enable();
                            }
                        }.start();


                    break;
                case BluetoothAdapter.STATE_ON:
                    break;
                default:
                    break;
            }
        }
    }
}
