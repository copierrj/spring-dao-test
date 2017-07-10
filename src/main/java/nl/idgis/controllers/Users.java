package nl.idgis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.idgis.data.UserRepository;
import nl.idgis.model.User;

@RestController
@RequestMapping("/users")
public class Users {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("")
	public Iterable<User> allUsers() {
		return userRepository.findAll();
	}
	
	@RequestMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		User user = userRepository.findOne(id);
		if(user == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(user);
		}
	}
}
