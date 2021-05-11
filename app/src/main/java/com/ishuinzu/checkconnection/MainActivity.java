package com.ishuinzu.checkconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button btnCheckInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCheckInternet = findViewById(R.id.btnCheckInternet);

        btnCheckInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check Internet Connection
                checkConnection();
            }
        });
    }

    private void checkConnection() {
        boolean isConnected = false;
        boolean isWorking = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            isConnected = true;

            try {
                String command = "ping -c 1 google.com";
                if (Runtime.getRuntime().exec(command).waitFor() == 0) {
                    isWorking = true;
                } else {
                    isWorking = false;
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

        if (isConnected && isWorking) {
            Toast.makeText(this, "Connected & Working", Toast.LENGTH_SHORT).show();
        } else if (isConnected && !isWorking) {
            Toast.makeText(this, "Connected & Not Working", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Connected & Not Working", Toast.LENGTH_SHORT).show();
        }
    }
}