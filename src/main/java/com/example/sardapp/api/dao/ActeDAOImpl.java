package com.example.sardapp.api.dao;

import com.example.sardapp.api.session.AbstractSession;
import com.example.sardapp.entities.Acte;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActeDAOImpl extends AbstractSession implements ActeDAO {
    @Override
    public List<Acte> findAll() {
        return getSession().createQuery("FROM Acte").list();
    }

    @Override
    public Acte findById(Integer id) {
        return getSession().get(Acte.class, id);
    }

    @Override
    public List<Acte> findByFilters(List<String> tipus, Date diaMinim, Date diaMaxim, String hora, Boolean anul,
                                    List<String> comarca, List<String> territori, List<String> cobla, List<String> poblacioMitjana) {
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<Acte> query = cb.createQuery(Acte.class);
        Root<Acte> acts = query.from(Acte.class);

        List<Predicate> predicates = new ArrayList<>();

        if (tipus != null)
        {
            Predicate acumuladorDeTipus = cb.like(acts.get("tipus"),tipus.get(0));
            for (String type : tipus)
            {
                acumuladorDeTipus = cb.or(acumuladorDeTipus, cb.like(acts.get("tipus"), type));
            }
            predicates.add(acumuladorDeTipus);
        }

        if (diaMinim != null || diaMaxim !=null)
        {
            if(diaMaxim == null && diaMinim != null) {
                predicates.add(cb.greaterThanOrEqualTo(acts.get("dia"),diaMinim));
            }
            else if (diaMaxim != null && diaMinim == null) {
                predicates.add(cb.lessThanOrEqualTo(acts.get("dia"),diaMaxim));
            }
            else {
                predicates.add(cb.between(acts.get("dia"), diaMinim, diaMaxim));
            }
        }

        if (hora != null)
        {
            predicates.add(cb.or(cb.like(acts.get("hora1"), hora), cb.like(acts.get("hora2"), hora), cb.like(acts.get("hora3"), hora)));
        }

        if (anul != null)
        {
            if (anul){
                predicates.add(cb.isNotNull(acts.get("anul")));
            }
            else {
                predicates.add(cb.isNull(acts.get("anul")));
            }
        }

        if (comarca != null)
        {
            Predicate acumuladorDeComarques = cb.like(acts.get("comarca"), comarca.get(0));
            for (String region : comarca)
            {
                acumuladorDeComarques = cb.or(acumuladorDeComarques, cb.like(acts.get("comarca"), region));
            }
            predicates.add(acumuladorDeComarques);
        }

        if (territori != null)
        {
            Predicate acumuladorDeTerritoris = cb.like(acts.get("territori"), territori.get(0));
            for (String territory : territori)
            {
                acumuladorDeTerritoris = cb.or(acumuladorDeTerritoris, cb.like(acts.get("territori"), territory));
            }
            predicates.add(acumuladorDeTerritoris);
        }

        if (cobla != null)
        {
            for (String cobles: cobla)
            {
                predicates.add(cb.or(cb.like(acts.get("cobla1"), cobles), cb.like(acts.get("cobla2"), cobles),
                        cb.like(acts.get("cobla3"), cobles), cb.like(acts.get("cobla4"), cobles),
                        cb.like(acts.get("cobla5"), cobles), cb.like(acts.get("cobla6"), cobles),
                        cb.like(acts.get("cobla7"), cobles)));
            }
        }

        if (poblacioMitjana != null)
        {
            Predicate acumuladorDePoblacions = cb.like(acts.get("poblacioMitjana"), poblacioMitjana.get(0));
            for (String population : poblacioMitjana)
            {
                acumuladorDePoblacions = cb.or(acumuladorDePoblacions, cb.like(acts.get("poblacioMitjana"), population));
            }
            predicates.add(acumuladorDePoblacions);
        }

        query.select(acts).where(predicates.toArray(new Predicate[predicates.size()]));
        List<Acte> listActs = getSession().createQuery(query).list();
        return listActs;
    }

    @Override
    public List<Acte> findAllByTipus(String tipus) {
        return getSession().createQuery("FROM Acte WHERE tipus = '"+tipus+"'").list();
    }

    @Override
    public List<Acte> findAllByDia(Date dia) {
        return getSession().createQuery("FROM Acte WHERE dia = '"+dia+"'").list();
    }

    @Override
    public List<Acte> findAllCanceled() {
        return getSession().createQuery("FROM Acte WHERE anul IS NOT NULL").list();
    }

    @Override
    public List<Acte> findAllByComarca(String comarca) {
        return getSession().createQuery("FROM Acte WHERE comarca = '"+comarca+"'").list();
    }

    @Override
    public List<Acte> findAllByTerritori(String territori) {
        return getSession().createQuery("FROM Acte WHERE territori = "+territori+"'").list();
    }

    @Override
    public List<Acte> findAllByPoblacioMitjana(String poblacioMitjana) {
        return getSession().createQuery("FROM Acte WHERE poblacioMitjana = "+poblacioMitjana+"'").list();
    }

    @Override
    public boolean save(Acte acte) {
        getSession().beginTransaction();
        getSession().saveOrUpdate(acte);
        getSession().getTransaction().commit();
        return getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean exists = existsById(id);
        if(exists) {
            Acte acte = findById(id);
            getSession().beginTransaction();
            getSession().delete(acte);
            getSession().getTransaction().commit();
            exists = getSession().getTransaction().getStatus() == TransactionStatus.COMMITTED;
        }
        return exists;
    }

    @Override
    public boolean existsById(Integer id) {
        boolean result = false;
        Acte acte = findById(id);
        if(acte != null) {
            result = true;
        }
        return result;
    }
}
