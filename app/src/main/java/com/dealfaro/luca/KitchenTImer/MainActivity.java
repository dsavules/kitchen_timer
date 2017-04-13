package com.dealfaro.luca.KitchenTImer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final private String LOG_TAG = "test2017app1";

    // Counter for the number of seconds.
    private int seconds = 0;

    //my_counter is the index of the 3 distinct timer values - it can get values 1,2, 3,
    // and than resets to one
    //stop_flag receives value 1 if stop button was pressed otherwise stays zero;
    // when timer finishes stop_flag resets to zero
    private int my_counter,stop_flag = 0;
    //flag to keep track if either plus or minus button were pressed
    //this flag resets to zero if stop was pressed or timer finishes
    private int plus_minus_flag=0;
    private int b1_time, b2_time, b3_time =0;

    // Countdown timer.
    private CountDownTimer timer = null;

    // One second.  We use Mickey Mouse time.
    private static final int ONE_SECOND_IN_MILLIS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTime();
    }

    public void onClickPlus(View v) {
        seconds += 60;
        //time_plus = System.currentTimeMillis();
        plus_minus_flag=1;
        displayTime();
    }

    public void onClickMinus(View v) {
        seconds = Math.max(0, seconds - 60);
        //time_minus = System.currentTimeMillis();
        plus_minus_flag=1;
        displayTime();

    }

    public void onClickStop(View v) {
        //stop button was clikced, so we set the flag to 1
        stop_flag=1;
        plus_minus_flag=0;
        cancelTimer();
        displayTime();
    }

    public void onClickButton1(View v) {
        //seconds is set to the value held by button 1
        seconds = b1_time;
        displayTime();
        onClickStart(null);
    }

    public void onClickButton2(View v) {
        //seconds is set to the value held by button 2
        seconds = b2_time;
        displayTime();
        onClickStart(null);
    }

    public void onClickButton3(View v) {
        //seconds is set to the value held by button 3
        seconds = b3_time;
        displayTime();
        onClickStart(null);
    }


    public void onReset(View v) {
        seconds = 0;
        //reset stop_flag
        stop_flag=0;
        plus_minus_flag=0;
        cancelTimer();
        displayTime();
    }

    public void onClickStart(View v) {
        if (seconds == 0) {
            stop_flag = 0;
            cancelTimer();

        }
        if (timer == null) {

            //if stop button was not pressed (stop_flag is zero) and if the current seconds
            //value is different than any of the values held by the 3 buttons
            // we increase the index "my_counter" with 1
            //My counter will increase up to 3 and each time the corresponding button will receive
            //the new distinct timer value; Once the 3rd button value is set, my_counter will
            //reset to 1, because now it is time to reuse/replace all over again button 1, 2, 3..

            //if (stop_flag == 0) {
            if (stop_flag == 0 || plus_minus_flag == 1) {
                int m, s;
                String new_display, b1_display, b2_display, b3_display;
                //TextView myvalue1=(TextView)findViewById(R.id.button1);

                Button myval1 = (Button) findViewById(R.id.button1);
                //get value displayed by button1
                b1_display = myval1.getText().toString();
                Button myval2 = (Button) findViewById(R.id.button2);
                //get value displayed by button2
                b2_display = myval2.getText().toString();
                Button myval3 = (Button) findViewById(R.id.button3);
                //get value displayed by button3
                b3_display = myval3.getText().toString();
                m = seconds / 60;
                s = seconds % 60;
                new_display = String.format("%d:%02d", m, s);

                //check if new display is different than any of the 3 button values
                if (!new_display.equals(b1_display) && !new_display.equals(b2_display) && !new_display.equals(b3_display)) {
                    if (my_counter < 3) {
                        my_counter = my_counter + 1;
                    } else {
                        my_counter = 1;
                    }

                    switch (my_counter) {
                        case 1:
                            myval1.setText(new_display);
                            //save the actual seconds value for button1
                            b1_time=seconds;
                            break;
                        case 2:
                            myval2.setText(new_display);
                            //save the actual seconds value for button2
                            b2_time=seconds;
                            break;
                        case 3:
                            myval3.setText(new_display);
                            //save the actual seconds value for button3
                            b3_time=seconds;
                            break;

                    }

                }

            }
            // We create a new timer.
            timer = new CountDownTimer(seconds * ONE_SECOND_IN_MILLIS, ONE_SECOND_IN_MILLIS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(LOG_TAG, "Tick at " + millisUntilFinished + "stop flag" + stop_flag);
                    seconds = Math.max(0, seconds - 1);
                    displayTime();
                }

                @Override
                public void onFinish() {
                    seconds = 0;
                    //timer finished, so we reset the stop_flag to zero
                    stop_flag = 0;
                    plus_minus_flag=0;
                    timer = null;
                    displayTime();
                }
            };
            timer.start();
        }

    }



    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Updates the time display.
    private void displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);
        TextView v = (TextView) findViewById(R.id.display);
        int m = seconds / 60;
        int s = seconds % 60;
        v.setText(String.format("%d:%02d", m, s));
        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button) findViewById(R.id.button_start);
        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }


}
