package com.example.sardapp.api.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.sardapp.api.dao.UserDAOImpl;
import com.ja.security.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sardapp.entities.User;
import com.example.sardapp.api.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> findAll()
    {
        List<User> listUsers= userDAO.findAll();
        return listUsers;
    }

    @Override
    public User findByEmail(String email)
    {
        User user = userDAO.findByEmail(email);
        return user;
    }

    @Override
    public List<User> findByFilters(List<String> events, List<String> habilitats, Integer edatMin, Integer edatMax, String comarca, Boolean transport)
    {
        Date minDate = null;
        Date maxDate = null;

        if (edatMin != null && edatMax != null)
        {
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();

            int currentYear = cal.get(Calendar.YEAR);
            int currentMonth = cal.get(Calendar.MONTH);
            int currentDay = cal.get(Calendar.DAY_OF_MONTH);

            int minYear = currentYear - edatMax;
            int maxYear = currentYear - edatMin;

            minDate = new GregorianCalendar(minYear, currentMonth, currentDay).getTime();
            maxDate = new GregorianCalendar(maxYear, currentMonth, currentDay).getTime();
        }

        List<User> users = userDAO.findByFilters(events, habilitats, minDate, maxDate, comarca, transport);

        return users;
    }

    @Override
    public void save(User user) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        // Convert password to hash and set it
        String hashedPassword = new PasswordHash().createHash(user.getPassword());
        user.setPassword(hashedPassword);

        // Save user to DB
        userDAO.save(user);
    }

    @Override
    public void deleteByEmail(String email)
    {
        userDAO.deleteByEmail(email);
    }

    @Override
    public User editUser(String email, User user)
    {
        User updatedUser = userDAO.editUser(email, user);
        return updatedUser;
    }

    @Override
    public void addProfileImage(String email, byte[] image) throws IOException
    {
        userDAO.addProfileImage(email, image);
    }

    @Override
    public boolean login(String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        User user = new UserDAOImpl().findByEmail(email);

        try {
            return new PasswordHash().validatePassword(password, user.getPassword());
        }
        catch (NullPointerException e){
            return false;
        }
    }

}
