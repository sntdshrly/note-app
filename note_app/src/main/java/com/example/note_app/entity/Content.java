package com.example.note_app.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import org.ocpsoft.prettytime.PrettyTime;

@Entity
public class Content {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "content_id")
    private Integer contentId;
    @Basic
    @Column(name = "content_title")
    private String contentTitle;
    @Basic
    @Column(name = "content_field")
    private String contentField;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<Feedback> feedbacks;

    @ManyToMany(mappedBy = "contents", fetch=FetchType.EAGER)
//    @JoinTable(name = "Collaborator",
//            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false),
//            joinColumns = @JoinColumn(name = "content_id", nullable = false),
//            inverseForeignKey = @ForeignKey(name = "fk_User_has_Content_User"),
//            foreignKey = @ForeignKey(name = "fk_User_has_Content_Content1")
//    )
    private Set<User> users = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch=FetchType.EAGER)
    @JoinTable(name = "ContentCategory",
            joinColumns = @JoinColumn(name = "content_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "category_id", nullable = false),
            foreignKey = @ForeignKey(name = "fk_Content_has_Category_Content1"),
            inverseForeignKey = @ForeignKey(name = "fk_Content_has_Category_Category1")
    )
    private Set<Category> categories = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentField() {
        return contentField;
    }

    public void setContentField(String contentField) {
        this.contentField = contentField;
    }

    public String getCreatedAt() {
//        String createFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(createdAt);
        PrettyTime createFormat = new PrettyTime();
        return String.valueOf(createFormat.format(createdAt));
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        PrettyTime updateFormat = new PrettyTime();
        return String.valueOf(updateFormat.format(updatedAt));
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreateTime() {
        String createTime = new SimpleDateFormat("dd LLLL yyyy HH:mm:ss").format(createdAt);
        return createTime;
    }
    public void setCreateTime(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateTime() {
        String createTime = new SimpleDateFormat("dd LLLL yyyy HH:mm:ss").format(updatedAt);
        return createTime;
    }
    public void setUpdateTime(Timestamp createdAt) {
        this.createdAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Objects.equals(contentId, content.contentId) && Objects.equals(contentTitle, content.contentTitle) && Objects.equals(contentField, content.contentField) && Objects.equals(createdAt, content.createdAt) && Objects.equals(updatedAt, content.updatedAt);
    }

    @Override
    public String toString() {
        return contentTitle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentId, contentTitle, contentField, createdAt, updatedAt);
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
