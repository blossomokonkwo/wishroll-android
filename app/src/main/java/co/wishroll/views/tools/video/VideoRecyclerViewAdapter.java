package co.wishroll.views.tools.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Videos;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoViewHolder> {

    List<Videos> videosList;
    Context mContext;

    public VideoRecyclerViewAdapter(List<Videos> videosList, Context mContext) {
        this.videosList = videosList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_screen_item, viewGroup, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Videos videoItem = videosList.get(position);
        

    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
