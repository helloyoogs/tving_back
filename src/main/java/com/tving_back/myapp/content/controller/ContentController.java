package com.tving_back.myapp.content.controller;

import com.tving_back.myapp.content.model.Content;
import com.tving_back.myapp.content.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentRepository contentRepository;

    // 모든 게시물 조회
    @GetMapping
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    // 콘텐츠 더미 데이터 저장
    @PostMapping
    public Content createContent(@RequestBody Content content) {
        return contentRepository.save(content);
    }

}
