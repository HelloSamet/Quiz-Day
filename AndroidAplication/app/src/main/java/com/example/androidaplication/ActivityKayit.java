package com.example.androidaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ActivityKayit extends AppCompatActivity {

    private EditText editEmail,editSifre,editIsim;
    private String  emailTxt,sifreTxt,isimTxt;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private HashMap<String,Object> mData;
    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        editIsim=(EditText)findViewById(R.id.kayit_editIsim);
        editEmail=(EditText)findViewById(R.id.kayit_editEmail);
        editSifre=(EditText) findViewById(R.id.kayit_editSifre);
    }

    public void KayitOl(View view){
        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        isimTxt=editIsim.getText().toString();
        emailTxt=editEmail.getText().toString();
        sifreTxt=editSifre.getText().toString();

        if (!TextUtils.isEmpty(isimTxt) && !TextUtils.isEmpty(emailTxt) && !TextUtils.isEmpty(sifreTxt)){
            mAuth.createUserWithEmailAndPassword(emailTxt,sifreTxt)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                mUser=mAuth.getCurrentUser();
                                mData=new HashMap<>();
                                mData.put("KullaniciAdi",isimTxt);

                                mFirestore.collection("Kullanicilar").document(mUser.getEmail())
                                        .set(mData)
                                        .addOnCompleteListener(ActivityKayit.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(ActivityKayit.this,"kayıt İşlemi başarılı",Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(ActivityKayit.this,ActivityGiris.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else{
                                                    Toast.makeText(ActivityKayit.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }else{
                                Toast.makeText(ActivityKayit.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(this,"Email ve Şifre Boş Olamaz...",Toast.LENGTH_SHORT).show();
        }
    }

}