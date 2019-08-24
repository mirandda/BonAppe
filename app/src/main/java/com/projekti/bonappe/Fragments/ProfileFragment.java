package com.projekti.bonappe.Fragments;


import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projekti.bonappe.Adapters.ReservationAdapter;
import com.projekti.bonappe.ListItems.Reservation;
import com.projekti.bonappe.MainActivity;
import com.projekti.bonappe.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private TextView  textView2,textView3,textView4;
   private ImageView iv_profilePic;
    private DatabaseReference databaseRef;
    private FirebaseUser user;
    private String currentRestaurantID;
    private FirebaseAuth mAuth;
    String uid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        user=FirebaseAuth.getInstance().getCurrentUser();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        textView2=(TextView)getView().findViewById(R.id.textView2);
        textView3=(TextView)getView().findViewById(R.id.textView3);
        textView4=(TextView)getView().findViewById(R.id.textView4);

        String emri=textView2.getText().toString();
        String mbiemri=textView3.getText().toString();
        String email=textView4.getText().toString();

        ListView reservationsList=getView().findViewById(R.id.lv_reservimet);
        ArrayList<Reservation> reservationsArrL=new ArrayList();
        ReservationAdapter reservationAdapter=new ReservationAdapter(reservationsArrL);
        //for(int i =0;i<10;i++)
           // reservationsArrL.add(new Reservation("hey","i"," just","met you","yolml"));
        //Log.d("blu3:",String.valueOf(reservationsList==null));
        //reservationsList.setAdapter(reservationAdapter);



    }

    private void RetreiveUserInfo()
    {

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        databaseRef.child("users").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("emri") && (dataSnapshot.hasChild("mbiemri"))))
                        {
                            String emri = dataSnapshot.child("emri").getValue().toString();
                            String mbiemri = dataSnapshot.child("mbiemri").getValue().toString();

                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                            String telefoni=dataSnapshot.child("telefoni").getValue().toString();


                            //userName.setText(retrieveUserName);
                            //userStatus.setText(retrievesStatus);
                            Picasso.get().load(retrieveProfileImage).centerCrop().fit().into(iv_profilePic);
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            //String retrievesStatus = dataSnapshot.child("status").getValue().toString();

                            //userName.setText(retrieveUserName);
                            //userStatus.setText(retrievesStatus);
                        }
                        else
                        {
                            //userName.setVisibility(View.VISIBLE);
                            //Toast.makeText(SettingsActivity.this, "Please set & update your profile information...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
