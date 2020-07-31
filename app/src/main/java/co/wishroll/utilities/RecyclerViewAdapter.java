package co.wishroll.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wishroll.R;
import co.wishroll.viewmodel.NotificationViewModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<NotificationViewModel> mData;

    public RecyclerViewAdapter(Context mContext, List<NotificationViewModel> mData) {
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
        MyViewHolder myHolder = new MyViewHolder(v);
        //TODO(LEFT OFF HERE)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
