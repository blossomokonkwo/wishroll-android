package co.wishroll.views.tools.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.viewmodel.media.VideosViewModel;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder>{

    private static final String TAG = "VideosAdapter";

    private ArrayList<Post> videosList = new ArrayList<>();
    Context mContext;
    VideosViewModel videosViewModel;

    public VideosAdapter(ArrayList<Post> videosList) {
        this.videosList = videosList;
    }

    public VideosAdapter(ArrayList<Post> videosList, Context mContext) {
        this.videosList = videosList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_screen, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
            holder.setVideoData(videosList.get(position));

            if((position+3) == videosList.size()){
                if(videosList.size() %  videosViewModel.getDataSetSize() == 0) {
                    VideosViewModel.setOffset(videosList.size());
                    videosViewModel.getMoreVideos();
                    Log.d(TAG, "onBindViewHolder: in the add/load more videos option");
                }
                Log.d(TAG, "onBindViewHolder: this is about the time we would be needing to get more posts, idk how the view pager takes care of that tho");
            }
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{

        VideoView videoView;
        ImageButton shareButton;
        ToggleButton bookmarkButton;
        ProgressBar progressBar;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            shareButton = itemView.findViewById(R.id.shareVideoView);
            bookmarkButton = itemView.findViewById(R.id.bookmarkVideoView);
            progressBar = itemView.findViewById(R.id.videoProgressBar);

        }


        void setVideoData(Post post){
            bookmarkButton.setChecked(post.getBookmarked());
            videoView.setVideoURI(Uri.parse(post.getMediaUrl()));
            videoView.setClickable(true);


            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    progressBar.setVisibility(View.VISIBLE);


                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    if(videoView.isPlaying()){
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                    float videoHeight = (float) mediaPlayer.getVideoHeight();
                    float videoWidth = (float) mediaPlayer.getVideoWidth();

                    float videoRatio = mediaPlayer.getVideoWidth()/ (float) mediaPlayer.getVideoHeight();
                    float screenRatio = videoView.getWidth()/(float) videoView.getHeight();
                    float scale = videoRatio/screenRatio;




                    if(scale >= 1){ //if video is a square or fatter
                        videoView.setScaleY(1f/scale);

                    }else{ //longer than a square
                        videoView.setScaleX(scale);

                    }









                }


            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //mediaPlayer.start();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(videoView.isPlaying()){
                        videoView.pause();
                    }else{
                        videoView.start();
                        //videoView.resume();

                    }


                }
            });
        }
    }
}
