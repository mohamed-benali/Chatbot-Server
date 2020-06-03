package com.example.sardapp.api.session;

import com.example.sardapp.entities.Intent;
import com.example.sardapp.hibernate.Factory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AbstractSession
{
    private Session session;
    private static  SessionFactory sessionFactory = Factory.getSessionFactory(Intent.class);

    private void newSession()
    {
        if (session == null) session = sessionFactory.openSession();
    }

    protected Session getSession()
    {
        newSession();
        return session;
    }
}
