package com.ausichenko.radio.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stream implements Parcelable {

    @SerializedName("stream")
    @Expose
    private String stream;

    @SerializedName("bitrate")
    @Expose
    private Integer bitrate;

    @SerializedName("content_type")
    @Expose
    private String contentType;

    @SerializedName("listeners")
    @Expose
    private Integer listeners;

    @SerializedName("status")
    @Expose
    private Integer status;

    protected Stream(Parcel in) {
        stream = in.readString();
        bitrate = in.readInt();
        contentType = in.readString();
        listeners = in.readInt();
        status = in.readInt();
    }

    public static final Creator<Stream> CREATOR = new Creator<Stream>() {
        @Override
        public Stream createFromParcel(Parcel in) {
            return new Stream(in);
        }

        @Override
        public Stream[] newArray(int size) {
            return new Stream[size];
        }
    };

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getListeners() {
        return listeners;
    }

    public void setListeners(Integer listeners) {
        this.listeners = listeners;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(stream);
        parcel.writeInt(bitrate);
        parcel.writeString(contentType);
        parcel.writeInt(listeners);
        parcel.writeInt(status);
    }
}
