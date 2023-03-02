package com.example.eldroid2app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bookingThread, cancelThread;
    private volatile boolean stopThreadFlag = false;

    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookingThread = findViewById(R.id.BookingBtn);
        cancelThread = findViewById(R.id.CancelBtn);

        bookingThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startThread(10);
            }
        });

        cancelThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopThread();
            }
        });
    }

    public void startThread(int seconds) {
        for (int i=0; i<seconds; i++){
            Log.d("THREAD ACTIVITY", "Start Thread : " + i );
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TookToLongRunnable runnable = new TookToLongRunnable(10);
        new Thread(runnable).start();

    }

    public void stopThread(){
        stopThreadFlag = true;
    }

    class TookToLongRunnable implements Runnable {
        int seconds;

        TookToLongRunnable(int seconds){
        this.seconds = seconds;
    }

        @Override
        public void run() {
            for (int i = 0; i<seconds; i++){
                if(stopThreadFlag){
                    return;
                }if(i == 5){
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"Riders are currently busy, it may take for a while.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Log.d("THREAD ACTIVITY", "Start Thread : " + i);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}