package co.wishroll.views.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.models.domainmodels.UserNotification;

public class NotisRecyclerViewAdapter extends RecyclerView.Adapter<NotisRecyclerViewAdapter.NotifsViewHolder>  {



    private static final String TAG = "NotifsRecyclerViewAda";
    private Context mContext;
    ArrayList<UserNotification> mData;
    private OnNotificationListener onNotificationListener;

    public NotisRecyclerViewAdapter(Context mContext, ArrayList<UserNotification> mData, OnNotificationListener onNotificationListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.onNotificationListener = onNotificationListener;
    }

    @NonNull
    @Override
    public NotisRecyclerViewAdapter.NotifsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_notification_list, parent, false);
        return new NotifsViewHolder(view, onNotificationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotisRecyclerViewAdapter.NotifsViewHolder holder, int position) {
        UserNotification thisNotification = mData.get(position);
        //TODO(view binding)


        //where all the cute stuff goes
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class NotifsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircularImageView profilePicture;
        TextView notificationsUsername, notificationsDescription, notificationsTime;
        ImageView playNotification, notificationThumbnail;
        OnNotificationListener listener;

        public NotifsViewHolder(@NonNull View itemView, OnNotificationListener mListener) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.notificationProfilePicture);
            notificationsDescription = itemView.findViewById(R.id.notificationDescription);
            notificationsTime = itemView.findViewById(R.id.notificationTimeAgo);
            notificationsUsername = itemView.findViewById(R.id.notificationUsername);
            playNotification = itemView.findViewById(R.id.playNotification);
            notificationThumbnail = itemView.findViewById(R.id.notificationThumbnail);

            listener = mListener;

            profilePicture.setOnClickListener(this);
            notificationsUsername.setOnClickListener(this);
            notificationThumbnail.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            int notificationClicked = 0;

            if(view.getId() == profilePicture.getId() ){
                notificationClicked = 1;
            }else if(view.getId() == notificationThumbnail.getId()){
                notificationClicked = 2;
            }else if(view.getId() == notificationsUsername.getId()){
                notificationClicked = 3;
            }

            //1 = user chose profile picture
            //2 = user chose the thumbnail of the object
            //3 = user chose the username of the other user

            listener.onNotificationClicked(getAdapterPosition(), notificationClicked);


        }
    }

    public interface OnNotificationListener{
        void onNotificationClicked(int position, int notificationIntentPosition);
    }


}
