package com.ran.apps.saad.service;

import java.util.List;
import java.util.Optional;

import com.ran.apps.saad.MyUserDetails;
import com.ran.apps.saad.model.User;
import com.ran.apps.saad.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = repo.findByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return user.map(MyUserDetails::new).get(); // new MyUserDetails = user.get();
    }

    
    /**
     * Get a List<User> with all the registered user
     * 
     * @return list
     */
    public List<User> getUsers() {
        return (List<User>) repo.findAll();
    }

    /**
     * Get an User entity by the given id
     * 
     * @param id 
     * @return the requested user or none 
     */
    public User getUserById(Integer id) {
        Optional<User> user = repo.findById(id);
        if (user.isPresent()) {
            return repo.findById(id).get();
        }
        return user.get();
    }

    /**
     * Create or update User
     * 
     * @param e an user entity to be saved or updated
     * @return the entered user entity
     */
    public User saveUser(User e) {
        /* Create new tuple */
        if (e.getId() == null) {
            e = repo.save(e);
            return e;
        } else {

            Optional<User> user = repo.findById(e.getId());

            if (user.isPresent()) {

                /** builds new user entity for updating */
                User newUser = user.get();
                newUser.setId(e.getId());
                newUser.setUserName(e.getUserName());
                newUser.setPassword(e.getPassword());
                newUser.setActive(e.isActive());
                newUser.setRoles(e.getRoles());
                /* Delete old tuple */
                repo.deleteById(e.getId());

                /* Save new tuple */
                newUser = repo.save(newUser);

                return newUser;
            } else {
                e = repo.save(e);
                return e;
            }
        }
    }

    /**
     * Delete an user tuple by the given id
     * 
     * @param id
     */
    public void deleteUser(Integer id) {

        Optional<User> user = repo.findById(id);

        if (user.isPresent()) {
            repo.deleteById(id);
        }
    }

}