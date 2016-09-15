package com.charley.screenlockapp.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.charley.screenlockapp.R;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    Button btOffBtn;
    Button btOnBtn;
    Button wifiOffBtn;
    Button wifiOnBtn;
    Button startSecBtn;
    BluetoothAdapter mBtAdapter;
    WifiManager wifiManager;
    static Handler mHandler;
    int numToExit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        btOnBtn = (Button) findViewById(R.id.btOnBtn);
        btOnBtn.setOnClickListener(this);

        btOffBtn = (Button) findViewById(R.id.btOffBtn);
        btOffBtn.setOnClickListener(this);

        wifiOffBtn = (Button) findViewById(R.id.wifiOffBtn);
        wifiOffBtn.setOnClickListener(this);

        wifiOnBtn = (Button) findViewById(R.id.wifiOnBtn);
        wifiOnBtn.setOnClickListener(this);

        startSecBtn = (Button) findViewById(R.id.startSecond);
        startSecBtn.setOnClickListener(this);

        setHandler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btOnBtn:
                if (! wifiManager.isWifiEnabled()) {
                    mBtAdapter.enable();
                }
                break;
            case R.id.btOffBtn:
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }
                break;
            case R.id.wifiOnBtn:
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                }
                break;
            case R.id.wifiOffBtn:
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }
                break;
            case R.id.startSecond:
                Intent myIntent = new Intent(MainActivity.this, SecondActivity.class);
                MainActivity.this.startActivity(myIntent);
                break;

        }

    }





    private void setHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                mHandler.post(handlerToexit);
            }

        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mHandler.removeCallbacks(handlerToexit);
            float X = event.getX();
            float Y = event.getY();
            Log.d("Point",Float.toString(X)+" "+Float.toString(Y));
            if(X<80 && Y<250 ){
                numToExit++;
                Log.d("Count",Integer.toString(numToExit));
                if(numToExit >= 4){
                    numToExit = 0;
                    showDialog();
                }
                else
                    mHandler.postDelayed(handlerToexit,3000);
            }


        }
        return true;
    }

    final Runnable handlerToexit = new Runnable() {
        @Override
        public void run() {
            numToExit = 0;
        }
    };


    public void showDialog(){
        AlertDialog.Builder exitDialog = new AlertDialog.Builder(
                MainActivity.this);
        exitDialog.setMessage("Do you want to exit?");
        exitDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

        exitDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        exitDialog.show();
    }


}

