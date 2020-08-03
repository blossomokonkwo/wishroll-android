package co.wishroll.utilities;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wishroll.R;

import co.wishroll.viewmodel.PostViewModel;

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.GridViewHolder> {
    //universal gridrecyclerview for all the grid layouts in wishroll
    private Context mContext;
    List<PostViewModel> mData;

    public GridRecyclerViewAdapter(Context mContext, List<PostViewModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.post_grid_item, parent, false);

        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        holder.mediaThumbnail.setImageResource(R.drawable.reaction_picture);
        //if(mData.get(position).mediaExtension == "mp4" || mData.get(position).mediaExtension == "mov" ){
            holder.videoFlag.setImageResource(R.drawable.ic_play);
        //}else{
            holder.videoFlag.setVisibility(View.INVISIBLE);
        //}

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder{
        ImageView mediaThumbnail;
        ImageView videoFlag;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            mediaThumbnail = (ImageView) itemView.findViewById(R.id.mediaThumbnail);
            videoFlag = (ImageView) itemView.findViewById(R.id.videoFlag);
        }
    }

}
