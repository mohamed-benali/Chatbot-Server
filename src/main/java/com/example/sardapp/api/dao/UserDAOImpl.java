package com.example.sardapp.api.dao;

import java.io.IOException;
import java.util.*;

import com.example.sardapp.entities.User;
import com.example.sardapp.api.session.AbstractSession;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;

@Repository
public class UserDAOImpl extends AbstractSession implements UserDAO
{
    @Override
    public List<User> findAll()
    {
        return getSession().createQuery("select u from User u where u.publicProfile = true").list();
    }

    @Override
    public User findByEmail(String email)
    {
        return getSession().get(User.class, email);
    }

    @Override
    public List<User> findByFilters(List<String> events, List<String> habilitats, Date minDate, Date maxDate, String comarca, Boolean transport)
    {
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.isTrue(user.get("publicProfile")));

        if (comarca != null)
        {
            Path<String> comarcaPath = user.get("comarca");
            predicates.add(cb.like(comarcaPath, comarca));
        }

        if (transport != null)
        {
            Path<Boolean> transportPath = user.get("vehicle");
            predicates.add(cb.equal(transportPath, transport));
        }

        if (minDate != null && maxDate != null)
        {
            Path<Date> birthdayPath = user.get("birthday");
            predicates.add(cb.between(birthdayPath, minDate, maxDate));
        }

        if (events != null)
        {
            Path<Boolean> aplecsPath = user.get("aplecs");
            Path<Boolean> balladesPath = user.get("ballades");
            Path<Boolean> concertsPath = user.get("concerts");
            Path<Boolean> concursosPath = user.get("concursos");
            Path<Boolean> cursetsPath = user.get("cursets");
            Path<Boolean> altresPath = user.get("altres");

            for (String event : events)
            {
                if (event.equals("aplecs")) predicates.add(cb.isTrue(aplecsPath));
                else if (event.equals("ballades")) predicates.add(cb.isTrue(balladesPath));
                else if (event.equals("concerts")) predicates.add(cb.isTrue(concertsPath));
                else if (event.equals("concursos")) predicates.add(cb.isTrue(concursosPath));
                else if (event.equals("cursets")) predicates.add(cb.isTrue(cursetsPath));
                else if (event.equals("altres")) predicates.add(cb.isTrue(altresPath));
            }
        }

        if (habilitats != null)
        {
            Path<Boolean> comptarPath = user.get("comptarRepartir");
            Path<Boolean> competidorPath = user.get("competidor");
            Path<Boolean> coblaCompeticioPath = user.get("coblaCompeticio");

            for (String habilitat : habilitats)
            {
                if (habilitat.equals("comptar")) predicates.add(cb.isTrue(comptarPath));
                else if (habilitat.equals("competidor")) predicates.add(cb.isTrue(competidorPath));
                else if (habilitat.equals("coblaCompeticio")) predicates.add(cb.isTrue(coblaCompeticioPath));
            }
        }

        query.select(user).where(predicates.toArray(new Predicate[predicates.size()]));

        List<User> users = getSession().createQuery(query).list();
        return users;
    }

    @Override
    public boolean save(User user)
    {
        getSession().beginTransaction();
        getSession().saveOrUpdate(user);
        getSession().getTransaction().commit();
        return getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public boolean deleteByEmail(String email)
    {
        boolean result = false;
        User user = findByEmail(email);
        if(user != null) {
            getSession().beginTransaction();
            getSession().delete(user);
            getSession().getTransaction().commit();
            result = getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED;
        }
        return result;
    }

    @Override
    public User editUser(String email, User user)
    {
        User oldUser = findByEmail(email);
        BeanUtils.copyProperties(user, oldUser, getNullPropertyNames(user));
        getSession().beginTransaction();
        getSession().saveOrUpdate(oldUser);
        getSession().getTransaction().commit();
        //if (getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED)
        return oldUser;
    }

    @Override
    public boolean addProfileImage(String email, byte[] image) throws IOException
    {
        // Update image in user
        User user = findByEmail(email);
        user.setImage(image);
        getSession().beginTransaction();
        getSession().saveOrUpdate(user);
        getSession().getTransaction().commit();
        return getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED;
    }


    // Other functions
    public static String[] getNullPropertyNames (Object source)
    {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
