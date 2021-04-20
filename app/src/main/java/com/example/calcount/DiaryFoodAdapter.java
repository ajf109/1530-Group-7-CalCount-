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

public class DiaryFoodAdapter extends RecyclerView.Adapter<DiaryFoodAdapter.DiaryViewHolder>{
    private List<Food> diaryFoodList = new ArrayList<>();
    private ButtonListener buttonListener;

    public DiaryFoodAdapter(ButtonListener buttonListener)
    {
        this.buttonListener = buttonListener;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_diary_entry, parent, false);
        return new DiaryViewHolder(itemView, buttonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        Food f = diaryFoodList.get(position);
        holder.textFoodName.setText(f.getName());
        holder.textFoodCals.setText("-" + f.getCalories() + "");
        holder.food = f;
    }

    @Override
    public int getItemCount() {
        return diaryFoodList.size();
    }

    public void setFoods(List<Food> diaryFoodList)
    {
        this.diaryFoodList = diaryFoodList;
        notifyDataSetChanged();
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {
        private TextView textFoodName;
        private TextView textFoodCals;
        private Button removeButton;
        Food food;
        ButtonListener buttonListener;

        public DiaryViewHolder(@NonNull View itemView, ButtonListener buttonListener) {
            super(itemView);

            textFoodName = itemView.findViewById(R.id.foodDiaryEntryName);
            textFoodCals = itemView.findViewById(R.id.foodDiaryEntryCals);
            removeButton = itemView.findViewById(R.id.removeFoodEntryButton);
            this.buttonListener = buttonListener;

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonListener.onRemoveFoodClick(food);
                }
            });
        }
    }

    public interface ButtonListener{
        void onRemoveFoodClick(Food food);
    }
}
