package co.WishRoll.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.WishRoll.Models.ImagePost;
import co.WishRoll.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<ImagePost> fakePosts;

    public RecyclerViewAdapter(Context mContext, List<ImagePost> fakePosts) {
        this.mContext = mContext;
        this.fakePosts = fakePosts;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_gridsquare, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.griddedImage.setImageURI(fakePosts.get(position).getImageURL());
        //THIS IS WHERE I STOPPED I LEFT TO GO FIND OUT HOW TO ACTUALLY GET THE IMAGES TO LOAD WITHOUT MY APP FRIGGIN CRASHING LOL
        //SEE YOU LATER GUYTH I LOVE EACH AND EVERY ONE OF YOU ILL BE BACK TO SET UP ON CLICK LISTENERS. MUAHH

    }

    @Override
    public int getItemCount() {
        return fakePosts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView griddedImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            griddedImage = itemView.findViewById(R.id.singlePost);
        }
    }
}
