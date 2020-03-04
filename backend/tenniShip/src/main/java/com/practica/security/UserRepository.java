package com.practica.security;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUserName(String userName);

	Optional<User> findByEmail(String email);
}
