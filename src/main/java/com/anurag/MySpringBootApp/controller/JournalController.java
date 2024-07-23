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

@RestController
@RequestMapping("/journal")
public class JournalController {

	@Autowired
	private JournalService journalService;

	@GetMapping("/user/{userName}")
	public ResponseEntity<List<JournalEntity>> getJournal(@PathVariable String userName) {

		if (journalService.getAll(userName) != null) {
			return new ResponseEntity<>(journalService.getAll(userName), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/{userName}")
	public ResponseEntity<?> create(@RequestBody JournalEntity entry,@PathVariable String userName) {
		try {
			 journalService.save(entry,userName);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<JournalEntity> getEntryById(@PathVariable ObjectId id) {

		Optional<JournalEntity> byId = journalService.getById(id);
		if (byId.isPresent())
			return new ResponseEntity<>(byId.get(), HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{userName}/{id}")
	public ResponseEntity<?> getDeleteId(@PathVariable ObjectId id,@PathVariable String userName) {

		boolean deleteById = journalService.deleteById(id,userName);
		 if(deleteById)
			 return new ResponseEntity<>( HttpStatus.NO_CONTENT);
		 return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		
	}

	@PutMapping("/{userName}/{id}")
	public ResponseEntity<JournalEntity> updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntity entry,String userName) {
		Optional<JournalEntity> op=journalService.updateEntry(id, entry,userName);
		if(!op.isEmpty())
		{
			return new ResponseEntity<>(op.get(),HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
}
