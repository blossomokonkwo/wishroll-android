package co.wishroll.views.tools.video;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;

public class VideoPlayerViewHolder extends RecyclerView.ViewHolder{

    FrameLayout mediaContainer;
    TextView  numShares, numViews;

    ProgressBar progressBar;
    View parent;
    RequestManager requestManager;
    ImageButton  shareButton;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);

        parent = itemView;

        mediaContainer = itemView.findViewById(R.id.media_container);
        progressBar = itemView.findViewById(R.id.videoProgressBar);
        shareButton = itemView.findViewById(R.id.shareVideoView);





    }

        public void onBind(Post post, RequestManager requestManager){
            this.requestManager = requestManager;
            parent.setTag(this);

            //this.requestManager.load(post.getThumbnailURL()).into();

        }





}
