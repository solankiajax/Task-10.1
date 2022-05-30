package com.example.TruckSharingApp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TruckSharingApp.model.Order;
import com.example.TruckSharingApp.model.Trucker;
import com.example.TruckSharingApp.model.User;

import java.util.List;

public class truckAdapter extends RecyclerView.Adapter<truckAdapter.truckViewHolder>{
    private List<Trucker> truckerList;
    private Context context;;

    public truckAdapter(List<Trucker> truckerList, Context context) {
        this.truckerList = truckerList;
        this.context = context;
    }
    @NonNull
    @Override
    public truckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.truck_list, parent, false);
        return new truckViewHolder(itemView);
    }


    public class truckViewHolder extends RecyclerView.ViewHolder  {
        public ImageView truckImage;
        public TextView truckerName,truckerLocation,truckId;


        public truckViewHolder(@NonNull View itemView) {
            super(itemView);
            truckerName = itemView.findViewById(R.id.truckerName);
            truckImage = itemView.findViewById(R.id.truckImage);
            truckerLocation = itemView.findViewById(R.id.truckerLocation);
            truckId = itemView.findViewById(R.id.truckId);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull truckViewHolder holder, int position) {
        holder.truckImage.setImageResource(R.drawable.truck);
        holder.truckerName.setText("Name: " +truckerList.get(position).getName());
        holder.truckerLocation.setText("Location: " + truckerList.get(position).getLocation());
        holder.truckId.setText("Trucker Id: " + Integer.toString(truckerList.get(position).getTrucker_id()));
    }
    @Override
    public int getItemCount() {
        return truckerList.size();
    }
}
