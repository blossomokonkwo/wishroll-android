package co.wishroll.views.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.domainmodels.TrendingTag;

public class TrendingRecyclerViewAdapter extends RecyclerView.Adapter<TrendingRecyclerViewAdapter.TrendingViewHolder> {
    private static final String TAG = "TrendingRecyclerViewAda";
    private Context mContext;
    ArrayList<TrendingTag> mData;
    private OnTrendingTagListener onTrendingTagListener;

    Post thumbnail1 = null;
    Post thumbnail2 = null;
    Post thumbnail3 = null;



    public TrendingRecyclerViewAdapter(Context mContext, ArrayList<TrendingTag> mData, OnTrendingTagListener onTrendingTagListener ) {
        this.mContext = mContext;
        this.mData = mData;
        this.onTrendingTagListener = onTrendingTagListener;
    }


    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_trending_tag, parent, false);
        return new TrendingViewHolder(view, onTrendingTagListener);

    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, int position) {

        TrendingTag thisTag = mData.get(position);

        switch(thisTag.getTrendingTagThumbnails().length){
            case 0:
                holder.thumbnailOne.setVisibility(View.GONE);
                holder.thumbnailTwo.setVisibility(View.GONE);
                holder.thumbnailThree.setVisibility(View.GONE);
                break;

            case 1:
                thumbnail1 = thisTag.getTrendingTagThumbnails()[0];
                holder.thumbnailOne.setVisibility(View.VISIBLE);
                holder.thumbnailTwo.setVisibility(View.GONE);
                holder.thumbnailThree.setVisibility(View.GONE);
                break;

            case 2:
                thumbnail1 = thisTag.getTrendingTagThumbnails()[0];
                thumbnail2 = thisTag.getTrendingTagThumbnails()[1];
                holder.thumbnailOne.setVisibility(View.VISIBLE);
                holder.thumbnailTwo.setVisibility(View.VISIBLE);
                holder.thumbnailThree.setVisibility(View.GONE);
                break;

            default:
                holder.thumbnailOne.setVisibility(View.VISIBLE);
                holder.thumbnailTwo.setVisibility(View.VISIBLE);
                holder.thumbnailThree.setVisibility(View.VISIBLE);

                thumbnail1 = thisTag.getTrendingTagThumbnails()[0];
                thumbnail2 = thisTag.getTrendingTagThumbnails()[1];
                thumbnail3 = thisTag.getTrendingTagThumbnails()[2];
                break;

        }

        holder.tagString.setText(thisTag.getTrendingTag());

       /* holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, PostsGridActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("query", thisTag.getTrendingTag());
                mContext.startActivity(i);
            }
        });*/

        //Displaying Thumbnails

        if(thumbnail1 != null) {
            if ((thumbnail1.getMediaUrl().contains("mp4") || thumbnail1.getMediaUrl().contains(".mov")) && thumbnail1 != null) {

                if (thumbnail1.getThumbnailUrl() == null) {

                    Glide.with(mContext)
                            .load(thumbnail1.getMediaUrl())
                            .placeholder(R.color.light_grey)
                            .into(holder.thumbnailOne);

                } else {
                    Glide.with(mContext)
                            .load(thumbnail1.getThumbnailUrl())
                            .placeholder(R.color.light_grey)
                            .into(holder.thumbnailOne);
                }
            } else {
                Glide.with(mContext)
                        .load(thumbnail1.getMediaUrl())
                        .placeholder(R.color.light_grey)
                        .into(holder.thumbnailOne);
            }

        }

        if(thumbnail2 != null) {
            if ((thumbnail2.getMediaUrl().contains("mp4") || thumbnail2.getMediaUrl().contains(".mov")) && thumbnail2 != null) {

                if (thumbnail2.getThumbnailUrl() == null) {

                    Glide.with(mContext)
                            .load(thumbnail2.getMediaUrl())
                            .placeholder(R.color.light_grey)
                            .into(holder.thumbnailTwo);

                } else {
                    Glide.with(mContext)
                            .load(thumbnail2.getThumbnailUrl())
                            .placeholder(R.color.light_grey)
                            .into(holder.thumbnailTwo);
                }
            } else {
                Glide.with(mContext)
                        .load(thumbnail2.getMediaUrl())
                        .placeholder(R.color.light_grey)
                        .into(holder.thumbnailTwo);
            }

        }

        if(thumbnail3 != null) {
            if ((thumbnail3.getMediaUrl().contains(".mp4") || thumbnail3.getMediaUrl().contains(".mov")) && thumbnail3 != null) {
                if (thumbnail3.getThumbnailUrl() == null) {
                    Glide.with(mContext)
                            .load(thumbnail3.getMediaUrl())
                            .placeholder(R.color.light_grey)
                            .into(holder.thumbnailThree);
                } else {
                    Glide.with(mContext)
                            .load(thumbnail3.getThumbnailUrl())
                            .placeholder(R.color.light_grey)
                            .into(holder.thumbnailThree);
                }
            } else {
                Glide.with(mContext)
                        .load(thumbnail3.getMediaUrl())
                        .placeholder(R.color.light_grey)
                        .into(holder.thumbnailThree);
            }

        }



        //Displaying Video Flag
        if(thumbnail1 != null) {
            if (thumbnail1.getMediaUrl().contains(".mp4") || thumbnail1.getMediaUrl().contains(".mov")) {
                holder.videoFlagOne.setVisibility(View.VISIBLE);
            } else {
                holder.videoFlagOne.setVisibility(View.INVISIBLE);
            }
        }
        if(thumbnail2 != null) {
            if (thumbnail2.getMediaUrl().contains(".mp4") || thumbnail2.getMediaUrl().contains(".mov")) {
                holder.videoFlagTwo.setVisibility(View.VISIBLE);
            } else {
                holder.videoFlagTwo.setVisibility(View.INVISIBLE);
            }
        }
        if(thumbnail3 != null) {
            if (thumbnail3.getMediaUrl().contains(".mp4") || thumbnail3.getMediaUrl().contains(".mov")) {
                holder.videoFlagThree.setVisibility(View.VISIBLE);
            } else {
                holder.videoFlagThree.setVisibility(View.INVISIBLE);
            }
        }


       /* holder.thumbnailOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO(check if video or image/gif)
                Log.d(TAG, "onClick: MEDIA URL " + thumbnail1.getMediaUrl());

                if (thumbnail1.getMediaUrl().contains(".mp4") || thumbnail1.getMediaUrl().contains(".mov")) {
                    Intent i = new Intent(mContext, VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("postId", thisTag.getTrendingTagThumbnails()[0].getId());
                    i.putExtra("mediaUrl", thisTag.getTrendingTagThumbnails()[0].getMediaUrl());
                    mContext.startActivity(i);
                }else{
                    Intent i = new Intent(mContext, ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("postId", thisTag.getTrendingTagThumbnails()[0].getId());
                    i.putExtra("mediaUrl", thisTag.getTrendingTagThumbnails()[0].getMediaUrl());
                    mContext.startActivity(i);
                }
            }
        });*/

            /*holder.thumbnailTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO(check if video or image/gif)

                    if (thumbnail2.getMediaUrl().contains(".mp4") || thumbnail2.getMediaUrl().contains(".mov")) {
                        Intent i = new Intent(mContext, VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.putExtra("postId", thisTag.getTrendingTagThumbnails()[1].getId());
                        i.putExtra("mediaUrl", thisTag.getTrendingTagThumbnails()[1].getMediaUrl());
                        mContext.startActivity(i);
                    }else{
                        Intent i = new Intent(mContext, ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.putExtra("postId", thisTag.getTrendingTagThumbnails()[1].getId());
                        i.putExtra("mediaUrl", thisTag.getTrendingTagThumbnails()[1].getMediaUrl());
                        mContext.startActivity(i);
                    }
                }
            });*/






            /*holder.thumbnailThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO(check if video or image/gif)

                    if (thumbnail3.getMediaUrl().contains(".mp4") || thumbnail3.getMediaUrl().contains(".mov")) {
                        Intent i = new Intent(mContext, VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.putExtra("postId", thisTag.getTrendingTagThumbnails()[2].getId());
                        i.putExtra("mediaUrl", thisTag.getTrendingTagThumbnails()[2].getMediaUrl());
                        mContext.startActivity(i);


                    }else{

                        Intent i = new Intent(mContext, ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.putExtra("postId", thisTag.getTrendingTagThumbnails()[2].getId());
                        i.putExtra("mediaUrl", thisTag.getTrendingTagThumbnails()[2].getMediaUrl());
                        mContext.startActivity(i);
                    }
                }
            });*/










    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public static class TrendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout postItem;
        TextView tagString, seeMore;
        ImageView thumbnailOne, thumbnailTwo, thumbnailThree;
        ImageView videoFlagOne, videoFlagTwo, videoFlagThree;
        OnTrendingTagListener mListener;





        public TrendingViewHolder(@NonNull View itemView, OnTrendingTagListener listener) {
            super(itemView);
            postItem = itemView.findViewById(R.id.trendingPostItem);

            thumbnailOne = itemView.findViewById(R.id.thumbnailOne);
            thumbnailTwo = itemView.findViewById(R.id.thumbnailTwo);
            thumbnailThree = itemView.findViewById(R.id.thumbnailThree);

            videoFlagOne = itemView.findViewById(R.id.videoFlagOne);
            videoFlagTwo = itemView.findViewById(R.id.videoFlagTwo);
            videoFlagThree = itemView.findViewById(R.id.videoFlagThree);

            tagString = itemView.findViewById(R.id.tagTitle);
            seeMore = itemView.findViewById(R.id.seeMoreButton);

            mListener = listener;
            thumbnailOne.setOnClickListener(this);
            thumbnailTwo.setOnClickListener(this);
            thumbnailThree.setOnClickListener(this);
            seeMore.setOnClickListener(this);






        }


        @Override
        public void onClick(View view) {
            int thumbnailPosition = 0;

            if(view.getId() == thumbnailOne.getId()){
                thumbnailPosition = 0;
            }else if(view.getId() == thumbnailTwo.getId()){
                thumbnailPosition = 1;
            }else if(view.getId() == thumbnailThree.getId()){
                thumbnailPosition = 2;
            }else if(view.getId() == seeMore.getId()){

                thumbnailPosition = -1;
            }


            mListener.onTrendingTagClicked(getBindingAdapterPosition(), thumbnailPosition);
        }
    }

    public interface  OnTrendingTagListener{
        void onTrendingTagClicked(int position, int thumbnailPosition);
    }




}
