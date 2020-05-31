package com.example.sardapp.hibernate;

import org.hibernate.SessionBuilder;

import java.io.File;

public class HibernateUtil
{
    private static File f = new File ("src/main/resources/hibernate.cfg.xml");

    private HibernateUtil()
    {
        throw new IllegalStateException("Utility class");
    }

    public static File getConfigFile()
    {
        return f;
    }
}
