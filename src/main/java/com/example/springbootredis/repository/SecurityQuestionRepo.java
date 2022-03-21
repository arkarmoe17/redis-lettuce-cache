package com.example.springbootredis.repository;

import com.example.springbootredis.model.entity.SecurityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityQuestionRepo extends JpaRepository<SecurityQuestion,Long> {
}
