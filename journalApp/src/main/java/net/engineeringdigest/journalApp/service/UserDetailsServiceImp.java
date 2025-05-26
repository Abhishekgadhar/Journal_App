package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
@Service
public class UserDetailsServiceImp implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user =userRepository.findByUsername(username);
       if(user!=null){
         return  org.springframework.security.core.userdetails.User.builder()
                   .username(user.getUsername())
                   .password(user.getPassword())
                   .roles(user.getRoles().toArray(new String[0]))
                   .build();

       }
       throw new UsernameNotFoundException("User not found with username:" + username);
    }
}
