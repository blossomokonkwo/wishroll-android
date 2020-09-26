package co.wishroll.localdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feed_posts_table")
public class FeedPostEntity {

    //created a post entity for feed page for all the fields that need to be cached
    //represents the lists of feedposts

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String thumbnailUrl;

    private String media_url;

    private int postID;





    public FeedPostEntity(String thumbnailUrl, String media_url, int postID) {
        this.thumbnailUrl = thumbnailUrl;
        this.media_url = media_url;
        this.postID = postID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getMedia_url() {
        return media_url;
    }

    public int getPostID() {
        return postID;
    }
}