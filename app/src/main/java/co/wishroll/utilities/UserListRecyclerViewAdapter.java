package co.wishroll.utilities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wishroll.R;
import co.wishroll.model.Notification;
import co.wishroll.model.User;

public class UserListRecyclerViewAdapter extends RecyclerView.Adapter<UserListRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<User> mData;

    public UserListRecyclerViewAdapter(Context mContext, List<User> mData) {
        //TODO(if anything goes wrong, change NotificationViewModel to Notification but i doubt that would be the problem because the view model wraps the model
        //I took in a view model instead because I wanted to be able to use data binding and observable fields in the notifications fragment, i see the adpter as just, quite frankly
        // a utility to get things in order i don't think it should mess with any mvvm functionality. Yeah, it for sure shouldn't mess with any functionality as long as it wraps
        //the notification model properly.

        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.user_list_item, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.fullnameListView.setText(mData.get(position).getFullName());
        holder.usernameListView. setText(mData.get(position).getUsername());


        //TODO(very skinny, adventurous code right here)
        holder.profilePictureListView.setImageResource(R.drawable.defaultprofile);

        if(position % 2 == 0) {
            holder.relationship.setText("Following");
            holder.relationship.setTextColor(Color.parseColor("#16a2ef"));
            holder.relationship.setBackgroundResource(R.drawable.following_white_button);
        }else{
            holder.relationship.setText("Follow");
            holder.relationship.setTextColor(Color.parseColor("#FFFFFF"));
            holder.relationship.setBackgroundResource(R.drawable.follow_blue_button);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView usernameListView;
        private TextView fullnameListView;
        ImageView profilePictureListView;
        Button relationship;

        //TODO(add profile picture and picture thumbnail)

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameListView = itemView.findViewById(R.id.listUsername);
            fullnameListView = itemView.findViewById(R.id.listFullName);
            profilePictureListView = itemView.findViewById(R.id.listProfilePicture);
            relationship = itemView.findViewById(R.id.bRelationship);
        }
    }


}
