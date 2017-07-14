package com.arnex.pucitacademiccalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Activity2c extends AppCompatActivity {

    boolean backPressedTwiceToExit = false;

    Button calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2c);
        this.setTitle("Calculate CGPA by previous CGPA");
        init();
    }

    private void init(){
        calculate = (Button) findViewById(R.id.calculateButtonAct3);

        calculate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                doCalculations();
            }
        });

    }

    private void doCalculations(){

        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity2c.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        double prevCGPA = 0, currGPA = 0;
        int prevCrHrs = 0, currCrHrs = 0;
        boolean flag = true;
        EditText temp = (EditText) findViewById(R.id.prevCGPAet);

        if(temp.getText().toString().length() == 0){
            Toast.makeText(this, "Kindly complete all fields", Toast.LENGTH_SHORT).show();
        }else {

            prevCGPA = Double.parseDouble(temp.getText().toString());

            if (prevCGPA < 0 || prevCGPA > 4) {
                flag = false;
                Toast.makeText(this, "Your previous CGPA is incorrect", Toast.LENGTH_LONG).show();
            } else {
                temp = (EditText) findViewById(R.id.prevCrHrsET);

                if(temp.getText().length() == 0){
                    Toast.makeText(this, "Kindly complete all fields", Toast.LENGTH_SHORT).show();
                }else {

                    prevCrHrs = Integer.parseInt(temp.getText().toString());
                    if (prevCrHrs < 0 || prevCrHrs > 130) {
                        flag = false;
                        Toast.makeText(this, "Your total previous credit hours are incorrect", Toast.LENGTH_LONG).show();
                    }
                }
            }

            if (flag) {

                temp = (EditText) findViewById(R.id.currGPAet);

                if(temp.getText().length() == 0){
                    Toast.makeText(this, "Kindly complete all fields", Toast.LENGTH_SHORT).show();
                }else {

                    currGPA = Double.parseDouble(temp.getText().toString());

                    if (currGPA < 0 || currGPA > 4) {
                        flag = false;
                        Toast.makeText(this, "Your current semester GPA is incorrect", Toast.LENGTH_LONG).show();
                    } else {
                        temp = (EditText) findViewById(R.id.currCrHrsET);

                        if(temp.getText().length() == 0){
                            Toast.makeText(this, "Kindly complete all fields", Toast.LENGTH_SHORT).show();
                        }else {
                            currCrHrs = Integer.parseInt(temp.getText().toString());

                            if (currCrHrs > 23 || currCrHrs <= 0) {
                                flag = false;
                                Toast.makeText(this, "Your current semester credit hours are incorrect", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }

            if (flag) {
                double cgpa = GPACalculator.calculateCGPAbyCGPA(currGPA, currCrHrs, prevCGPA, prevCrHrs);
                TextView tv = (TextView) findViewById(R.id.cgpa2);
                tv.setText(Double.toString(cgpa));
            }
        }

    }

    @Override
    public void onBackPressed() {

        if(backPressedTwiceToExit){
            super.onBackPressed();
            return;
        }else {

            backPressedTwiceToExit = true;
            Toast.makeText(this, "Press the back key again to return to main screen.", Toast.LENGTH_SHORT).show();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    backPressedTwiceToExit = false;
                }
            };

            Timer t = new Timer();
            t.schedule(task, 3000);

        }
    }

}
