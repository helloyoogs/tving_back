package com.tving_back.myapp.content.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Array;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;
    private Array genre_ids;
    private Array title;
    private String poster_path;
}
