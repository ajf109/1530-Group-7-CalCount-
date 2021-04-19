package com.example.calcount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.FoodViewHolder> {
    private List<Food> foods = new ArrayList<Food>();
    private UserViewModel userViewModel;
    private Context context;
    private String username;
    private ButtonListener buttonListener;

    public AddFoodAdapter(String str, ButtonListener buttonListener)
    {
        username = str;
        this.buttonListener = buttonListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_entry, parent, false);
        return new FoodViewHolder(itemView, buttonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food f = foods.get(position);
        holder.textFoodName.setText(f.getName());
        holder.textFoodCals.setText("" + f.getCalories() + "");
        holder.food = f;
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods; //can do in onbind instead
        notifyDataSetChanged();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView textFoodName;
        private TextView textFoodCals;
        private UserViewModel userViewModel;
        Food food;
        Button addFoodEntryButton;
        ButtonListener buttonListener;

        public FoodViewHolder(@NonNull View itemView, ButtonListener buttonListener) {
            super(itemView);
            textFoodName = itemView.findViewById(R.id.foodEntryName);
            textFoodCals = itemView.findViewById(R.id.foodEntryCals);
            addFoodEntryButton = itemView.findViewById(R.id.addFoodEntryButton);
            this.buttonListener = buttonListener;

            addFoodEntryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* food.setInDiary(true);
                    //userViewModel.update(food);
                    Intent intent = new Intent(v.getContext(), HomepageActivity.class);
                    intent.putExtra("username", username);
                    v.getContext().startActivity(intent);*/
                    buttonListener.onAddFoodClick(food);
                }
            });
        }


    }

    public interface ButtonListener{
        void onAddFoodClick(Food food);
    }

}
