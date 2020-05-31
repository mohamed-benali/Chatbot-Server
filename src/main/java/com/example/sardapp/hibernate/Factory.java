package com.example.sardapp.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

import java.io.File;

public class Factory
{
    private Factory()
    {
        throw new IllegalStateException("Utility class");
    }

    @Bean
    public static SessionFactory getSessionFactory(Class tipoEntity){
        File f = HibernateUtil.getConfigFile();
        return new Configuration().configure(f).addAnnotatedClass(tipoEntity).buildSessionFactory();
    }

}
