package com.example.sketcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

public class Tester extends Fragment {

    public Activity containerActivity;
    DrawingView dv = null;

    public Tester() {
    }

    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    public DrawingView getDrawingView(){
        return dv;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Fragment created for tester");
        View view = inflater.inflate(R.layout.test, container, false);

        return view;
    }
}
