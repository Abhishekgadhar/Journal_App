package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Scheduler.UserScheduler;
import net.engineeringdigest.journalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {


    @Autowired
    private  EmailService emailService;

    @Autowired
    private UserScheduler userScheduler;

    @Test
    void testsendmail(){
        emailService.sendEmail("anirudhgadhar2004@gmail.com","Testing java mail","Hi, aap kaise haii");
    }
    @Test
    void Testsentimnet(){
        userScheduler.Fetchusersandsendmail();
    }
}
