package com.catering.repository;

import org.springframework.data.repository.CrudRepository;

import com.catering.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}