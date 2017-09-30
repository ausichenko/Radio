package com.ausichenko.radio.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stream {

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
}
