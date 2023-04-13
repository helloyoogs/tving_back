package com.tving_back.myapp.repository;

import org.springframework.data.repository.CrudRepository;
import com.tving_back.myapp.model.Member;

public interface MemberRepository extends CrudRepository<Member, Long>{
  Member findByUsername(String username);
}
