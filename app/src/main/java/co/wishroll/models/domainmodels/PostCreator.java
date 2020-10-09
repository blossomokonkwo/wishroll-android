package co.wishroll.models.domainmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostCreator implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("verified")
    @Expose
    private Boolean verified;

    @SerializedName("avatar")
    @Expose
    private String avatar;


    /**
     * No args constructor for use in serialization
     *
     */
    public PostCreator() {
    }

    /**
     *
     *
     * @param verified
     * @param id
     * @param avatar
     * @param username
     */
    public PostCreator(Integer id, String username, Boolean verified, String avatar) {
        super();
        this.id = id;
        this.username = username;
        this.verified = verified;
        this.avatar = avatar;
    }

    protected PostCreator(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        username = in.readString();
        byte tmpVerified = in.readByte();
        verified = tmpVerified == 0 ? null : tmpVerified == 1;
        avatar = in.readString();
    }


    public static final Creator<PostCreator> CREATOR = new Creator<PostCreator>() {
        @Override
        public PostCreator createFromParcel(Parcel in) {
            return new PostCreator(in);
        }

        @Override
        public PostCreator[] newArray(int size) {
            return new PostCreator[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(username);
        parcel.writeByte((byte) (verified == null ? 0 : verified ? 1 : 2));
        parcel.writeString(avatar);
    }
}
