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


    // 기본 게시물 작성
//    @PostMapping
//    public Subscription createSubscription(@RequestBody Subscription subscription) {
//        return subscriptionRepository.save(subscription);
//    }
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

    // 게시물 수정
    @PutMapping("/{id}")
    public Subscription updateSubscription(@PathVariable Long id, @RequestBody Subscription updatedSubscription) throws NotFoundException{
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: "+ id));
        subscription.setName(updatedSubscription.getName());
        subscription.setPrice(updatedSubscription.getPrice());
        return subscriptionRepository.save(subscription);
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubscription(@PathVariable Long id) throws NotFoundException{
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: "+ id));
        subscriptionRepository.delete(subscription);
        return ResponseEntity.ok().build();
    }
}
