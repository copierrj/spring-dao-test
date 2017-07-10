package nl.idgis.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.idgis.model.User;

@RestController
@RequestMapping("/users")
public class Users {

	@RequestMapping("")
	public List<User> allUsers() {
		return Arrays.asList(new User("copierrj", "Reijer Copier"));
	}
}
