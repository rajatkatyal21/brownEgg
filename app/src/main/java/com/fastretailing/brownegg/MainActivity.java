package com.fastretailing.brownegg;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    int maxTime = 180;
    SeekBar seekBar;
    boolean counterIsActive = false;
    Button button;
    CountDownTimer countDownTimer;

    public String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public void start(View view) {
        if (counterIsActive) {
            counterIsActive = false;
            button = (Button) findViewById(R.id.startButton);
            button.setText("START");
            countDownTimer.cancel();
            textView.setText("03:00");
            seekBar.setProgress(180);
            seekBar.setEnabled(true);
        } else{
            counterIsActive = true;
            seekBar.setEnabled(false);
            button = (Button) findViewById(R.id.startButton);
            button.setText("STOP");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(final long millisUntilFinished) {
                    updateTime(millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.applause);
                    mediaPlayer.start();
                    counterIsActive = false;
                    button = (Button) findViewById(R.id.startButton);
                    button.setText("START");
                    countDownTimer.cancel();
                    textView.setText("03:00");
                    seekBar.setProgress(180);
                    seekBar.setEnabled(true);
                }
            };
            countDownTimer.start();
        }

    }

    private void updateTime(long second) {
        textView = (TextView) findViewById(R.id.timerTextView);
        long seconds = second  % 60;
        long minutes = (second % 3600) / 60;

        Log.i("Time Left", twoDigitString(minutes) + ":" + twoDigitString(seconds));
        textView.setText(twoDigitString(minutes) + ":" + twoDigitString(seconds));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        seekBar.setMax(maxTime);
        seekBar.setProgress(maxTime);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxTime = progress;
                updateTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
