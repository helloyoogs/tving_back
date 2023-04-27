package com.tving_back.myapp.wish.controller;

import com.tving_back.myapp.wish.model.Wish;
import com.tving_back.myapp.wish.repository.WishRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wish")
public class WishController {
    @Autowired
    private WishRepository wishRepository;

    //전체 조회
    @GetMapping
    public List<Wish> getAllWish() {
        return wishRepository.findAll();
    }
    //저장
    @PostMapping("/dataSave")
    public Wish createWish(@RequestBody Wish wish) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        wish.setUser_id(username);
        return wishRepository.save(wish);
    }

    //수정
    @PutMapping("/{id}")
    public Wish updateWish(@PathVariable Long id, @RequestBody Wish updatedWish) throws NotFoundException {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: "+ id));
        return wishRepository.save(wish);
    }

    //찜한 컨텐츠 출력
    @GetMapping("/list")
    public Wish getWish() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Wish> allWish = wishRepository.findAll();

        if (allWish == null) {
            return null;
        }


        Optional<Wish> wishOptional = allWish.stream()
                .filter(state ->
                        userId.equals(state.getUser_id())
                && state.getRemoved_at() == null
                && state.getAdded_at() != null)
                .findFirst();
        return wishOptional.orElse(null);
    }

}
