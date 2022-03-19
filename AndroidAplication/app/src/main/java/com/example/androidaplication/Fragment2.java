package com.example.androidaplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class Fragment2 extends Fragment {

    private EditText editWords,editMeanings;
    private String wordsTxt,meaningsTxt;
    private FirebaseFirestore mFirestore;
    private HashMap<String,String> mData;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStatement){

        View view=inflater.inflate(R.layout.fragmet2_layout,container,false);

        editWords=(EditText) view.findViewById(R.id.wordActivity_word);
        editMeanings=(EditText) view.findViewById(R.id.wordActivity_meaning);
        Button save = view.findViewById(R.id.DatayiKaydet);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirestore=FirebaseFirestore.getInstance();
                wordsTxt=editWords.getText().toString();
                meaningsTxt=editMeanings.getText().toString();

                if (!TextUtils.isEmpty(wordsTxt) && !TextUtils.isEmpty(meaningsTxt)){
                    mData=new HashMap<>();
                    mData.put("Words",wordsTxt);
                    mData.put("Meanings",meaningsTxt);
                    mFirestore.collection("Words and Meanings")
                            .add(mData)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getActivity(),"Word successfully added.",Toast.LENGTH_SHORT).show();
                                        editWords.setText("");
                                        editMeanings.setText("");
                                    }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"Word didn't successfully added.",Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else{
                    Toast.makeText(getContext(),"Words and Meanings cannot be left blank",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  view;
    }
}
