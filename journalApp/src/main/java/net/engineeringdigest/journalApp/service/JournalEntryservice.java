package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

//@Component
@Service
public class JournalEntryservice {
// controller ---> service-->Repsoitory
 @Autowired
 private JournalEntryRepository journalEntryRepository;

 @Autowired
 private UserService userService;


 private static  final Logger logger = LoggerFactory.getLogger(JournalEntryservice.class);



 @Transactional // If happens should happen alll , if any fails all should fail
public void saveEntry(JournalEntry journalEntry, String username){
    try{
        User user = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    } catch (Exception e) {
        logger.info(String.valueOf(e));
        throw new RuntimeException("Error occured while saving the entry:", e);
    }
}
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

public List<JournalEntry> getAll(){
     return  journalEntryRepository.findAll();
}
public Optional<JournalEntry> findById(ObjectId id){
    return journalEntryRepository.findById(id);
}

@Transactional
public boolean deleteById(ObjectId id,String username){
     boolean removed = false;
 try {
     User user = userService.findByUsername(username);
     removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
     if(removed){
         userService.saveEntry(user);
         journalEntryRepository.deleteById(id);
     }
 }catch (Exception e){
     System.out.println(e);
     throw  new RuntimeException("An error occured while deleting the entry:", e);

 }
 return  removed;
}
//public List<JournalEntry> findByUserName(String username){
//     journalEntryRepository.findByUsername(username);
//}
}
