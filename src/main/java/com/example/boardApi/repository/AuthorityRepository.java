package com.example.boardApi.repository;

import com.example.boardApi.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository  extends JpaRepository<Authority, Long> {
}
