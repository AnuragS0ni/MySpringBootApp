package com.anurag.MySpringBootApp.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@GetMapping
	public ResponseEntity<List<User >> getJournal() {

		if (userService.getAll() != null && !userService.getAll().isEmpty()) {
			return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}



	@GetMapping("/{id}")
	public ResponseEntity<User> getEntryById(@PathVariable ObjectId id) {

		Optional<User> byId = userService.getById(id);
		if (byId.isPresent())
			return new ResponseEntity<>(byId.get(), HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> getDeleteId(@PathVariable ObjectId id) {

		boolean deleteById = userService.deleteById(id);
		 if(deleteById)
			 return new ResponseEntity<>( HttpStatus.NO_CONTENT);
		 return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		
	}

	@PutMapping()
	public ResponseEntity<User> updateUser( @RequestBody User entry) {
		Optional<User> op=userService.updateEntry(entry);
		if(!op.isEmpty())
		{
			return new ResponseEntity<>(op.get(),HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	
}
