package com.example.sardapp.api.dao;

import com.example.sardapp.api.session.AbstractSession;
import com.example.sardapp.entities.Intent;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IntentDAOImpl extends AbstractSession implements IntentDAO
{


    @Override
    public boolean save(Intent intent) {
        getSession().beginTransaction();
        getSession().saveOrUpdate(intent);
        getSession().getTransaction().commit();
        return getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public Intent findById(String name) {
        return getSession().get(Intent.class, name);
    }

    @Override
    public List<Intent> findAll() {
        return getSession().createQuery("select i from Intent i ").list();
    }

    @Override
    public String setUpEmptyDB() {
        getSession().beginTransaction();
        getSession().createSQLQuery("CREATE TABLE IF NOT EXISTS intents (nom varchar(255), " +
                                                                            "response varchar(255), " +
                                                                            "outcontext varchar(255))").executeUpdate();
        getSession().createSQLQuery("DELETE FROM intents").executeUpdate();

        getSession().getTransaction().commit();
        boolean correct =  getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED;

        return correct? "Committed correctly" : "Something went wrong";
    }

}
