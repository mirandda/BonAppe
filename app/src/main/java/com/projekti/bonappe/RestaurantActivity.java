package com.projekti.bonappe;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.projekti.bonappe.Adapters.RestaurantSectionsPagerAdapter;
import com.projekti.bonappe.Fragments.SearchFragment;
import com.squareup.picasso.Picasso;

public class RestaurantActivity extends AppCompatActivity {
    private Uri uri;
    private ImageView imgView;
    private DatabaseReference databaseRef;
    private String currentRestaurantID;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    //private DatabaseReference RootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        RestaurantSectionsPagerAdapter restaurantSectionsPagerAdapter = new RestaurantSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(restaurantSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        imgView=(ImageView)findViewById(R.id.imageView6);

        //Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mybonappeproject.appspot.com/o/images%2Fbon.jpg?alt=media&token=c535f407-b5d8-4f85-b7c1-76619f18371d").into(imgView);

        mAuth = FirebaseAuth.getInstance();
       // currentRestaurantID = mAuth.getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference().child("restaurants");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        RetrieveRestaurantInfo();
    }

    private void RetrieveRestaurantInfo()
    {
        //SearchFragment sf=new SearchFragment();
        //databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.child("Restaurant").child("Swiss Diamonds")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("emri") && (dataSnapshot.hasChild("image"))))
                        {
                            String emri = dataSnapshot.child("emri").getValue().toString();
                            String lokacioni = dataSnapshot.child("adresa").getValue().toString();
                            String qyteti = dataSnapshot.child("qyteti").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                            //String orari=dataSnapshot.child("orari").getValue().toString();


                            Picasso.get().load(retrieveProfileImage).centerCrop().fit().into(imgView);
                            //Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mybonappeproject.appspot.com/o/images%2Fbon.jpg?alt=media&token=c535f407-b5d8-4f85-b7c1-76619f18371d").into(imgView);
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("emri")))
                        {
                            String retrieveUserName = dataSnapshot.child("emri").getValue().toString();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}