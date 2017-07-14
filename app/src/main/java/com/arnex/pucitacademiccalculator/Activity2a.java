package com.arnex.pucitacademiccalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Activity2a extends AppCompatActivity {

    boolean backPressedTwiceToExit = false;

    LinearLayout parentLayout;
    RelativeLayout.LayoutParams rlp;
    ArrayList<Integer> subjectIdList, marksList, crdHrsList;
    ArrayList<Integer> hrsArray;
    int no_of_Subjects = 0;
    Button discButton, addButton, calculateButton;
    TextView gpaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2a);
        init();
    }

    private void init(){
        this.setTitle("Calculate Semester GPA");
        parentLayout = (LinearLayout) findViewById(R.id.subjects);
        gpaText = (TextView) findViewById(R.id.gpa);

        subjectIdList = new ArrayList<>();
        marksList = new ArrayList<>();
        crdHrsList = new ArrayList<>();

        discButton = (Button) findViewById(R.id.subSubject);
        addButton = (Button) findViewById(R.id.addSubject);
        calculateButton = (Button) findViewById(R.id.calculateButtonAct1);

        onclickListener(discButton);
        onclickListener(addButton);
        onclickListener(calculateButton);

        hrsArray = new ArrayList<>();
        hrsArray.add(1);
        hrsArray.add(2);
        hrsArray.add(3);

        rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        addSubjectInLayout(createSubjectField());

    }

    private RelativeLayout createSubjectField() {

        RelativeLayout subjectBar = new RelativeLayout(this);
        int x = View.generateViewId();
        subjectBar.setId(x);
        subjectIdList.add(x);

        x = View.generateViewId();
        marksList.add(x);

        EditText editText = new EditText(this);
        editText.setId(x);

        x = View.generateViewId();
        crdHrsList.add(x);

        Spinner spinner = new Spinner(this);
        spinner.setId(x);
        ArrayAdapter<Integer> spinnerArray = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, hrsArray);

        editText.setHint("Marks out of 100");
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        spinner.setAdapter(spinnerArray);
        spinner.setLayoutParams(rlp);
        subjectBar.addView(editText);
        subjectBar.addView(spinner);

        return subjectBar;

    }

    private void addSubjectInLayout(RelativeLayout subject){
        parentLayout.addView(subject, no_of_Subjects);
        no_of_Subjects++;
    }

    private void onclickListener(Button button){

        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                onButtonClick(view.getId());
            }

        });

    }

    private void onButtonClick(int id){


        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity2a.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if(id == R.id.addSubject) {
            addSubjectInLayout(createSubjectField());
        }else if(id == R.id.calculateButtonAct1){
            doCalculations();
        }else if(id == R.id.subSubject){
            if(no_of_Subjects > 1) {
                no_of_Subjects--;
                parentLayout.removeViewAt(no_of_Subjects);
                crdHrsList.remove(no_of_Subjects);
                marksList.remove(no_of_Subjects);
                subjectIdList.remove(no_of_Subjects);
            }else{
                Toast.makeText(this, "There must be at least 1 subject in list", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void doCalculations(){


        ArrayList<Integer> marks = new ArrayList<>(), crHrs = new ArrayList<>();
        boolean flag = true;
        EditText temp;
        Spinner tempSpinner;
        String tempString;
        int m;

        for(int i = 0 ; i < no_of_Subjects && flag; i++){

            temp = (EditText) findViewById(marksList.get(i));
            tempString = temp.getText().toString();

            if (tempString.length() > 0) {

                m = Integer.parseInt(tempString);

                if (m < 0 || m > 100) {
                    flag = false;
                    Toast.makeText(this, "Marks in subject no. " + (i + 1) + " are wrong.", Toast.LENGTH_LONG).show();
                } else {
                    marks.add(m);

                    tempSpinner = (Spinner) findViewById(crdHrsList.get(i));
                    tempString = tempSpinner.getSelectedItem().toString();
                    m = Integer.parseInt(tempString);
                    crHrs.add(m);
                }

            }else{
                flag = false;
                Toast.makeText(this, "Kindly fill all fields", Toast.LENGTH_LONG).show();
            }

        }

        if(flag){
            double gpa = GPACalculator.calculateGPA(marks, crHrs);
            gpaText.setText(Double.toString(gpa));
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