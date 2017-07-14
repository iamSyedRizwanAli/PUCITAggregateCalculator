package com.arnex.pucitacademiccalculator;

import java.util.ArrayList;

/**
 * Created by Rizwan on 04-Feb-17.
 */
public class GPACalculator {

    public static double calculateGPA(ArrayList<Integer> marks, ArrayList<Integer> crHours){

        int subjects = marks.size();
        int totHours = 0;
        double totSubjGPS = 0;


        for(int i = 0 ; i < subjects ; i++){
            totHours += crHours.get(i);

            totSubjGPS += ( returnGradePoints(marks.get(i)) * crHours.get(i) );
        }
        totSubjGPS /= totHours;

        totSubjGPS = (Math.round(totSubjGPS * 100));
        totSubjGPS /= 100;

        return totSubjGPS;
    }

    private static double returnGradePoints(int marks){

        double gradePoints = 0;

        if(marks >= 0 && marks < 50){
            gradePoints = 0;
        }else if(marks >= 50 && marks < 55){
            gradePoints = 1.0;
        }else if(marks >= 55 && marks < 58){
            gradePoints = 1.70;
        }else if(marks >= 58 && marks <= 60){
            gradePoints = 2.0;
        }else if(marks > 60 && marks < 65){
            gradePoints = 2.30;
        }else if(marks >= 65 && marks < 70){
            gradePoints = 2.70;
        }else if(marks >= 70 && marks < 75){
            gradePoints = 3.0;
        }else if(marks >= 75 && marks < 80){
            gradePoints = 3.30;
        }else if(marks >= 80 && marks < 85){
            gradePoints = 3.70;
        }else if(marks >= 85 && marks <= 100){
            gradePoints = 4.0;
        }

        return gradePoints;
    }

    public static double calculateCGPAbySemester(ArrayList<Double> cgpa, ArrayList<Integer> crHours){

        int subjects = cgpa.size();
        double cGpa = 0;
        int totcrhour = 0;

        for(int i = 0 ; i < subjects ; i++){

            cGpa += cgpa.get(i) * crHours.get(i);
            totcrhour += crHours.get(i);

        }

        cGpa /= totcrhour;

        cGpa = Math.round(cGpa * 100);
        cGpa /= 100;

        return cGpa;
    }

    public static double calculateCGPAbyCGPA(double thisSemCGPA, int thisSemCrHrs, double prevCGPA, int prevCrHrs){
        double cgpa = 0;

        int temp = thisSemCrHrs;
        cgpa += thisSemCGPA * thisSemCrHrs;

        cgpa += prevCGPA * prevCrHrs;

        temp += prevCrHrs;

        cgpa /= temp;

        cgpa = Math.round(cgpa * 100);
        cgpa /= 100;

        return cgpa;
    }

}
