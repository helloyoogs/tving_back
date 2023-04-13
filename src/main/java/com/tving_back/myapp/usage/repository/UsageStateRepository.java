package com.tving_back.myapp.usage.repository;

import com.tving_back.myapp.usage.model.UsageState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageStateRepository extends JpaRepository<UsageState, Long> {
}
