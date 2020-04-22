package com.projekti.bonappe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projekti.bonappe.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText inputEmail, inputPassword;
    private ProgressDialog loadingBar;
    private Button btnRegjistrohu, btnLogin, btnReset;
    private TextView tvForgotPassword;

    public static String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText)findViewById(R.id.et_Email);
        inputPassword = (EditText)findViewById(R.id.et_Fjalekalimi);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegjistrohu=(Button)findViewById(R.id.btnRegjistrohu);
        // progressBar = (ProgressBar)findViewById(R.id.progressBar);
           firebaseAuth = FirebaseAuth.getInstance();
           tvForgotPassword=(TextView)findViewById(R.id.tvForgotPassword);
           loadingBar=new ProgressDialog(this);
      /*  if (null != firebaseAuth.getCurrentUser())
        {
           startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
        }*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnRegjistrohu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                regjistrohu();
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });

    }

    private void regjistrohu()
    {
        //Intent intent=new Intent(this,RegisterActivity.class);
        //startActivity(intent);
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
    private void login() {

        String email = inputEmail.getText().toString().trim();
         String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
          loadingBar.show();
       firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            loadingBar.dismiss();
                          //  Toast.makeText(MainActivity.this, "U loguat me sukses", Toast.LENGTH_SHORT).show();

                            if(firebaseAuth.getCurrentUser().isEmailVerified())
                            {
                                Toast.makeText(MainActivity.this, "U loguat me sukses", Toast.LENGTH_SHORT).show();
                                FirebaseUser  c_user=FirebaseAuth.getInstance().getCurrentUser();
                                UserInformation.userID=userID=c_user.getUid();





                               // FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                                //DatabaseReference DatabaseRef;


UserInformation.email=c_user.getEmail();




                             //   DatabaseRef.child("mbiemri").setValue(et_Mbiemri.getText().toString());
                               // DatabaseRef.child("telefoni").setValue(et_Telefoni.getText().toString());

                                //DatabaseRef.child("image").setValue(imageUrl);











                                // Log.d("blu3",c_user.getPhotoUrl().toString());
                                startActivity(new Intent(MainActivity.this,RestaurantActivity.class));
                                finish();


                            }else{
                                Toast.makeText(MainActivity.this, "Ju lutem verifikoni emailin", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(MainActivity.this, "Login failed or user does not exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /*@Override
    public void onClick(View view)
{
    if(view==btnLogin)
    {
        login();
    }
    //if(view==btnRegjistrohu)
       // regjistrohu();
}*/

}

