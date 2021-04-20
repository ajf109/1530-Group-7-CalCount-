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

//used to populate the recyclerview in AddExerciseActivity
public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.ExerciseViewHolder>{
    private List<Exercise> exercises = new ArrayList<>();
    private ButtonListener buttonListener;

    //takes a buttonListener as an argument, which will be the AddExerciseActivity itself
    public AddExerciseAdapter(ButtonListener buttonListener)
    {
        this.buttonListener = buttonListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_entry, parent, false);
        return new ExerciseViewHolder(itemView, buttonListener);
    }

    //displays the required text for each exercise entry
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise e = exercises.get(position);
        holder.textExName.setText(e.getName());
        holder.textExCals.setText("" + e.getCalories() + "");
        holder.exercise = e;
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercises(List<Exercise> exercises)
    {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView textExName;
        private TextView textExCals;
        private Button addExEntryButton;
        private ButtonListener buttonListener;
        Exercise exercise;

        public ExerciseViewHolder(@NonNull View itemView, ButtonListener buttonListener) {
            super(itemView);
            textExName = itemView.findViewById(R.id.exEntryName);
            textExCals = itemView.findViewById(R.id.exEntryCals);
            addExEntryButton = itemView.findViewById(R.id.addExEntryButton);
            this.buttonListener = buttonListener;

            addExEntryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonListener.onAddExClick(exercise);
                }
            });
        }
    }

    //ButtonListener interface will be implemented by AddExerciseActivity
    public interface ButtonListener{
        void onAddExClick(Exercise exercise);
    }

}
