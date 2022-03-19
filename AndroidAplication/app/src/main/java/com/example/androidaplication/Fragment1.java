package com.example.androidaplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    private String stringFromTextView;
    private MultiAutoCompleteTextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStatement){
        View view=inflater.inflate(R.layout.fragmet1_layout,container,false);
        textView=(MultiAutoCompleteTextView) view.findViewById(R.id.multiAutoCompleteTextView);


        textView.setText("Hello there! Nice to see you! My name is Adam Smith."
               + "I am 17 and an student in high school.\n"
               + "I am planning to go college in New York.\n" +"My courses in high school are Biology, Spanish, and History."
                +" Spanish is the hardest course I have been taking until now.\n"
                +"It’s my second year in school now. I love my school."+
                "I live in a big house in Amsterdam.\n" +
                " It’s close to the my high school. I share my house with two different students.\n" +
                " Their names are Bill  and Paul. We love to help each other with schoolwork.\n " +
                "Toward the end of the week, we play football.\n" +
                "I have a more youthful brother. He just began secondary school." +
                " He is 13 and lives with my parents." +
                " They live on Acardan Street in Amsterdam." +
                "Sometimes they call me and visit me in Amsterdam." +
                " I am very when they visit. " +
                "My mom always brings me desserts and candy when they come." +
                " I miss them so much, as well!\n\n"+
                "************************************************\n"
                +"youthful --> having the qualities that are typical of young people.\n"+
                "hard --> not easy to bend, cut, or break.\n"+
                "dessert --> sweet food eaten at the end of a meal."

        );



        stringFromTextView=textView.getText().toString();


        return view;

    }



}
