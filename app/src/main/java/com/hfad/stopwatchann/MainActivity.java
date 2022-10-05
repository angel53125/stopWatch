package com.hfad.stopwatchann;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    Chronometer stopwatch;
    private boolean running = false;
    private long offset = 0;

    public static final String OFFSET_KEY = "offset";
    public static final String RUNNING_KEY = "running";
    public static final String BASE_KEY = "base";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatch = findViewById(R.id.chrStopwatch);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnPause = (Button) findViewById(R.id.btnPause);
        Button btnReset = (Button) findViewById(R.id.btnReset);

        if(savedInstanceState != null)
        {
            offset = savedInstanceState.getLong(OFFSET_KEY);
            running = savedInstanceState.getBoolean(RUNNING_KEY);

            if(running)
            {
                stopwatch.setBase(savedInstanceState.getLong(BASE_KEY));
                stopwatch.start();
            }
            else
            {
                setBaseTime();
            }
        }
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running)
                {
                    setBaseTime();
                    stopwatch.start();
                    running = true;

                    System.out.println("Start:");
                    System.out.println("\t running: " + running);
                    System.out.println("\toffset: " + offset);
                    System.out.println("\tbase: " + stopwatch.getBase());
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running)
                {
                    saveOffset();
                    stopwatch.stop();
                    running = false;

                    System.out.println("Pause:");
                    System.out.println("\t running: " + running);
                    System.out.println("\toffset: " + offset);
                    System.out.println("\tbase: " + stopwatch.getBase());
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset = 0;
                setBaseTime();

                System.out.println("Reset:");
                System.out.println("\t running: " + running);
                System.out.println("\toffset: " + offset);
                System.out.println("\tbase: " + stopwatch.getBase());
            }
        });

    }

    public void setBaseTime()
    {
        stopwatch.setBase(SystemClock.elapsedRealtime() - offset);
    }

    public void saveOffset()
    {
        offset = SystemClock.elapsedRealtime() - stopwatch.getBase();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putLong(OFFSET_KEY,offset);
        savedInstanceState.putBoolean(RUNNING_KEY,running);
        savedInstanceState.putLong(BASE_KEY,stopwatch.getBase());
    }

}