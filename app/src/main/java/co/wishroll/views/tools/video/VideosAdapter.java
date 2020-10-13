package co.wishroll.views.tools.video;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.FileUtils;
import co.wishroll.utilities.ToastUtils;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {


    private ArrayList<Post> videosList = new ArrayList<>();
    Context mContext;


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



        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(mContext,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions((Activity)mContext,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1000);

                    }else{

                        try {

                            startDownloading(position);

                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                    }
                }
                else{

                    try {

                        startDownloading(position);

                    } catch (Exception e) {

                        e.printStackTrace();

                    }
                }
            }
        });





    }












    private void startDownloading(int position) {

        ToastUtils.showToast(mContext, "Downloading...");

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(videosList.get(position).getMediaUrl()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download from WishRoll");
        request.setDescription("Downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis() + FileUtils.getExtension(videosList.get(position).getMediaUrl()));

        //get download service and enque file
        DownloadManager manager = (DownloadManager)mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        ToastUtils.showToast(mContext, "Downloaded");


    }



    @Override
    public int getItemCount() {
        return videosList.size();
    }



    static class VideoViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;
        ImageButton shareButton;
        ImageButton downloadButton;
        ToggleButton bookmarkButton;
        ProgressBar progressBar;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
            downloadButton = itemView.findViewById(R.id.downloadVideoView);

        }



        void setVideoData(Post post) {
            videoView.setVideoURI(Uri.parse(post.getMediaUrl()));
            videoView.setClickable(true);



            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    progressBar.setVisibility(View.VISIBLE);


                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    if (videoView.isPlaying()) {
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                    float videoHeight = (float) mediaPlayer.getVideoHeight();
                    float videoWidth = (float) mediaPlayer.getVideoWidth();

                    float videoRatio = mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoHeight();
                    float screenRatio = videoView.getWidth() / (float) videoView.getHeight();
                    float scale = videoRatio / screenRatio;


                    if (scale >= 1) { // a square or fatter
                        videoView.setScaleY(1f / scale);

                    } else { //longer than a square
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
                    if (videoView.isPlaying()) {
                        videoView.pause();
                    } else {
                        videoView.start();
                        //videoView.resume();

                    }


                }
            });
        }
    }
}
