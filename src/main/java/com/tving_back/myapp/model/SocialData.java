package com.tving_back.myapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class SocialData {

    @Id
    @GeneratedValue
    private int id;
    private String socialId;
    private String email;
    private String type;

    @OneToOne(mappedBy = "social")
    private Member member;

    public SocialData(String socialId, String email, String type) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.type = type;
    }
}