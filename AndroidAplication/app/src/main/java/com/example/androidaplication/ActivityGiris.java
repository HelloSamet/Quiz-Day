package com.example.androidaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityGiris extends AppCompatActivity {

    private EditText editEmail,editSifre;
    private String  emailTxt,sifreTxt;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_activity);

        editEmail=(EditText)findViewById(R.id.giris_editEmail);
        editSifre=(EditText) findViewById(R.id.giris_editSifre);



        mAuth=FirebaseAuth.getInstance();

        openHomePage();
    }

    public void GirisYap(View view){
        emailTxt=editEmail.getText().toString();
        sifreTxt=editSifre.getText().toString();

        if (!TextUtils.isEmpty(emailTxt) && !TextUtils.isEmpty(sifreTxt)){
            mAuth.signInWithEmailAndPassword(emailTxt,sifreTxt)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(ActivityGiris.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ActivityGiris.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }else{

            Toast.makeText(this,"Email ve Şifre Boş Olamaz...",Toast.LENGTH_SHORT).show();
        }

    }

    public void KayitOl(View view){
        Intent intent = new Intent(ActivityGiris.this, ActivityKayit.class);
        startActivity(intent);

    }

    public void openHomePage() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(ActivityGiris.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        System.exit(0);
    }
}