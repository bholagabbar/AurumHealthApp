package com.iotaconcepts.aurum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class OneFrangment extends Fragment
{
    //Default constructor
    public OneFrangment() {}

    //DS
    public TreeMap<Integer,String> sym=new TreeMap<Integer,String>(); //Stores the symptoms for various diseases\
    ArrayList<Product> symptomList = new ArrayList<Product>();
    ListAdapter boxAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void Parser(View myInflatedView)
    {
        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(getActivity().getAssets().open("symp.txt")));
            String parse1=br.readLine(); //Reads 'SYMPTOMS' from the file
            while((parse1=br.readLine())!=null)
            {
                String[] separateCntandSymptom=parse1.split("\t");
                Integer num= Integer.parseInt(separateCntandSymptom[0]);
                String Symptom=separateCntandSymptom[1];
                sym.put(num, Symptom);
            }
        }
        catch(Exception e)
        {
            //tv.append("SORRY\n");
            Toast.makeText(getActivity(),"SORRY!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View myInflatedView = inflater.inflate(R.layout.fragment_one, container, false);
        //Method for parsing Symptoms
        Parser(myInflatedView);
        //Now, basically set the data and stuff
        fillData();
        boxAdapter = new ListAdapter(getActivity(), symptomList);
        ListView lvMain = (ListView)myInflatedView.findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);
        // Inflate the layout for this fragment

        //Return the veiw. Standard procedure

        Button b1=(Button)myInflatedView.findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), Diagnosis.class);
                String numSymspace="";
                int cnt=0;
                for(int i:sym.keySet())
                    numSymspace+=Integer.toString(i)+","+sym.get(i)+",";
                numSymspace=numSymspace.substring(0,numSymspace.length()-1);
                String sendSymptoms=getResult(myInflatedView);
                String[] checkSymptomListSize;
                if(sendSymptoms.length()>0)
                    checkSymptomListSize=sendSymptoms.split(",");
                else
                    checkSymptomListSize=new String[0];
                if(checkSymptomListSize.length<3)
                {
                    Toast.makeText(getActivity(),"Please enter atleast 3 Symptoms!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    intent.putExtra("userSymptoms",sendSymptoms.trim());
                    startActivity(intent);
                }
            }
        });

        return myInflatedView;
    }

    void fillData()
    {
        for (int i:sym.keySet())
        {
            symptomList.add(new Product(i, sym.get(i)));
        }
    }

    public String getResult(View v)
    {
        String result = "";
        for (Product p : boxAdapter.getBox())
        {
            if (p.box)
            {
                for(int i:sym.keySet())
                    if(sym.get(i).equals(p.name))
                    {
                        result+=i+",";
                        break;
                    }
            }
        }
        if(result.length()>0)
            return result.substring(0,result.length()-1);
        else
            return "";
    }
}