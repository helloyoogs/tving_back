package com.tving_back.myapp.wish.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Wish {
    @Id
    @Column(name = "wish_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user_id;
    private Long content_id;

    @ElementCollection
    @CollectionTable(name="content_genres")
    private List<Genre> content_genres = new ArrayList<>();

    private String content_title;
    private String content_poster;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date added_at;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date removed_at;
    public Wish() {
        this.added_at = new Date();
        this.removed_at = new Date();
    }

    @Embeddable
    @Getter
    @Setter
    public static class Genre {
        @Column(name = "genre_id")
        private int id;
        @Column(name = "genre_name")
        private String name;

        public Genre() {}

        public Genre(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}
