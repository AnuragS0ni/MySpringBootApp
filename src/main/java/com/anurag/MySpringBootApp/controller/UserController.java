package com.anurag.MySpringBootApp.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anurag.MySpringBootApp.Entity.JournalEntity;
import com.anurag.MySpringBootApp.Entity.User;
import com.anurag.MySpringBootApp.Service.JournalService;
import com.anurag.MySpringBootApp.Service.UserService;
import com.anurag.MySpringBootApp.repositries.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;

	
	 @DeleteMapping
	    public ResponseEntity<?> deleteUserById() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        userRepo.deleteByUserName(authentication.getName());
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Optional<User> userInDbOp = userService.findByUserName(userName);
			User userInDb=userInDbOp.get();
		userInDb.setUserName(user.getUserName());
		userInDb.setPassword(user.getPassword());
		userService.saveNewUser(userInDb);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	
}
