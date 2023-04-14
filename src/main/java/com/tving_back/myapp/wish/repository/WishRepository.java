package com.tving_back.myapp.wish.repository;

import com.tving_back.myapp.wish.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish,Long> {
}
