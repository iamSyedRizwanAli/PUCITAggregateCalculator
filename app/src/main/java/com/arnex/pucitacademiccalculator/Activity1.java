package com.arnex.pucitacademiccalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Activity1 extends AppCompatActivity {

    Button [] bArray = new Button[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        init();
    }

    private void init(){

        bArray[0] =  (Button) findViewById(R.id.button1);
        bArray[1] =  (Button) findViewById(R.id.button2);
        bArray[2] =  (Button) findViewById(R.id.button3);

        setOnClick(bArray[0]);
        setOnClick(bArray[1]);
        setOnClick(bArray[2]);

    }

    private void setOnClick(Button button){

        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                initiateNextAct(view.getId());
            }

        });

    }

    private void initiateNextAct(int id){

        Intent i = null;

        if(id == R.id.button1){
           i = new Intent(this, Activity2a.class);
        }else if(id == R.id.button2){
            i = new Intent(this, Activity2b.class);
        }else{
            i = new Intent(this, Activity2c.class);
        }

        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        Activity1.super.onBackPressed();
    }
}
