package com.tving_back.myapp.subscription.controller;

import com.tving_back.myapp.subscription.model.Subscription;
import com.tving_back.myapp.subscription.repository.SubscriptionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // 모든 게시물 조회
    @GetMapping
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    // 구독 상품 종류 넣기
    @PostConstruct
    public void createSubscriptions() {
        Subscription basicSubscription = new Subscription();
        basicSubscription.setName("베이직");
        basicSubscription.setPrice(7900);
        subscriptionRepository.save(basicSubscription);

        Subscription standardSubscription = new Subscription();
        standardSubscription.setName("스탠다드");
        standardSubscription.setPrice(10900);
        subscriptionRepository.save(standardSubscription);

        Subscription premiumSubscription = new Subscription();
        premiumSubscription.setName("프리미엄");
        premiumSubscription.setPrice(12500);
        subscriptionRepository.save(premiumSubscription);
    }

}
