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

public class Activity2b extends AppCompatActivity {

    boolean backPressedTwiceToExit = false;

    LinearLayout parentLayout;
    TextView cgpaText;
    ArrayList<Integer> semesterIdList, cgpaList, crdHrsList;
    Button discButton, addButton, calculateButton;
    ArrayList<Integer> hrsArray;
    RelativeLayout.LayoutParams rlp;
    int no_of_Subjects = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2b);
        this.setTitle("Calculate CGPA by Semesters");
        init();
    }


    private void init(){
        parentLayout = (LinearLayout) findViewById(R.id.semesters);
        cgpaText = (TextView) findViewById(R.id.cgpa1);

        semesterIdList = new ArrayList<>();
        cgpaList = new ArrayList<>();
        crdHrsList = new ArrayList<>();

        discButton = (Button) findViewById(R.id.subSemester);
        addButton = (Button) findViewById(R.id.addSemester);
        calculateButton = (Button) findViewById(R.id.calculateButtonAct2);

        onclickListener(discButton);
        onclickListener(addButton);
        onclickListener(calculateButton);

        hrsArray = new ArrayList<>();
        for(int i = 1 ; i < 24 ; i++)
            hrsArray.add(i);

        rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        addSubjectInLayout(createSubjectField());

    }

    private RelativeLayout createSubjectField() {

        RelativeLayout semesterBar = new RelativeLayout(this);
        int x = View.generateViewId();
        semesterBar.setId(x);
        semesterIdList.add(x);

        x = View.generateViewId();
        cgpaList.add(x);

        EditText editText = new EditText(this);
        editText.setId(x);

        x = View.generateViewId();
        crdHrsList.add(x);

        Spinner spinner = new Spinner(this);
        spinner.setId(x);
        ArrayAdapter<Integer> spinnerArray = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, hrsArray);

        editText.setHint("Semester " + (no_of_Subjects + 1) + " GPA");
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        spinner.setAdapter(spinnerArray);
        spinner.setLayoutParams(rlp);
        semesterBar.addView(editText);
        semesterBar.addView(spinner);

        return semesterBar;

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
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity2b.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if(id == R.id.addSemester) {
            if(no_of_Subjects <= 9) {
                addSubjectInLayout(createSubjectField());
            }else{
                Toast.makeText(this, "There can be at most 10 semesters", Toast.LENGTH_LONG).show();
            }
        }else if(id == R.id.calculateButtonAct2){
            doCalculations();
        }else if(id == R.id.subSemester){
            if(no_of_Subjects > 1) {
                no_of_Subjects--;
                parentLayout.removeViewAt(no_of_Subjects);
                crdHrsList.remove(no_of_Subjects);
                cgpaList.remove(no_of_Subjects);
                semesterIdList.remove(no_of_Subjects);
            }else{
                Toast.makeText(this, "There must be at least 1 semester in list", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void doCalculations(){

        ArrayList<Double> gpa = new ArrayList<>();
        ArrayList<Integer> crHrs = new ArrayList<>();
        boolean flag = true;
        EditText temp;
        Spinner tempSpinner;
        String tempString;
        int m;
        double c;

        for(int i = 0 ; i < no_of_Subjects && flag; i++){

            temp = (EditText) findViewById(cgpaList.get(i));
            tempString = temp.getText().toString();

            if(tempString.length() == 0){
                flag = false;
                Toast.makeText(this, "Kindly fill all fields", Toast.LENGTH_LONG).show();
            }else {

                c = Double.parseDouble(tempString);

                if (c < 0 || c > 4) {
                    flag = false;
                    Toast.makeText(this, "GPA of semester " + (i + 1) + " is wrong.", Toast.LENGTH_LONG).show();
                } else {
                    gpa.add(c);

                    tempSpinner = (Spinner) findViewById(crdHrsList.get(i));
                    tempString = tempSpinner.getSelectedItem().toString();
                    m = Integer.parseInt(tempString);
                    crHrs.add(m);
                }
            }

        }

        if(flag){
            double cgpa = GPACalculator.calculateCGPAbySemester(gpa, crHrs);
            cgpaText.setText(Double.toString(cgpa));
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