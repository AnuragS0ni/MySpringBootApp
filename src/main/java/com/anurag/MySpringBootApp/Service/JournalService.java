package com.anurag.MySpringBootApp.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anurag.MySpringBootApp.Entity.JournalEntity;
import com.anurag.MySpringBootApp.Entity.User;
import com.anurag.MySpringBootApp.repositries.JournalRepository;
import com.anurag.MySpringBootApp.repositries.UserRepository;

@Service
public class JournalService {

	@Autowired
	private JournalRepository journalRepository;
	@Autowired
	private UserRepository userRepo;

//save
	public void save(JournalEntity entry,String userName) {
		Optional<User> user=userRepo.findByUserName(userName);
		 if(user.isPresent())
		 {
			 entry.setDate(LocalDateTime.now());
			  journalRepository.save(entry);
			  user.get().getJournalEntries().add(entry);
			  userRepo.save(user.get());
		 }
	}
//get all

	public List<JournalEntity> getAll(String userName) {
		Optional<User> user=userRepo.findByUserName(userName);
		 if(user.isPresent())
		 {
			 return user.get().getJournalEntries();
		 }
		return null;
	}

//getById
	public Optional<JournalEntity> getById(ObjectId id) {
		return journalRepository.findById(id);
	}

	// Delete
	@Transactional
	public boolean deleteById(ObjectId id,String userName) {
		Optional<User> user=userRepo.findByUserName(userName);
		 if(user.isPresent())
		 {
			 journalRepository.deleteById(id);
			 user.get().getJournalEntries().removeIf(x->x.getId().equals(id));
			 userRepo.save(user.get());
		 }
		
		return true;
	}

	// update
	@Transactional
	public Optional<JournalEntity> updateEntry(ObjectId id, JournalEntity newEntry,String userName) {
		Optional<User> user=userRepo.findByUserName(userName);
		Optional<JournalEntity> journalEntity = journalRepository.findById(id);
		
		if (journalEntity.isPresent()) {
			journalEntity.get().setContent(newEntry.getContent() != null && newEntry.getContent() != "" ? newEntry.getContent()
					: journalEntity.get().getContent());
			journalEntity.get().setTitle(
					newEntry.getTitle() != null && newEntry.getTitle() != "" ? newEntry.getTitle() :
						journalEntity.get().getTitle());
			journalRepository.save(journalEntity.get());
		}
		
		return journalEntity;
	}
}
