package com.tving_back.myapp.payment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tving_back.myapp.usage.model.UsageState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_state_id")
    private UsageState usageState;
    private String imp_uid;

    private String user_id;

    private Long subscription_id;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date payment_date;
    private int origin_amount;
    private int payment_amount;
    @ColumnDefault("false")
    private String pay_check;
    public Payment() {
        this.payment_date = new Date();
    }

    // Getter, Setter, Constructor, toString 생략
}
