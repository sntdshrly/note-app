package com.example.note_app.entity.relationship;

import javax.persistence.*;

@Entity
@Table(name = "UserCategory")
public class UserCategory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_category_id")
    private Integer user_category_id;

    @Basic
    @Column(name = "user_id")
    private Integer user_id;

    @Basic
    @Column(name = "category_id")
    private Integer category_id;

    public UserCategory(Integer user_id, Integer category_id) {
        this.user_id = user_id;
        this.category_id = category_id;
    }

    public UserCategory() {

    }
}
