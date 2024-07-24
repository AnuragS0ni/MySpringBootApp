package com.anurag.MySpringBootApp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.anurag.MySpringBootApp.Entity.User;
import com.anurag.MySpringBootApp.Service.UserDetailsServiceImpl;
import com.anurag.MySpringBootApp.Service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    
  
    @PostMapping("/create-user")
	public ResponseEntity<User> createUser(@RequestBody User entry) {
		try {
			User save = userService.saveNewUser(entry);
			return new ResponseEntity<>(save, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
    
}
