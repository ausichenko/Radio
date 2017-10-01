package com.ausichenko.radio.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumb implements Parcelable {

    @SerializedName("url")
    @Expose
    private String url;

    protected Thumb(Parcel in) {
        url = in.readString();
    }

    public static final Creator<Thumb> CREATOR = new Creator<Thumb>() {
        @Override
        public Thumb createFromParcel(Parcel in) {
            return new Thumb(in);
        }

        @Override
        public Thumb[] newArray(int size) {
            return new Thumb[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
    }
}
