package com.example.note_app.entity.relationship;

import javax.persistence.*;

@Entity
public class ContentCategory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "content_category_id")
    private Integer content_category_id;

    @Basic
    @Column(name = "content_id")
    private Integer content_id;

    @Basic
    @Column(name = "category_id")
    private Integer category_id;

    public ContentCategory(Integer content_category_id, Integer content_id, Integer category_id) {
        this.content_category_id = content_category_id;
        this.content_id = content_id;
        this.category_id = category_id;
    }

    public ContentCategory() {

    }
}
