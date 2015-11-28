package com.iotaconcepts.aurum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iotaconcepts.aurum.R;


public class TwoFragment extends Fragment
{
    // Required empty public constructor
    public TwoFragment()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_three, container, false);
    }





}