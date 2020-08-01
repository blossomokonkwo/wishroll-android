package co.wishroll.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wishroll.R;
import co.wishroll.viewmodel.NotificationViewModel;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<NotificationViewModel> mData;

    public NotificationRecyclerViewAdapter(Context mContext, List<NotificationViewModel> mData) {
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
        v = LayoutInflater.from(mContext).inflate(R.layout.notification_list_item, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.notifUsername.setText(mData.get(position).getUser().getUsername());
        holder.notifDescription. setText(mData.get(position).getNotificationDescription());
        holder.notifTimeAgo.setText(mData.get(position).getTimeAgo());

        //TODO(very skinny code right here)
        holder.notifProfilePicture.setImageResource(R.drawable.defaultprofile);
        holder.notifImageThumbnail.setImageResource(R.drawable.reaction_picture);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView notifUsername;
        private TextView notifDescription;
        private TextView notifTimeAgo;
        ImageView notifProfilePicture;
        ImageView notifImageThumbnail;

        //TODO(add profile picture and picture thumbnail)

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            notifUsername = (TextView) itemView.findViewById(R.id.notificationUsername);
            notifDescription = (TextView) itemView.findViewById(R.id.notificationDescription);
            notifTimeAgo = (TextView) itemView.findViewById(R.id.notificationTimeAgo);
            notifProfilePicture = (ImageView) itemView.findViewById(R.id.notificationProfilePicture);
            notifImageThumbnail = (ImageView) itemView.findViewById(R.id.notificationThumbnail);
        }
    }
}
