package com.example.calcount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DiaryExAdapter extends RecyclerView.Adapter<DiaryExAdapter.DiaryExViewHolder>{
    private List<Exercise> diaryExList = new ArrayList<>();
    private ButtonListener buttonListener;

    public DiaryExAdapter (ButtonListener buttonListener) { this.buttonListener = buttonListener;}

    @NonNull
    @Override
    public DiaryExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_diary_entry, parent, false);
        return new DiaryExAdapter.DiaryExViewHolder(itemView, buttonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryExViewHolder holder, int position) {
        Exercise e = diaryExList.get(position);
        holder.textExName.setText(e.getName());
        holder.textExCals.setText("+" + e.getCalories() + "");
        holder.exercise = e;
    }

    @Override
    public int getItemCount() {
        return diaryExList.size();
    }

    public void setExercises(List<Exercise> diaryExList)
    {
        this.diaryExList = diaryExList;
        notifyDataSetChanged();
    }

    class DiaryExViewHolder extends RecyclerView.ViewHolder{
        private TextView textExName;
        private TextView textExCals;
        private Button removeExButton;
        Exercise exercise;
        ButtonListener buttonListener;

        public DiaryExViewHolder(@NonNull View itemView, ButtonListener buttonListener) {
            super(itemView);
            textExName = itemView.findViewById(R.id.exDiaryEntryName);
            textExCals = itemView.findViewById(R.id.exDiaryEntryCals);
            removeExButton = itemView.findViewById(R.id.removeExEntryButton);
            this.buttonListener = buttonListener;

            removeExButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonListener.onRemoveExClick(exercise);
                }
            });
        }
    }

    public interface ButtonListener{
        void onRemoveExClick(Exercise exercise);
    }
}
