package com.example.note_app.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Feedback {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "feedback_id")
    private Integer feedbackId;
    @Basic
    @Column(name = "feedback_field")
    private String feedbackField;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "content_id", referencedColumnName = "content_id", nullable = false)
    private Content content;

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackField() {
        return feedbackField;
    }

    public void setFeedbackField(String feedbackField) {
        this.feedbackField = feedbackField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(feedbackId, feedback.feedbackId) && Objects.equals(feedbackField, feedback.feedbackField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackId, feedbackField);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
