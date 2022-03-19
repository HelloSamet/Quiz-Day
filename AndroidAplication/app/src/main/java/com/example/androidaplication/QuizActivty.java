package com.example.androidaplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class QuizActivty extends AppCompatActivity {
    private TextView qustionText, answer1Text, answer2Text, answer3Text, answer4Text, trueNumberText, falseNumberText;
    private CardView answer1Card, answer2Card, answer3Card, answer4Card;
    private FirebaseFirestore mFireStore;
    private FirebaseAuth mAuth;
    private ArrayList<String> meaningsList;
    private ArrayList<String> wordsList;
    private Random rndQuestion, rndTrueAnswer;
    private String eMail;
    private int rndQuestionNumber, rndTrueAnswerNumber, trueSize = 0, falseSize = 0, questionSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_activty);

        qustionText = findViewById(R.id.question);
        answer1Text = findViewById(R.id.answer1);
        answer2Text = findViewById(R.id.answer2);
        answer3Text = findViewById(R.id.answer3);
        answer4Text = findViewById(R.id.answer4);
        trueNumberText = findViewById(R.id.trueNumber);
        falseNumberText = findViewById(R.id.falseNumber);
        answer1Card = findViewById(R.id.answer1Card);
        answer2Card = findViewById(R.id.answer2Card);
        answer3Card = findViewById(R.id.answer3Card);
        answer4Card = findViewById(R.id.answer4Card);

        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        eMail = mAuth.getCurrentUser().getEmail();

        meaningsList = new ArrayList<>();
        wordsList = new ArrayList<>();
        rndQuestion = new Random();
        rndTrueAnswer = new Random();

        getDataFromFirestore();


    }

    public void getDataFromFirestore() {

        mFireStore.collection("Words and Meanings").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        String meaning = snapshot.getData().get("Meanings").toString();
                        String word = snapshot.getData().get("Words").toString();

                        meaningsList.add(meaning);
                        wordsList.add(word);
                    }
                    Print(meaningsList, wordsList);
                }
            }
        });
    }

    public void Print(ArrayList<String> meaningsListPrint, ArrayList<String> wordsListPrint) {
        rndQuestionNumber = rndQuestion.nextInt(meaningsListPrint.size());
        rndTrueAnswerNumber = rndTrueAnswer.nextInt(4);

        qustionText.setText(meaningsListPrint.get(rndQuestionNumber));

        if (questionSize < 20) {

            if (rndTrueAnswerNumber == 0) {
                if (rndQuestionNumber + 3 < meaningsList.size()) {
                    answer1Text.setText(wordsListPrint.get(rndQuestionNumber));
                    answer2Text.setText(wordsListPrint.get(rndQuestionNumber + 1));
                    answer3Text.setText(wordsListPrint.get(rndQuestionNumber + 2));
                    answer4Text.setText(wordsListPrint.get(rndQuestionNumber + 3));
                } else {
                    answer1Text.setText(wordsListPrint.get(rndQuestionNumber));
                    answer2Text.setText(wordsListPrint.get(rndQuestionNumber - 1));
                    answer3Text.setText(wordsListPrint.get(rndQuestionNumber - 2));
                    answer4Text.setText(wordsListPrint.get(rndQuestionNumber - 3));
                }
                answer1Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    TrueSet(meaningsListPrint, wordsListPrint);
                });
                answer2Card.setOnClickListener(V -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
                answer3Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
                answer4Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
            } else if (rndTrueAnswerNumber == 1) {
                if (rndQuestionNumber + 3 < meaningsList.size()) {
                    answer2Text.setText(wordsListPrint.get(rndQuestionNumber));
                    answer1Text.setText(wordsListPrint.get(rndQuestionNumber + 1));
                    answer3Text.setText(wordsListPrint.get(rndQuestionNumber + 2));
                    answer4Text.setText(wordsListPrint.get(rndQuestionNumber + 3));
                } else {
                    answer2Text.setText(wordsListPrint.get(rndQuestionNumber));
                    answer1Text.setText(wordsListPrint.get(rndQuestionNumber - 1));
                    answer3Text.setText(wordsListPrint.get(rndQuestionNumber - 2));
                    answer4Text.setText(wordsListPrint.get(rndQuestionNumber - 3));
                }
                answer1Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
                answer2Card.setOnClickListener(V -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    TrueSet(meaningsListPrint, wordsListPrint);
                });
                answer3Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
                answer4Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
            } else if (rndTrueAnswerNumber == 2) {
                if (rndQuestionNumber + 3 < meaningsList.size()) {
                    answer3Text.setText(wordsListPrint.get(rndQuestionNumber));
                    answer2Text.setText(wordsListPrint.get(rndQuestionNumber + 1));
                    answer1Text.setText(wordsListPrint.get(rndQuestionNumber + 2));
                    answer4Text.setText(wordsListPrint.get(rndQuestionNumber + 3));
                } else {
                    answer3Text.setText(wordsListPrint.get(rndQuestionNumber));
                    answer2Text.setText(wordsListPrint.get(rndQuestionNumber - 1));
                    answer1Text.setText(wordsListPrint.get(rndQuestionNumber - 2));
                    answer4Text.setText(wordsListPrint.get(rndQuestionNumber - 3));
                }
                answer1Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
                answer2Card.setOnClickListener(V -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
                answer3Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    TrueSet(meaningsListPrint, wordsListPrint);
                });
                answer4Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
            } else if (rndTrueAnswerNumber == 3) {
                if (rndQuestionNumber + 3 < meaningsList.size()) {
                    answer4Text.setText(wordsListPrint.get(rndQuestionNumber));
                    answer2Text.setText(wordsListPrint.get(rndQuestionNumber + 1));
                    answer3Text.setText(wordsListPrint.get(rndQuestionNumber + 2));
                    answer1Text.setText(wordsListPrint.get(rndQuestionNumber + 3));
                } else {
                    answer4Text.setText(wordsListPrint.get(rndQuestionNumber));
                    answer2Text.setText(wordsListPrint.get(rndQuestionNumber - 1));
                    answer3Text.setText(wordsListPrint.get(rndQuestionNumber - 2));
                    answer1Text.setText(wordsListPrint.get(rndQuestionNumber - 3));
                }
                answer1Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
                answer2Card.setOnClickListener(V -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
                answer3Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    FalseSet(meaningsListPrint, wordsListPrint);
                });
                answer4Card.setOnClickListener(v -> {
                    meaningsListPrint.remove(rndQuestionNumber);
                    wordsListPrint.remove(rndQuestionNumber);
                    TrueSet(meaningsListPrint, wordsListPrint);
                });
            }
            questionSize += 1;
        }else {
            HashMap<String, Object> data = new HashMap<>();
            data.put("Scor",(trueSize-(falseSize/4))*100);
            data.put("Email", eMail);

            mFireStore.collection("Scors").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getApplicationContext(),"Quiz is the finish",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuizActivty.this, HomeActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void TrueSet(ArrayList<String> meaningsListTrue, ArrayList<String> wordsListTrue) {
        trueSize += 1;
        trueNumberText.setText(String.valueOf(trueSize));
        Print(meaningsListTrue, wordsListTrue);
    }

    public void FalseSet(ArrayList<String> meaningsListFalse, ArrayList<String> wordsListFalse) {
        falseSize += 1;
        falseNumberText.setText(String.valueOf(falseSize));
        Print(meaningsListFalse, wordsListFalse);
    }
}