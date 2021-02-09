package com.example.spring.mongo.embedded.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.spring.mongo.embedded.api.model.User;

public interface DataRepository extends MongoRepository<User, Integer>{

	List<User> findByName(String name);

	@Query("{'Address.city':?0}")
	List<User> findByCity(String city);

}
