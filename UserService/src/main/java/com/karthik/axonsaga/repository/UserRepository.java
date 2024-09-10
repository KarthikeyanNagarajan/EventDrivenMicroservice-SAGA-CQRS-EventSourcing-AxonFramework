package com.karthik.axonsaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.axonsaga.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
