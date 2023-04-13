package com.tving_back.myapp.usage.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}, ignoreUnknown = true)
public class UsageState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_state_id")
    private Long id;

     @Column(name = "user_id")
    private String user_id;

    private Long subscription_id;

    private String imp_uid;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
   private Date start_date;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date end_date;

    private String subscription_check;

    public UsageState() {
        this.start_date = new Date();
    }
    @PrePersist
    public void setEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.start_date);
        cal.add(Calendar.MONTH, 1);
        this.end_date = cal.getTime();
        //만료일이 되면 구독체크 false
        if (new Date().after(this.end_date)) {
            this.subscription_check = "false";
        }
    }

}
