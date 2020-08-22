package co.wishroll.views.tools;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.views.reusables.ImageActivity;
import co.wishroll.views.reusables.VideoActivity;

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.GridViewHolder> {

    private Context mContext;
    List<Post> mData;

    public GridRecyclerViewAdapter(Context mContext, List<Post> mData) {
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
        holder.videoFlag.setImageResource(R.drawable.ic_play_white);

        //imitation
        if(position % 2 == 0){
            holder.videoFlag.setVisibility(View.INVISIBLE);
        }else{
            holder.videoFlag.setVisibility(View.VISIBLE);

        }

        //imitation
        if(position % 2 == 0) {
            holder.postItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO(check if video or image/gif)

                    Intent i = new Intent(mContext, ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //put extras maybe
                    mContext.startActivity(i);
                }
            });
        }else{
            holder.postItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //TODO(check if video or image/gif)

                    Intent i = new Intent(mContext, VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //put extras maybe
                    mContext.startActivity(i);
                }
            });

        }




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder{

        LinearLayout postItem;
        ImageView mediaThumbnail;
        ImageView videoFlag;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            postItem = itemView.findViewById(R.id.postItem);
            mediaThumbnail = itemView.findViewById(R.id.mediaThumbnail);
            videoFlag = itemView.findViewById(R.id.videoFlag);
        }
    }

}
