package com.example.sardapp.api.service;

import com.example.sardapp.entities.Assistent;
import com.example.sardapp.entities.User;
import com.example.sardapp.entities.Acte;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface AssistentService
{
    public Assistent findById(Integer id, String email);
    public List<User> getAssistants(Integer id);
    public Boolean newAssistant(Integer id, String email);
    public Boolean deleteAssistant(Integer id, String email);

    public List<Acte> getUserActs(String email);
    public List<Acte> getPastUserActs(String email);
    public List<Acte> getNextUserActs(String email);
}
