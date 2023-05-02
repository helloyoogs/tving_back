package com.tving_back.myapp.wish.controller;

import com.tving_back.myapp.wish.model.Wish;
import com.tving_back.myapp.wish.repository.WishRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if (wish.getContent_genres() == null) {
            wish.setContent_genres(new ArrayList<>());
        }

        List<Wish.Genre> genres = new ArrayList<>();
        for (Wish.Genre genre : wish.getContent_genres()) {
            Wish.Genre newGenre = new Wish.Genre();
            newGenre.setId(genre.getId());
            newGenre.setName(genre.getName());
            genres.add(newGenre);
        }
        wish.setContent_genres(genres);

        wish.setUser_id(username);
        return wishRepository.save(wish);
    }

    //수정
    @PutMapping("/{id}")
    public Wish updateWish(@PathVariable Long id, @RequestBody Wish updatedWish) throws NotFoundException {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: "+ id));
            wish.setRemoved_at(updatedWish.getRemoved_at());
        return wishRepository.save(wish);
    }

    //찜한 컨텐츠 출력
    @GetMapping("/list")
    public List<Wish> getWishList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Wish> allWish = wishRepository.findAll();

        if (allWish == null) {
            return null;
        }

        List<Wish> userWishList = allWish.stream()
                .filter(state ->
                        userId.equals(state.getUser_id())
                                && state.getRemoved_at() == null
                                && state.getAdded_at() != null)
                .collect(Collectors.toList());
        return userWishList;
    }
    //이미 찜한 컨텐츠 출력
    @GetMapping("/wishedList")
    public List<Wish> getWishedList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Wish> allWish = wishRepository.findAll();

        if (allWish == null) {
            return null;
        }

        List<Wish> userWishList = allWish.stream()
                .filter(state ->
                        userId.equals(state.getUser_id())
                                && state.getAdded_at() != null)
                .collect(Collectors.toList());
        return userWishList;
    }
}
