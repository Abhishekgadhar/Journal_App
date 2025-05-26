package net.engineeringdigest.journalApp.controller;


import com.sun.org.apache.bcel.internal.generic.NEW;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryservice;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryservice journalEntryservice;
@Autowired
private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username =authentication.getName();
        User user= userService.findByUsername(username);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){

        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username =authentication.getName();
               journalEntryservice.saveEntry(myEntry,username);
               return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
           } catch (Exception e) {
               return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
    }

    //@RequestBody -> spring pls take data from the request and turn it into a java object
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
         Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
         String username= authentication.getName();
         User user = userService.findByUsername(username);
         List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
         if(!collect.isEmpty()){
             Optional<JournalEntry> journalEntry=journalEntryservice.findById(myId);

             if(journalEntry.isPresent()){
                 return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
             }
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username =authentication.getName();
       boolean removed = journalEntryservice.deleteById(myId,username);
        if(removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return  new ResponseEntity<>((HttpStatus.NOT_FOUND));
    }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username =authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry= journalEntryservice.findById(myId);
            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
                journalEntryservice.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}

//controllers call services
}
