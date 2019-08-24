package com.projekti.bonappe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projekti.bonappe.Adapters.ReservationAdapter;
import com.projekti.bonappe.Adapters.SectionsPagerAdapter;
import com.projekti.bonappe.ListItems.Reservation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView textView2,textView3,textView4,textView37,textView41,textView42;
    private ImageView iv_profilePic;
    private DatabaseReference databaseRef;
    private FirebaseUser user;
    private String currentRestaurantID;
    private FirebaseAuth mAuth;
    private StorageReference storageRef;
    private ListView lv_reservimet;
    ProgressDialog loadingBar;
    //String uid;
    public static String userId,userEmail;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FloatingActionButton fab = findViewById(R.id.fab);

fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(ProfileActivity.this,RestaurantActivity.class));
    }
});



        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        storageRef=FirebaseStorage.getInstance().getReference().child("users");
        user=FirebaseAuth.getInstance().getCurrentUser();

        lv_reservimet=findViewById(R.id.lv_reservimet);

        textView2=(TextView)findViewById(R.id.textView2);
        textView3=(TextView)findViewById(R.id.textView3);
        textView4=(TextView)findViewById(R.id.textView4);
        textView37=(TextView)findViewById(R.id.textView37);
        iv_profilePic=findViewById(R.id.iv_profilePic);
        textView41=(TextView) findViewById(R.id.textView41);
        textView42=(TextView) findViewById(R.id.textView42);

        textView41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivateAccount();

            }
        });
        textView42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });



         loadingBar=new ProgressDialog(this);
         loadingBar.show();
        UserInformation.DatabaseRef=FirebaseDatabase.getInstance().getReference("users").child(UserInformation.userID);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                // Post post = dataSnapshot.getValue(Post.class);
                UserInformation.mbiemri=(String)dataSnapshot.child("mbiemri").getValue();
                UserInformation.emri=(String)dataSnapshot.child("emri").getValue();
                UserInformation.telefoni=(String)dataSnapshot.child("telefoni").getValue();
                UserInformation.photoUrl=(String)dataSnapshot.child("image").getValue();



                Log.d("blu8",UserInformation.email);
                Log.d("blu8",UserInformation.photoUrl);
                Log.d("blu8",UserInformation.emri);
                Log.d("blu8",UserInformation.telefoni);
                Log.d("blu8",UserInformation.userID);

                Picasso.get().load(UserInformation.photoUrl).centerCrop().fit().into(iv_profilePic);

                textView2.setText(UserInformation.emri);
                textView3.setText(UserInformation.mbiemri);
                textView4.setText(UserInformation.email);
                textView37.setText(UserInformation.telefoni);

loadingBar.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("blu3 --", "loadPost:onCancelled", databaseError.toException());
                // ...
            }

        };
        UserInformation.DatabaseRef.addValueEventListener(postListener);










        //RetreiveUserInfo();

        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Rezervimet");
        //storageRef=FirebaseStorage.getInstance().getReference().child("rezervimet");

        final ArrayList<Reservation> listaRezervimet=new ArrayList<>();
       final ReservationAdapter reservationAdapter=new ReservationAdapter(listaRezervimet);
        lv_reservimet.setAdapter(reservationAdapter);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reservation kk=null;
                listaRezervimet.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if (postSnapshot.child("userId").getValue().toString().equals(UserInformation.userID))//--
                        try{
                            listaRezervimet.add(
                                    kk = new Reservation(
                                            postSnapshot.child("ora").getValue().toString(),
                                            postSnapshot.child("data").getValue().toString(),
                                            postSnapshot.child("nrPersonave").getValue().toString(),
                            postSnapshot.child("rasti").getValue().toString())
                            );}catch(NullPointerException npu){Log.d("blu11_2",String.valueOf(npu));}
                        }

                Collections.reverse(listaRezervimet);
                reservationAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("blu7", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });




    }


    private void deactivateAccount()
    {
        AlertDialog.Builder dialog=new AlertDialog.Builder(ProfileActivity.this);
        dialog.setTitle("A jeni i sigurte ");
        dialog.setMessage("Fshirja e kesaj llogarie konsiston ne fshirjen e te gjitha te dhenave tuaja nga sistemi " +
                "dhe ju nuk mund te kyçeni ne kete llogari.");
        dialog.setPositiveButton("Fshije", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //loadingBar.show();
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //loadingBar.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ProfileActivity.this,"Llogaria juaj eshte fshire",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.setNegativeButton("Anulo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog= dialog.create();
        alertDialog.show();
        //startActivity(new Intent(ProfileActivity.this,MainActivity.class));
    }



















    private void RetreiveUserInfo()
    {
       /* FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    userId = firebaseUser.getUid();
                      userEmail = firebaseUser.getEmail();
                }
            }
        };*/

        databaseRef.child("users").child(MainActivity.userID)
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

                            textView2.setText(emri);
                            textView3.setText(mbiemri);
                            textView4.setText(telefoni);


                            //Glide.with(this).load("").into(iv_profilePic);
                            //userName.setText(retrieveUserName);
                            //userStatus.setText(retrievesStatus);
                            //Picasso.get().load(retrieveProfileImage).centerCrop().fit().into(iv_profilePic);
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