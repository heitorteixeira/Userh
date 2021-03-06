package com.userh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.userh.entity.User;


public interface UserRepository extends MongoRepository<User, Integer>{

	@Query("{'$or':[ {'name':{ '$regex' : ?0 , $options: 'i'}}, {'email':?1} ] }")
	Page<User> findByNomeOrEmailPage(String name, String email, PageRequest pageRequest);

	User findByEmail(String email);

}
