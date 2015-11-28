package com.iotaconcepts.aurum;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iotaconcepts.aurum.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeMap;


public class OneFrangment extends Fragment
{
    public OneFrangment() {} //Default constructor



    public TreeMap<Integer,String> sym=new TreeMap<Integer,String>(); //Stores the symptoms for various diseases

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myInflatedView = inflater.inflate(R.layout.fragment_one, container,false);
        //Code for parsing the SYMPTOMS file and storing index,Symptom ins TreeMap sym
        TextView tv = (TextView)myInflatedView.findViewById(R.id.textview);
        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(getActivity().getAssets().open("symp.txt")));
            String parse1=br.readLine(); //Reads 'SYMPTOMS' from the file
            tv.setText("");
            while((parse1=br.readLine())!=null)
            {
                String[] separateCntandSymptom=parse1.split("\t");
                Integer num= Integer.parseInt(separateCntandSymptom[0]);
                String Symptom=separateCntandSymptom[1];
                sym.put(num,Symptom);
                tv.append(num+" "+sym.get(num)+"\n");
            }
        }
        catch(Exception e)
        {
            tv.append("SORRY\n");
            Toast.makeText(getActivity(),"SORRY!", Toast.LENGTH_LONG).show();
        }
        //Done parsing Symptoms






        // Inflate the layout for this fragment
        return myInflatedView;
    }





}