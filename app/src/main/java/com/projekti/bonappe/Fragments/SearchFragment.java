package com.projekti.bonappe.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.projekti.bonappe.R;
import com.projekti.bonappe.RestaurantActivity;

public class SearchFragment extends Fragment {

   EditText editText9,lokacioni;
   Button btnSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText9=(EditText)getView().findViewById(R.id.editText9);
       lokacioni=(EditText)getView().findViewById(R.id.lokacioni);
       btnSearch=(Button)getView().findViewById(R.id.button4);

       btnSearch.setOnClickListener( new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               Intent intent = new Intent(getActivity(), RestaurantActivity.class);
               startActivity(intent);
           }
       });

    }
    String search=editText9.getText().toString();
    String vendi=lokacioni.getText().toString();


    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getVendi() {
        return vendi;
    }

    public void setVendi(String vendi) {
        this.vendi = vendi;
    }

    public SearchFragment() {
    }
    }