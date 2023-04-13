package com.tving_back.myapp.subscription.repository;

import com.tving_back.myapp.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
