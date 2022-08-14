package com.example.note_app.entity.relationship;

import javax.persistence.*;

@Entity
public class Collaborator {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "collaborator_id")
    private Integer collaborator_id;

    @Basic
    @Column(name = "content_id")
    private Integer content_id;

    @Basic
    @Column(name = "user_id")
    private Integer user_id;

    public Collaborator(Integer content_id, Integer user_id) {
        this.content_id = content_id;
        this.user_id = user_id;
    }

    public Collaborator() {

    }
}
