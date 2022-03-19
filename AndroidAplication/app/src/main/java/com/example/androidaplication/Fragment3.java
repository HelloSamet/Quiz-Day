package com.example.androidaplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends Fragment {
    private Button favButton;
    private RecyclerView recyclerView;
    private FirebaseFirestore mFireStore;
    private FirebaseAuth mAuth;
    private ArrayList<String> meaningsList;
    private ArrayList<String> wordsList;
    private ArrayList<String> idList;
    private AllWordsRecyclerAdapter recyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStatement) {
        View view = inflater.inflate(R.layout.fragmet3_layout, container, false);

        favButton = view.findViewById(R.id.favButton);
        recyclerView = view.findViewById(R.id.favWord);
        mFireStore = FirebaseFirestore.getInstance();

        meaningsList = new ArrayList<>();
        wordsList = new ArrayList<>();
        idList = new ArrayList<>();


        favButton.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(),AllWords.class);
            startActivity(intent);
        });

        getDataFromFirestore();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerAdapter = new AllWordsRecyclerAdapter(meaningsList,wordsList,idList,"favs");
        recyclerView.setAdapter(recyclerAdapter);


        return view;
    }
    public void getDataFromFirestore(){
        mAuth = FirebaseAuth.getInstance();
        String eMail = mAuth.getCurrentUser().getEmail();

        mFireStore.collection(eMail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value !=null){
                    meaningsList.clear();
                    wordsList.clear();
                    idList.clear();


                    for (DocumentSnapshot snapshot: value.getDocuments()){
                        String id = snapshot.getId();

                        mFireStore.collection("Words and Meanings").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value !=null) {
                                    for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                                        String id2 = documentSnapshot.getId();
                                        if (id.equals(id2)) {
                                            String meaning = documentSnapshot.getData().get("Meanings").toString();
                                            String word = documentSnapshot.getData().get("Words").toString();

                                            meaningsList.add(meaning);
                                            wordsList.add(word);
                                            idList.add(id);

                                            recyclerAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}


