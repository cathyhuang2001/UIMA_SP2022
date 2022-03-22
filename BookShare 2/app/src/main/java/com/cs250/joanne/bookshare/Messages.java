package com.cs250.joanne.bookshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;


public class Messages extends Fragment {
    private MainActivity myact;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.messages, container, false);
        myact = (MainActivity) getActivity();
        assert myact != null;
        myact.getSupportActionBar().setTitle("Messages");

        return view;
    }

}

