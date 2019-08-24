package com.projekti.bonappe;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener{

  private static final int PICK_IMAGE_REQUEST=234;
 private EditText et_Emri,et_Mbiemri,et_Fjalekalimi,et_KonfirmoFjalekalimin,et_Telefoni,et_Email;
 private Button btn_Regjistrohu,btnUpload,btnChoose;
 private static ImageView imageView;
 private Uri imageUri;
 private ProgressDialog loadingBar;
 private StorageReference storageReference;
 private DatabaseReference DatabaseRef;
 private StorageTask UploadTask;
 private FirebaseAuth firebaseAuth;
 private FirebaseDatabase firebaseDatabase;
 public String imageUrl=null;

    @Override
    protected void onStart()
    {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         firebaseAuth=FirebaseAuth.getInstance();
        et_Emri=(EditText)findViewById(R.id.et_Emri);
        et_Mbiemri=(EditText)findViewById(R.id.et_Mbiemri);
        et_Fjalekalimi=(EditText)findViewById(R.id.et_Fjalekalimi);
        et_KonfirmoFjalekalimin=(EditText)findViewById(R.id.et_KonfirmoFjalekalimin);
        et_Telefoni=(EditText)findViewById(R.id.et_Telefoni);
        et_Email=(EditText)findViewById(R.id.et_Email);
        storageReference= FirebaseStorage.getInstance().getReference();
        btn_Regjistrohu=(Button)findViewById(R.id.btn_Regjistrohu);
        btn_Regjistrohu.setOnClickListener(this);
        imageView=(ImageView)findViewById(R.id.imageView);
        loadingBar=new ProgressDialog(this);

         firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseRef=FirebaseDatabase.getInstance().getReference();

        //folder=FirebaseStorage.getInstance().getReference().child("image");





            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    openFileChooser();
                }
            });
        }



    public void registerUser()
    {
      String emri=et_Emri.getText().toString().trim();
      String mbiemri=et_Mbiemri.getText().toString().trim();
        String  fjalekalimi=et_Fjalekalimi.getText().toString().trim();
        String konfirmoFjalekalimin=et_KonfirmoFjalekalimin.getText().toString().trim();
        String email=et_Email.getText().toString().trim();
        String telefoni=et_Telefoni.getText().toString().trim();

        boolean hasUpper=fjalekalimi.matches(".*[A-Z].*");
        boolean hasLower=fjalekalimi.matches(".*[a-z].*");
        boolean hasNumber=fjalekalimi.matches(".*\\d.*");
        boolean hasSpecial=fjalekalimi.matches(".*[!@#$%&*].*");




        //DatabaseRef.child("users").setValue(userInformation);
        // DatabaseRef.getParent().setValue(userInformation);
        if(TextUtils.isEmpty(emri))
        {
            Toast.makeText(this,"Ju lutem shenoni emrin",Toast.LENGTH_LONG).show();
        }

        if(TextUtils.isEmpty(mbiemri))
        {
            Toast.makeText(this,"Ju lutem shenoni mbiemrin",Toast.LENGTH_LONG).show();
        }

        if(TextUtils.isEmpty(telefoni))
        {
            Toast.makeText(this,"Ju lutem shenoni telefonin",Toast.LENGTH_LONG).show();
        }

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Ju lutem shenoni emailin",Toast.LENGTH_LONG).show();
        }
        loadingBar.show();

        if(fjalekalimi.length()>=8 & fjalekalimi.length()<15 && hasUpper && hasLower && hasNumber && hasSpecial) {
            if (fjalekalimi.equals(konfirmoFjalekalimin)) {
                //Toast.makeText(this,"Fjalekalimi  perputhet",Toast.LENGTH_LONG).show();
                //mProgressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email, fjalekalimi)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //mProgressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    uploadFile();
                                    //loadingBar.dismiss();

                                   // Toast.makeText(RegisterActivity.this, "Jeni regjistruar me sukses", Toast.LENGTH_LONG).show();
                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                /*FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                                String uid = current_user.getUid();
                                                String child=et_Emri.getText().toString();
                                                DatabaseRef=firebaseDatabase.getReference("users").child(uid);
                                                DatabaseRef.child("emri").setValue(et_Emri.getText().toString());
                                                DatabaseRef.child("mbiemri").setValue(et_Mbiemri.getText().toString());
                                                DatabaseRef.child("telefoni").setValue(et_Telefoni.getText().toString());

                                                DatabaseRef.child("image").setValue(imageUrl);

                                                Toast.makeText(RegisterActivity.this, "Jeni regjistruar me sukses.Ju lutem shikoni emailin per verifikim", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                et_Emri.setText("");
                                                et_Email.setText("");
                                                et_Fjalekalimi.setText("");
                                                et_KonfirmoFjalekalimin.setText("");
                                                et_Mbiemri.setText("");
                                                et_Telefoni.setText("");*/
                                            }else{
                                                Toast.makeText(RegisterActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Nuk jeni regjistruar me sukses ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(RegisterActivity.this, "Konfirmimi i fjalekalimit nuk perputhet", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {


            et_Fjalekalimi.setError("Password must be at least 8 characters with upper,lower  and special characters");
                                                  /*Toast.makeText(Main4Activity.this, "Password must be at least 6 characters ", Toast.LENGTH_LONG).show();
                                                  Log.i("app_log","Password must be at least ")*/
        }
            //Toast.makeText(RegisterActivity.this,"Fjalekalimi duhet te jete me i madh se 8 karaktere",Toast.LENGTH_SHORT).show();


    }
    @Override
    public void onClick(View view)
    {
        if(view==btn_Regjistrohu) {
            registerUser();
        }
    }

    private void openFileChooser()
    {
       Intent intent=new Intent();
       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
Log.d("blu4","filechoose");
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
           imageUri=data.getData();

                   try{
                       Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                       imageView.setImageBitmap(bitmap);
                   }catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        }

    private void uploadFile() {
        if (imageUri != null) {
              final StorageReference riversRef=storageReference.child("images/"+et_Emri.getText().toString().trim()+".jpg");

              riversRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            //progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(),"File uploaded",Toast.LENGTH_SHORT).show();
                            /*Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                            firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                     String url = uri.toString();
                                     DatabaseRef.child()
                                    //Log.e("TAG:", "the url is: " + url);

                                    //String ref = yourStorageReference.getName();
                                    //Log.e("TAG:", "the ref is: " + ref);
                                }
                            });*/

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                        }
                    }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imageUrl=downloadUri.toString();

                        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = current_user.getUid();
                        String child=et_Emri.getText().toString();
                        DatabaseRef=firebaseDatabase.getReference("users").child(uid);
                        DatabaseRef.child("emri").setValue(et_Emri.getText().toString());
                        DatabaseRef.child("mbiemri").setValue(et_Mbiemri.getText().toString());
                        DatabaseRef.child("telefoni").setValue(et_Telefoni.getText().toString());

                        DatabaseRef.child("image").setValue(imageUrl);

                        Toast.makeText(RegisterActivity.this, "Jeni regjistruar me sukses.Ju lutem shikoni emailin per verifikim", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        et_Emri.setText("");
                        et_Email.setText("");
                        et_Fjalekalimi.setText("");
                        et_KonfirmoFjalekalimin.setText("");
                        et_Mbiemri.setText("");
                        et_Telefoni.setText("");

                        loadingBar.dismiss();


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });;
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

}
}

