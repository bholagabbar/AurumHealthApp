package com.iotaconcepts.aurum;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iotaconcepts.aurum.OneFrangment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Diagnosis extends Activity
{

    TreeMap<Integer,String> symptoms=new TreeMap<Integer,String>();
    TreeMap<String,TreeSet<Integer>> diseasesMappedtoSymptoms=new TreeMap<String, TreeSet<Integer>>();
    int[] userSymptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosis);
        String[] userSymptomsString=getIntent().getExtras().getString("userSymptoms").split(",");
        userSymptoms=new int[userSymptomsString.length];
        int cnt=0;
        for(String i:userSymptomsString)
            userSymptoms[cnt++]=Integer.parseInt(i);
        TextView tv=(TextView)findViewById(R.id.dtextview);
        ParseSymptoms();
        ParseDiseases();
        ArrayList<String> finalProblableDiseases=getProbableDiseases();

        for(String x:finalProblableDiseases) //Printing probable diseases. Add features mentioned above
            tv.append(x+"\n\n");

        //Got everything

        /*
        TO DO: Add the label: 'Symptoms entered' and extract from 'userSymptoms' array and print

        Then add the label 'probable disease' and print the data from 'finalProbableDiseases' as below

        Stylize this shit

        add button send to doctor which is empty

        Have fun. I'm getting the fuck outta this fragment. Nuff done
         */

    }

    //mapping diseases to Symptoms
    public void ParseDiseases()
    {
        TextView tv=(TextView)findViewById(R.id.dtextview);
        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(getAssets().open("dis.txt")));
            String parse1=br.readLine(); //Reads 'SYMPTOMS' from the file
            parse1=br.readLine();
            while((parse1=br.readLine())!=null)
            {
                String[] now= parse1.split("\t");
                String[] sympforthis=now[2].split(", ");
                TreeSet<Integer> ts=new TreeSet<Integer>();
                for(String i:sympforthis)
                    ts.add(Integer.parseInt(i));
                diseasesMappedtoSymptoms.put(now[1], ts);
                //tv.setText(now[1]+" "+diseasesMappedtoSymptoms.get(now[1])+"\n");
            }
        }
        catch(Exception e)
        {
            tv.append("Sorry\n");
        }
    }

    //Parsing Symptoms again. I don't know shared preferences :(
    public void ParseSymptoms()
    {
        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(getAssets().open("symp.txt")));
            String parse1=br.readLine(); //Reads 'SYMPTOMS' from the file
            while((parse1=br.readLine())!=null)
            {
                String[] separateCntandSymptom=parse1.split("\t");
                Integer num= Integer.parseInt(separateCntandSymptom[0]);
                String Symptom=separateCntandSymptom[1];
                symptoms.put(num, Symptom);
            }
        }
        catch(Exception e)
        {
            //tv.append("Sorry\n");
        }
    }

    public ArrayList<String> getProbableDiseases() //Algorithm to get diseases
    {

        ArrayList<String> finalDiseases=new ArrayList<>();
        class Node //Pair
        {
            String f;
            int s;
            Node(String f, int s)
            {
                this.f=f;
                this.s=s;
            }
        }
        HashSet<Integer> hs=new HashSet<Integer>();
        for(Integer s:userSymptoms)
            hs.add(s);
        TreeSet<Node> ts=new TreeSet<Node>(new Comparator<Node>() //Stores Diseaess in descending order
        {
            @Override
            public int compare(Node o1, Node o2)
            {
                return o2.s-o1.s;
            }
        });
        for(String dis:diseasesMappedtoSymptoms.keySet())
        {
            int cnt=0;//Count for diseases
            for(int i:diseasesMappedtoSymptoms.get(dis))
                if(hs.contains(i)) //If user symptoms contains this
                    cnt++;
            if(cnt>=1)
                ts.add(new Node(dis,cnt));
        }
        int cnt=0;
        while(cnt<5 && !ts.isEmpty()) //Max 5 diseases
        {
            finalDiseases.add(ts.pollFirst().f);
            cnt++;
        }
        return finalDiseases;
    }



}
