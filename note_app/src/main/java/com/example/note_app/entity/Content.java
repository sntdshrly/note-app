package com.example.note_app.entity;

public class Content {
    private Integer content_id;
    private String content_title;
    private String content_field;
    private String timestamp;
    private String timestamp_update;

    public Content(Integer content_id, String content_title, String content_field, String timestamp, String timestamp_update) {
        this.content_id = content_id;
        this.content_title = content_title;
        this.content_field = content_field;
        this.timestamp = timestamp;
        this.timestamp_update = timestamp_update;
    }

    public Content() {

    }

    public Integer getContent_id() {
        return content_id;
    }

    public void setContent_id(Integer content_id) {
        this.content_id = content_id;
    }

    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public String getContent_field() {
        return content_field;
    }

    public void setContent_field(String content_field) {
        this.content_field = content_field;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp_update() {
        return timestamp_update;
    }

    public void setTimestamp_update(String timestamp_update) {
        this.timestamp_update = timestamp_update;
    }

    @Override
    public String toString() {
        return content_title;
    }
}
