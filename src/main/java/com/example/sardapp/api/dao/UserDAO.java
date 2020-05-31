package com.example.sardapp.api.dao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.example.sardapp.entities.User;

public interface UserDAO
{
    public List<User> findAll();
    public User findByEmail(String email);
    public List<User> findByFilters(List<String> events, List<String> habilitats, Date minDate, Date maxDate, String comarca, Boolean transport);
    public boolean save(User user);
    public boolean deleteByEmail(String email);
    public User editUser(String email, User user);
    public boolean addProfileImage(String email, byte[] image) throws IOException;

}
