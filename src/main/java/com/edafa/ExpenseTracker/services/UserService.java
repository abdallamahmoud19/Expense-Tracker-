package com.edafa.ExpenseTracker.services;

import com.edafa.ExpenseTracker.Repository.UserRepository;

import com.edafa.ExpenseTracker.entities.User;


import com.edafa.ExpenseTracker.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final  PasswordEncoder passwordEncoder;

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findById(Long id){
        return  userRepository.findById(id).orElse(null) ;
    }

    public User findByEmail(String email) {
        return  userRepository.findByEmail(email).orElse(null) ;
    }

    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent()){
        throw  new UsernameNotFoundException("Email not found");
        }

        return new AppUserDetails(user.get());
    }



}
