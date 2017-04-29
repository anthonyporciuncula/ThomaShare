package com.android.hood.thomashare.Bean;

/**
 * Created by Cerjeff Pineda on 4/11/2017.
 */

public class FileList {

    String title;
    String Description;
    String displayName;
    String downloadUri;
    String tag;
    String dateTime;


    String uploader;

    public FileList(String displayName, String downloadUri, String tag, String uploader, String dateTime) {
        this.setDisplayName(displayName);
        this.setDownloadUri(downloadUri);
        this.setTag(tag);
        this.setUploader(uploader);
        this.setDateTime(dateTime);
    }

    ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
