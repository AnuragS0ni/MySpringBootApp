package com.anurag.MySpringBootApp.controller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.anurag.MySpringBootApp.Service.JournalService;
import com.anurag.MySpringBootApp.Service.UserService;
import com.anurag.MySpringBootApp.Entity.User;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

	@Autowired
	private JournalService journalService;
	@Autowired
	private UserService userService;

	@GetMapping()
	public ResponseEntity<List<JournalEntity>> getJournal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

		if (journalService.getAll(userName) != null) {
			return new ResponseEntity<>(journalService.getAll(userName), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping()
	public ResponseEntity<?> create(@RequestBody JournalEntity entry) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			journalService.save(entry, userName);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("id/{myId}")
	public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String userName = authentication.getName();
	        Optional<User> userOp = userService.findByUserName(userName);
	        User user=userOp.get();
	        List<JournalEntity> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
	        if (!collect.isEmpty()) {
	            Optional<JournalEntity> journalEntry = journalService.getById(myId);
	            if (journalEntry.isPresent()) {
	                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
	            }
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> getDeleteId(@PathVariable ObjectId myId,@PathVariable String userName) {

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        boolean removed = journalService.deleteById(myId, username);
	        if (removed) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } else{
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
		
	}

	@PutMapping("id/{myId}")
	public ResponseEntity<JournalEntity> updateJournal(@PathVariable ObjectId myId, @RequestBody JournalEntity newEntry) {
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String userName = authentication.getName();
	        Optional<User> userOp = userService.findByUserName(userName);
	        User user=userOp.get();
	        List<JournalEntity> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
	        if (!collect.isEmpty()) {
	            Optional<JournalEntity> journalEntry = journalService.getById(myId);
	            if (journalEntry.isPresent()) {
	            	JournalEntity old = journalEntry.get();
	                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
	                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
	                journalService.saveEntry(old);
	                return new ResponseEntity<>(old, HttpStatus.OK);
	            }
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
