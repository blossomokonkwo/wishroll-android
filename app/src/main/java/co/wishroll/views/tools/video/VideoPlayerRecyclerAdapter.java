package co.wishroll.views.tools.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;

public class VideoPlayerRecyclerAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "VideoPlayerRecyclerAdapter";

    private ArrayList<Post> videosList = new ArrayList<>();
    Context mContext;



    public VideoPlayerRecyclerAdapter(ArrayList<Post> videosList, Context mContext) {
        this.videosList = videosList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoPlayerViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_video_screen, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((VideoPlayerViewHolder) holder).onBind(videosList.get(position));
    }



    @Override
    public int getItemCount() {
        return videosList.size();
    }


}
