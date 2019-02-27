package com.example.testingbtvoice;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class SpeechResultAdapter extends RecyclerView.Adapter<SpeechResultAdapter.ResultsViewHolder> {
    ArrayList<String> results;

    public SpeechResultAdapter(ArrayList<String> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_result_list, viewGroup, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder resultsViewHolder, int i) {
        resultsViewHolder.textView.setText(results.get(i));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.resultTextItem);
        }
    }
}
