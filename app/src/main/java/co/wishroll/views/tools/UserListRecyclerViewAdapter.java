package co.wishroll.views.tools;

import android.content.Context;
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
import co.wishroll.models.domainmodels.User;

public class UserListRecyclerViewAdapter extends RecyclerView.Adapter<UserListRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<User> mData;

    public UserListRecyclerViewAdapter(Context mContext, List<User> mData) {

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

        holder.fullnameListView.setText("blossom okonkwo filler");
        holder.usernameListView. setText("blossomfiller");
        /*holder.fullnameListView.setText(mData.get(position).getFullName());
        holder.usernameListView. setText(mData.get(position).getUsername());*/


        //imitation code
        holder.profilePictureListView.setImageResource(R.drawable.defaultprofile);

        //imitiation code
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

        //imitiation code: user object needed here?
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
