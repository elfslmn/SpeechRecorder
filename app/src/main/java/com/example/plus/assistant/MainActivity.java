package com.example.plus.assistant;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    MediaRecorder mMediaRecorder;
    Button startButton, stopButton;
    TextView mtextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtextView = (TextView) findViewById(R.id.textView);
        startButton = (Button) findViewById(R.id.button_start);
        stopButton = (Button) findViewById(R.id.button_stop);
        stopButton.setClickable(false);

        initializeMediaRecorder();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                try {
                    mMediaRecorder.prepare();
                    mMediaRecorder.start();
                    mtextView.setText("Recording...");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
                mtextView.setText("Saved. Click start for a new record");

                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder=null;

                initializeMediaRecorder();
            }
        });

    }

    private void initializeMediaRecorder(){
        String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());

        outputFile +="/";
        outputFile += currentDateandTime;
        outputFile +=".3gp";

        Log.e("Name",outputFile);

        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mMediaRecorder.setOutputFile(outputFile);
    }


}
