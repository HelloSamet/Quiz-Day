package com.example.androidaplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AllWords extends AppCompatActivity {

    private FirebaseFirestore mFireStore;
    private ArrayList<String> meaningsList;
    private ArrayList<String> wordsList;
    private ArrayList<String> idList;
    private AllWordsRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_words);

        mFireStore = FirebaseFirestore.getInstance();


        meaningsList = new ArrayList<>();
        wordsList = new ArrayList<>();
        idList = new ArrayList<>();

        getDataFromFirestore();


        RecyclerView recyclerView = findViewById(R.id.allWords);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new AllWordsRecyclerAdapter(meaningsList, wordsList, idList,"all");
        recyclerView.setAdapter(recyclerAdapter);

    }

    public void getDataFromFirestore() {
        mFireStore.collection("Words and Meanings").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    meaningsList.clear();
                    wordsList.clear();
                    idList.clear();

                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        String meaning = snapshot.getData().get("Meanings").toString();
                        String word = snapshot.getData().get("Words").toString();
                        String id1 = snapshot.getId();

                        meaningsList.add(meaning);
                        wordsList.add(word);
                        idList.add(id1);

                        recyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}