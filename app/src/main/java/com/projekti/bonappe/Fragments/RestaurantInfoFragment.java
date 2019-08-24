package com.projekti.bonappe.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.RadioAccessSpecifier;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projekti.bonappe.MainActivity;
import com.projekti.bonappe.R;
import com.projekti.bonappe.RestaurantActivity;
import com.projekti.bonappe.UserInformation;
import com.projekti.bonappe.ui.main.PageViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class RestaurantInfoFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private EditText et_Personat, Data, Ora,Rasti;
    private Button btn_Rezervo;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference DatabaseRef;

    public static RestaurantInfoFragment newInstance(int index) {
        RestaurantInfoFragment fragment = new RestaurantInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurant_info, container, false);

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data = (EditText) getView().findViewById(R.id.Data);
        et_Personat = (EditText) getView().findViewById(R.id.et_Personat);
        Ora = (EditText) getView().findViewById(R.id.Ora);
        btn_Rezervo = (Button) getView().findViewById(R.id.btn_Rezervo);

Rasti=(EditText) getView().findViewById(R.id.rasti);

        //firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseRef=FirebaseDatabase.getInstance().getReference("Rezervimet");

        btn_Rezervo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reservations();

            }
        });

    }

    public void Reservations()
    {
        String data=Data.getText().toString();
        String nr_Personave=et_Personat.getText().toString();
        String ora=Ora.getText().toString();
       // FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = current_user.getUid();
        //DatabaseRef=firebaseDatabase.getReference("Rezervimet").child(MainActivity.userID);
        if(TextUtils.isEmpty(data)) {
            Toast.makeText(getActivity(), "Duhet te shenohet data", Toast.LENGTH_SHORT).show();
        }
         if (TextUtils.isEmpty(nr_Personave)) {
            Toast.makeText(getActivity(), "Duhet te caktohet numri i personave", Toast.LENGTH_SHORT).show();
        }
         if(TextUtils.isEmpty(data))
        {
            Toast.makeText(getActivity(), "Duhet te shenohet ora", Toast.LENGTH_SHORT).show();
        }
            String id = DatabaseRef.push().getKey();
        DatabaseRef.child(id).child("userId").setValue(UserInformation.userID);
            DatabaseRef.child(id).child("nrPersonave").setValue(et_Personat.getText().toString());
            DatabaseRef.child(id).child("data").setValue(Ora.getText().toString());
            DatabaseRef.child(id).child("ora").setValue(Data.getText().toString());
        DatabaseRef.child(id).child("rasti").setValue(Rasti.getText().toString());
            Toast.makeText(getActivity(), "Rezervimi u krye me sukses", Toast.LENGTH_SHORT).show();
        et_Personat.setText("");
        Ora.setText("");
        Data.setText("");
Rasti.setText("");
    }
}