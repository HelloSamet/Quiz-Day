package com.example.androidaplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment4 extends Fragment {
    private Button play;
    private RecyclerView scors;
    private FirebaseFirestore firebaseFirestore;
    private ScorsRecyclerAdapter scorsRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> userNameList;
    private ArrayList<String> scorList;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStatement){
        View view=inflater.inflate(R.layout.fragmet4_layout,container,false);

        play=view.findViewById(R.id.play);
        scors = view.findViewById(R.id.scors);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userNameList = new ArrayList<>();
        scorList = new ArrayList<>();

        getDataFromFirestore();

        scors.setLayoutManager(new LinearLayoutManager(view.getContext()));
        scorsRecyclerAdapter = new ScorsRecyclerAdapter(userNameList,scorList);
        scors.setAdapter(scorsRecyclerAdapter);

        play.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(),QuizActivty.class);
            startActivity(intent);
        });
        return view ;
    }
    public void getDataFromFirestore(){
        firebaseFirestore.collection("Scors").orderBy("Scor", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    userNameList.clear();
                    scorList.clear();
                    for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                        String email = documentSnapshot.getData().get("Email").toString();
                        int scor = documentSnapshot.getLong("Scor").intValue();
                        String scorString = String.valueOf(scor);

                        firebaseFirestore.collection("Kullanicilar").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    String username = task.getResult().getData().get("KullaniciAdi").toString();

                                    if (!userNameList.contains(username)){
                                        userNameList.add(username);
                                        scorList.add(scorString);
                                    }

                                    //userNameList.add(username);
                                    //scorList.add(scorString);

                                    scorsRecyclerAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
