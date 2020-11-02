package co.wishroll.views.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;

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
        view = mInflater.inflate(R.layout.item_post_grid, parent, false);
        return new GridViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Post thisPost = mData.get(position);

        holder.videoFlag.setImageResource(R.drawable.ic_play_white);



        if(thisPost.getMediaUrl() != null) {
            if (thisPost.getMediaUrl().contains("mp4") || thisPost.getMediaUrl().contains(".mov")) {

                if (thisPost.getThumbnailUrl() == null) {

                    Glide.with(mContext)
                            .load(thisPost.getMediaUrl())
                            .placeholder(R.color.light_grey)
                            .into(holder.mediaThumbnail);

                } else {
                    Glide.with(mContext)
                            .load(thisPost.getThumbnailUrl())
                            .placeholder(R.color.light_grey)
                            .into(holder.mediaThumbnail);
                }

            } else {
                Glide.with(mContext)
                        .load(thisPost.getMediaUrl())
                        .placeholder(R.color.light_grey)
                        .into(holder.mediaThumbnail);
            }


        }




            if(thisPost.getMediaUrl() != null) {

                if (thisPost.getMediaUrl().contains("mp4") || thisPost.getMediaUrl().contains(".mov")) {
                    holder.videoFlag.setVisibility(View.VISIBLE);
                } else {
                    holder.videoFlag.setVisibility(View.INVISIBLE);
                }

            }





    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    private OnGridItemClickListener mListener;
    public interface OnGridItemClickListener{
        void onGridItemClicked(int position);
    }

    public void setOnGridItemClickListener(OnGridItemClickListener listener){
            this.mListener = listener;
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder{

        LinearLayout postItem;
        ImageView mediaThumbnail;
        ImageView videoFlag;

        public GridViewHolder(@NonNull View itemView, OnGridItemClickListener listener) {
            super(itemView);
            postItem = itemView.findViewById(R.id.postItem);
            mediaThumbnail = itemView.findViewById(R.id.mediaThumbnail);
            videoFlag = itemView.findViewById(R.id.videoFlag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onGridItemClicked(getAdapterPosition());
                        }
                    }

                }
            });
        }
    }




}
