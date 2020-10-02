package co.wishroll.models.domainmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendingTag {

    @SerializedName("id")
    @Expose
    private int trendingTagId;

    @SerializedName("text")
    @Expose
    private String trendingTag;

    @SerializedName("posts")
    @Expose
    private Post[] trendingTagThumbnails;

    public TrendingTag() {
    }

    public TrendingTag(int trendingTagId, String trendingTag, Post[] trendingTagThumbnails) {
        this.trendingTagId = trendingTagId;
        this.trendingTag = trendingTag;
        this.trendingTagThumbnails = trendingTagThumbnails;
    }

    public int getTrendingTagId() {
        return trendingTagId;
    }

    public void setTrendingTagId(int trendingTagId) {
        this.trendingTagId = trendingTagId;
    }

    public String getTrendingTag() {
        return trendingTag;
    }

    public void setTrendingTag(String trendingTag) {
        this.trendingTag = trendingTag;
    }

    public Post[] getTrendingTagThumbnails() {
        return trendingTagThumbnails;
    }

    public void setTrendingTagThumbnails(Post[] trendingTagThumbnails) {
        this.trendingTagThumbnails = trendingTagThumbnails;
    }


}
