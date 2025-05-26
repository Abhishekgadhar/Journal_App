package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WheatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WheatherService wheatherService;


    @PutMapping
    public ResponseEntity<?> updateuser(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username =authentication.getName();
        User userindb = userService.findByUsername(username);
        userindb.setUsername(user.getUsername());
        userindb.setPassword(user.getPassword());
        userService.saveEntry(userindb);
        return  new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteuser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/id/{id}")
//    public Optional<User> getbyId(@PathVariable  ObjectId id){
//        return  userService.findById(id);
//    }
      @GetMapping
     public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = wheatherService.getWeather( "Mumbai" );
        String gretting="";
        if(weatherResponse!=null){
             gretting = " Weather feeks like " + weatherResponse.getCurrent().getFeelslike();
        }

          return new ResponseEntity<>("Hi " + authentication.getName() + gretting, HttpStatus.OK);

      }



}
