package com.anurag.MySpringBootApp.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anurag.MySpringBootApp.Entity.JournalEntity;
import com.anurag.MySpringBootApp.Entity.User;
import com.anurag.MySpringBootApp.repositries.JournalRepository;
import com.anurag.MySpringBootApp.repositries.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//save
	public User save(User entry) {
		entry.setPassword(passwordEncoder.encode(entry.getPassword()));
		entry.getRoles().add("Users");
	return userRepository.save(entry);
	}
//get all

	public List<User> getAll() {
		return userRepository.findAll();
	}

//getById
	public Optional<User> getById(ObjectId id) {
		return userRepository.findById(id);
	}

	// Delete
	public boolean deleteById(ObjectId id) {
		userRepository.deleteById(id);
		return true;
	}

	// update
	public Optional<User> updateEntry(User newEntry) {
		Optional<User> userInDB = userRepository.findByUserName(newEntry.getUserName());
		
		if (userInDB.isPresent()) {
			userInDB.get().setPassword(
					newEntry.getPassword() != null && newEntry.getPassword() != "" ? newEntry.getPassword() :
						userInDB.get().getPassword());
			userRepository.save(userInDB.get());
		}
		
		return userInDB;
	}
}
