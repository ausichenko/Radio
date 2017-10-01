package com.ausichenko.radio.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Radio implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("image")
    @Expose
    private Image image;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("twitter")
    @Expose
    private String twitter;

    @SerializedName("facebook")
    @Expose
    private String facebook;

    @SerializedName("total_listeners")
    @Expose
    private Integer totalListeners;

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    @SerializedName("streams")
    @Expose
    private List<Stream> streams = null;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    protected Radio(Parcel in) {
        id = in.readInt();
        name = in.readString();
        country = in.readString();
        image = in.readParcelable(Image.class.getClassLoader());
        slug = in.readString();
        website = in.readString();
        twitter = in.readString();
        facebook = in.readString();
        totalListeners = in.readInt();
        categories = in.createTypedArrayList(Category.CREATOR);
        streams = in.createTypedArrayList(Stream.CREATOR);
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<Radio> CREATOR = new Creator<Radio>() {
        @Override
        public Radio createFromParcel(Parcel in) {
            return new Radio(in);
        }

        @Override
        public Radio[] newArray(int size) {
            return new Radio[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Integer getTotalListeners() {
        return totalListeners;
    }

    public void setTotalListeners(Integer totalListeners) {
        this.totalListeners = totalListeners;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(country);
        parcel.writeParcelable(image, i);
        parcel.writeString(slug);
        parcel.writeString(website);
        parcel.writeString(twitter);
        parcel.writeString(facebook);
        parcel.writeInt(totalListeners);
        parcel.writeTypedList(categories);
        parcel.writeTypedList(streams);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
    }

    @Override
    public String toString() {
        String message = "";
        if (!TextUtils.isEmpty(country)) {
            message = message.concat("Country: ").concat(country).concat("\n");
        }
        if (!TextUtils.isEmpty(website)) {
            message = message.concat("Website: ").concat(website).concat("\n");
        }
        if (!TextUtils.isEmpty(twitter)) {
            message = message.concat("Twitter: ").concat(twitter).concat("\n");
        }
        if (!TextUtils.isEmpty(facebook)) {
            message = message.concat("Facebook: ").concat(facebook).concat("\n");
        }
        message = message.concat("Listeners: ").concat(String.valueOf(totalListeners)).concat("\n");
        if (!TextUtils.isEmpty(createdAt)) {
            message = message.concat("Created at: ").concat(createdAt).concat("\n");
        }
        return message;
    }
}
