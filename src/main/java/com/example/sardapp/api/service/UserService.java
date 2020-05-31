package com.example.sardapp.api.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import com.example.sardapp.entities.User;
import io.swagger.models.auth.In;
import org.springframework.data.util.Pair;

public interface UserService
{
    public List<User> findAll();
    public User findByEmail(String email);
    public List<User> findByFilters(List<String> events, List<String> habilitats, Integer edatMin, Integer edatMax, String comarca, Boolean transport);
    public void save(User user) throws InvalidKeySpecException, NoSuchAlgorithmException;
    public void deleteByEmail(String email);
    public User editUser(String email, User user);
    public void addProfileImage(String email, byte[] image) throws IOException;
    public boolean login(String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException;

}
