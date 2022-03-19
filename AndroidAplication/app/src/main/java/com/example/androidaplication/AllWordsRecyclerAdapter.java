package com.example.androidaplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AllWordsRecyclerAdapter extends RecyclerView.Adapter<AllWordsRecyclerAdapter.Holder> {

    private ArrayList<String> meaningsList;
    private ArrayList<String> wordsList;
    private ArrayList<String> idList;
    private String type;

    public AllWordsRecyclerAdapter(ArrayList<String> meaningsList, ArrayList<String> wordsList,ArrayList<String> idList,String type) {
        this.meaningsList = meaningsList;
        this.wordsList = wordsList;
        this.idList = idList;
        this.type = type;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_words, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String id = idList.get(position);
        String meaningTextContent = meaningsList.get(position);
        String wordTextContent = wordsList.get(position);

        if(type == "favs"){
            holder.star.setImageResource(R.drawable.fav2);
        }

        holder.meaningText.setText(meaningTextContent);
        holder.wordText.setText(wordTextContent);
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = holder.mAuth.getCurrentUser().getEmail();
                if (type == "all") {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("favorite", true);

                    holder.mFirestore.collection(userEmail).document(id).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(v.getContext(), "Favoriye eklendi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    if (meaningsList.size() == 1){
                        Intent refresh = new Intent(v.getContext(), HomeActivity.class);
                        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        v.getContext().startActivity(refresh);
                    }
                    holder.mFirestore.collection(userEmail).document(id).delete();
                    Toast.makeText(v.getContext(),"Favoriden kaldırıldı",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return meaningsList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView meaningText, wordText;
        private ImageView star;
        private FirebaseFirestore mFirestore;
        private FirebaseAuth mAuth;

        public Holder(@NonNull View itemView) {
            super(itemView);

            meaningText = itemView.findViewById(R.id.recycler_meaning);
            wordText = itemView.findViewById(R.id.recycler_word);
            star = itemView.findViewById(R.id.star);
            mAuth = FirebaseAuth.getInstance();
            mFirestore = FirebaseFirestore.getInstance();

        }
    }
}
