package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.dao.UsersDao;
import ru.otus.domain.User;
import ru.otus.userDetails.UserUD;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersDao usersDao;

    @Autowired
    public UserDetailsServiceImpl(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = usersDao.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new UserUD(user);
    }
}
