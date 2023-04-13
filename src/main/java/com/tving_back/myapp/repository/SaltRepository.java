package com.tving_back.myapp.repository;

import org.springframework.data.repository.CrudRepository;
import com.tving_back.myapp.model.Salt;

public interface SaltRepository extends CrudRepository<Salt,Long> {

}
