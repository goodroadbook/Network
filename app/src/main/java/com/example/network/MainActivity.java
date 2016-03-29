package com.example.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(false == isConnected())
        {
            Toast.makeText(this, "네트워크가 연결되지 않았습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        switch(getNetworkType())
        {
            case ConnectivityManager.TYPE_WIFI:
                // 대용량 데이터를 받는 경우 과금이 발생하지 않는 WiFi를 권장한다.
                HttpMgrThread httpThread = new HttpMgrThread();
                //httpThread.start();
                httpThread.reqHttp();
                break;
            case ConnectivityManager.TYPE_MOBILE:
                // 대용량 데이터를 받는 경우 사용자에게 동의 여부를 묻는 팝업 창을 제공하는 경우도 있다.
                break;
            default:
                break;
        }

        Toast.makeText(this, getNetworkTypeName() + "에 연결되어 있습니다.", Toast.LENGTH_LONG).show();

    }

    private String getNetworkTypeName()
    {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork.getTypeName();
    }

    private int getNetworkType()
    {
        /*
        ConnectivityManager.TYPE_MOBILE         // 3G or 4G 기타 등등, 과긂 발생
        ConnectivityManager.TYPE_WIFI           // Wi-Fi 무료로 사용 가능
        */
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork.getType();
    }

    private boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
