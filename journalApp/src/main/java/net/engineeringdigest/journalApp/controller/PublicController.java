package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    private UserService userService;


    @PostMapping("/create-user")
    public void createuser(@RequestBody User user){
        userService.saveNewUser(user);
    }



    // all endpoints are converted to json
    @GetMapping("/health-check")
    // healthcheck() is mapped to /health-check
    public String healthCheck(){
        // if u go to the ./healthcheck() with get  the control will come here
        return "ok";
    }


}
