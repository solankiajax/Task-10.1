package com.example.TruckSharingApp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TruckSharingApp.model.Order;
import com.example.TruckSharingApp.model.User;

import java.util.List;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.orderViewHolder>{
    private List<Order> orderList;
    private Context context;
    private User user;
    private ClickInterfaceRV click;
    public orderAdapter(List<Order> orderList, User user, Context context,ClickInterfaceRV click) {
        this.orderList = orderList;
        this.context = context;
        this.user = user;
        this.click = click;
    }
    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.orders_list, parent, false);
        return new orderViewHolder(itemView);
    }


    public class orderViewHolder extends RecyclerView.ViewHolder  {
        public ImageView userImage;
        public TextView userName,userLocation,userDate,orderId;
        public ImageButton shareButton;

        public orderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        click.onItemClick(getAdapterPosition());
                    }catch (Exception e){
                        Log.d("reached",e.getMessage());
                    }

                }
            });
            shareButton = itemView.findViewById(R.id.shareButton);
            userName = itemView.findViewById(R.id.userName);
            userImage = itemView.findViewById(R.id.userImage);
            userLocation = itemView.findViewById(R.id.userLocation);
            userDate = itemView.findViewById(R.id.userDate);
            orderId = itemView.findViewById(R.id.orderId);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharing = new Intent(Intent.ACTION_SEND);
                    sharing.putExtra(Intent.EXTRA_TEXT,"This is my order");
                    sharing.setType("text/plain");
                    context.startActivity(Intent.createChooser(sharing,"Send To"));
                }
            });
            holder.userImage.setImageBitmap(BitmapFactory.decodeByteArray(user.getImg(), 0, user.getImg().length));
            holder.userName.setText("Name: " +orderList.get(position).getReceiver_name());
            holder.userLocation.setText("Location: " + orderList.get(position).getPickup_location());
            holder.userDate.setText("Date: " + orderList.get(position).getPickup_date());
            holder.orderId.setText("Order Id: " + Integer.toString(orderList.get(position).getOrder_id()));
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
