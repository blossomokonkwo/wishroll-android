package co.wishroll.utilities;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;
import co.wishroll.entities.Videos;

public class VideoPlayerViewHolder extends RecyclerView.ViewHolder{

    FrameLayout mediaContainer;
    CircularImageView profilePicture;
    TextView username, timeAgo, numLikes, numBookmarks, numShares, videoCaption, numViews;
    ImageView videoThumbnail;
    ProgressBar progressBar;
    View parent;
    RequestManager requestManager;
    ImageButton likeButton, shareButton, bookmarkButton;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);

        parent = itemView;

        mediaContainer = itemView.findViewById(R.id.media_container);
        profilePicture = itemView.findViewById(R.id.profilePicVideoView);
        username = itemView.findViewById(R.id.usernameVideoView);
        timeAgo = itemView.findViewById(R.id.timeAgoVideoView);
        numLikes = itemView.findViewById(R.id.likeCountVideoView);
        numShares = itemView.findViewById(R.id.shareCountVideoView);
        numBookmarks = itemView.findViewById(R.id.bookmarkCountVideoView);
        videoCaption = itemView.findViewById(R.id.videoCaption);
        numViews = itemView.findViewById(R.id.viewCountVideoView);
        progressBar = itemView.findViewById(R.id.videoProgressBar);
        likeButton = itemView.findViewById(R.id.likeVideoView);
        shareButton = itemView.findViewById(R.id.shareVideoView);
        bookmarkButton = itemView.findViewById(R.id.bookmarkVideoView);
        //videoThumbnail = itemView.findViewById(R.id.); where do I find this?


    }

        public void onBind(Videos video, RequestManager requestManager){
            this.requestManager = requestManager;
            parent.setTag(this);
            videoCaption.setText(video.getCaption());
            timeAgo.setText(video.getTimeAgo());

            //this.requestManager.load(video.getThumbnailURL()).into();

        }





}
