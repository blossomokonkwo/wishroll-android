package co.wishroll.views.tools.video;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;

public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {
    FrameLayout mediaContainer;
    ToggleButton bookmarkButton;
    ImageButton downloadButton;
    ImageButton shareButton;
    View parent;
    ProgressBar progressBar;


    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        bookmarkButton = itemView.findViewById(R.id.bookmarkVideoView);
        downloadButton = itemView.findViewById(R.id.downloadVideoView);
        shareButton = itemView.findViewById(R.id.shareVideoView);
        mediaContainer = itemView.findViewById(R.id.media_container);
        progressBar = itemView.findViewById(R.id.videoProgressBar);
    }

    public void onBind(Post post) {
        parent.setTag(this);

    }
}
