package com.example.sardapp.database;

import com.example.sardapp.api.dao.ActeDAO;
import com.example.sardapp.api.dao.ActeDAOImpl;

public class ActeManage {

    private ActeManage(){
        throw new IllegalStateException("Utility class");
    }

    public static void delete(Integer id)
    {
        ActeDAO users = new ActeDAOImpl();
        users.deleteById(id);
    }
}
