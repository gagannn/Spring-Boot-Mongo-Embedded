package com.example.spring.mongo.embedded.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.mongo.embedded.api.model.User;
import com.example.spring.mongo.embedded.api.repository.DataRepository;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private DataRepository repository;

	@PostMapping("/place")
	public String placeOrder(@RequestBody User user) {
		repository.save(user);
		return "Order placed";
	}

	@GetMapping("/getUserByName/{name}")
	public List<User> getUserByName(@PathVariable String name) {
		return repository.findByName(name);

	}
	
	@GetMapping("/getUserByCity/{city}")
	public List<User> getUserByAddress(@PathVariable String city) {
		return repository.findByCity(city);

	}

	@DeleteMapping("/deleteUserByName/{name}")
	public String deleteUserByName(@PathVariable String name) {
		List<User> users = repository.findByName(name);
		users.forEach(user -> {
			repository.delete(user);
		});
		return "User Deleted";
	}

	@PutMapping("/updateUser")
	public User updateUser(@RequestBody User user) {
		repository.save(user);
		return user;
	}
}
