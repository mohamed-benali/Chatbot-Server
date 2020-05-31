package com.example.sardapp.api.dao;

import com.example.sardapp.api.session.AbstractSession;
import com.example.sardapp.entities.Acte;
import com.example.sardapp.entities.Assistent;
import com.example.sardapp.entities.AssistentId;
import com.example.sardapp.entities.User;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class AssistantDAOImpl extends AbstractSession implements AssistantDAO
{
    @Override
    public Assistent findById(Integer id, String email)
    {
        return getSession().get(Assistent.class, new AssistentId(id, email));
    }

    @Override
    public List<User> getAssistants(Integer id)
    {
        return getSession().createQuery("select u from User u, Assistent a where a.id.usuari = u.email and u.publicProfile = true and a.id.acte = " + id).list();
    }

    @Override
    public Boolean newAssistant(Integer id, String email)
    {
        Assistent assistent = new Assistent(new AssistentId(id, email));
        getSession().beginTransaction();
        getSession().saveOrUpdate(assistent);
        getSession().getTransaction().commit();
        return getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public Boolean deleteAssistant(Integer id, String email)
    {
        boolean result = false;
        Assistent assistent = findById(id, email);
        if(assistent != null) {
            getSession().beginTransaction();
            getSession().delete(assistent);
            getSession().getTransaction().commit();
            result = getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED;
        }
        return result;
    }

    @Override
    public List<Acte> getUserActs(String email)
    {
        return getSession().createQuery("select act from Acte act, Assistent a where a.id.usuari = '" + email + "' and a.id.acte = act.id").list();
    }

    @Override
    public List<Acte> getPastUserActs(String email)
    {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return getSession().createQuery("select a.id from Acte act, Assistent a where a.id.usuari = '" + email + "' and a.id.acte = act.id and act.dia < '" + date.format(dateFormatter) + "'").list();
    }

    @Override
    public List<Acte> getNextUserActs(String email)
    {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return getSession().createQuery("select act from Acte act, Assistent a where a.id.usuari = '" + email + "' and a.id.acte = act.id and act.dia >= '" + date.format(dateFormatter) + "'").list();
    }
}
