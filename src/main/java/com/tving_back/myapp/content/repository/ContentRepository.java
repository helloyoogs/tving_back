package com.tving_back.myapp.content.repository;

import com.tving_back.myapp.content.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content,Long>  {
}
