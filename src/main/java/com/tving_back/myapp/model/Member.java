package com.tving_back.myapp.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.tving_back.myapp.config.UserRole;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Members")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private int seq;

    @Column(unique = true)
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotNull
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="social_id")
    private SocialData social;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_NOT_PERMITTED;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salt_id")
    private Salt salt;

    public Member() {
    }

    public Member(@NotBlank String username, @NotBlank String password, @NotBlank String name, @NotBlank String email, @NotBlank String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "seq=" + seq +
                ", id='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}