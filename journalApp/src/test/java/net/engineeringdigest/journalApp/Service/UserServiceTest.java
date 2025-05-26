package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testFindByUsername(){
        assertEquals(4,2+2);
        assertNotNull(userRepository.findByUsername("ram"));
        User user= userRepository.findByUsername("ram");
        assertTrue(user.getJournalEntries().isEmpty());
    }
    @ParameterizedTest
    @CsvSource({
             "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a,int b ,int expected){
        assertEquals(expected , a+b);
    }


    @ParameterizedTest
    @ValueSource( strings =
            {
                    "ram",
                    "krishna",
                    "radhakrishna"

            }
    )
    public void test2(String username){
        assertNotNull(userRepository.findByUsername(username),"faied for:"+username);
    }

//we can create a custome source
    @Autowired
    private UserRepositoryImpl userRepositoryimpl;
    @Test
    public  void TestsaveNewUser(){
        Assertions.assertNotNull(userRepositoryimpl.getUserForSA());
    }

}
