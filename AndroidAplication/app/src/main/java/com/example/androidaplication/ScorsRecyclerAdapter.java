package com.example.androidaplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ScorsRecyclerAdapter extends RecyclerView.Adapter<ScorsRecyclerAdapter.Holder> {

    private ArrayList<String> userNameList;
    private ArrayList<String> scorList;

    public ScorsRecyclerAdapter(ArrayList<String> userNameList, ArrayList<String> scorList) {
        this.userNameList = userNameList;
        this.scorList = scorList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.scor_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.userNameText.setText(userNameList.get(position));
        holder.scorText.setText(scorList.get(position));
    }

    @Override
    public int getItemCount() {
        return scorList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView userNameText, scorText;

        public Holder(@NonNull View itemView) {
            super(itemView);

            userNameText = itemView.findViewById(R.id.scorItemUsername);
            scorText = itemView.findViewById(R.id.scorItemScor);

        }
    }
}
