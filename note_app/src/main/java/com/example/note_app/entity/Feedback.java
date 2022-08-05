package com.example.note_app.entity;

public class Feedback {
    private Integer feedback_id;
    private String feedback_field;
    private Integer user_id;
    private Integer content_id;

    public Feedback(Integer feedback_id, String feedback_field, Integer user_id, Integer content_id) {
        this.feedback_id = feedback_id;
        this.feedback_field = feedback_field;
        this.user_id = user_id;
        this.content_id = content_id;
    }


    public Integer getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(Integer feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getFeedback_field() {
        return feedback_field;
    }

    public void setFeedback_field(String feedback_field) {
        this.feedback_field = feedback_field;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getContent_id() {
        return content_id;
    }

    public void setContent_id(Integer content_id) {
        this.content_id = content_id;
    }
}