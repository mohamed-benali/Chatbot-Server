package com.example.sardapp.api.dao;

import com.example.sardapp.entities.Intent;

import java.util.List;

public interface IntentDAO
{
    public boolean save(Intent intent);

    public Intent findById(String name);

    String setUpEmptyDB();

    List<Intent> findAll();
}
