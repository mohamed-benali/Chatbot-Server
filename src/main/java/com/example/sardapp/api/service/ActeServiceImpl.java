package com.example.sardapp.api.service;

import com.example.sardapp.api.dao.ActeDAO;
import com.example.sardapp.entities.Acte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ActeServiceImpl implements ActeService {

    @Autowired
    private ActeDAO acteDAO;

    @Override
    public List<Acte> findAll() {
        List<Acte> listActs = acteDAO.findAll();
        return listActs;
    }

    @Override
    public List<Acte> findByFilters(List<String> tipus, Date diaMinim, Date diaMaxim, String hora, Boolean anul, List<String> comarca, List<String> territori, List<String> cobla, List<String> poblcioMitjana) {
        List<Acte> listActs = acteDAO.findByFilters(tipus, diaMinim, diaMaxim, hora, anul, comarca, territori, cobla, poblcioMitjana);
        return listActs;
    }

    @Override
    public List<Acte> findAllByTipus(String tipus) {
        List<Acte>listActs = acteDAO.findAllByTipus(tipus);
        return listActs;
    }

    @Override
    public List<Acte> findAllByDia(Date dia) {
        List<Acte>listActs = acteDAO.findAllByDia(dia);
        return listActs;
    }

    @Override
    public List<Acte> findAllCancelled() {
        List<Acte>listActs = acteDAO.findAllCanceled();
        return listActs;
    }

    @Override
    public List<Acte> findAllActsByComarca(String comarca) {
        List<Acte>listActs = acteDAO.findAllByComarca(comarca);
        return listActs;
    }

    @Override
    public List<Acte> findAllActsByTerritori(String territori) {
        List<Acte>listActs = acteDAO.findAllByTerritori(territori);
        return listActs;
    }

    @Override
    public List<Acte> findAllActsByPoblacioMitjana(String poblacioMitjana) {
        List<Acte>listActs = acteDAO.findAllByPoblacioMitjana(poblacioMitjana);
        return listActs;
    }

    @Override
    public Acte findById(Integer id) {
        Acte act = acteDAO.findById(id);
        return act;
    }

    @Override
    public void save(Acte act) {
        acteDAO.save(act);
    }

    @Override
    public void deleteById(Integer id) {
        acteDAO.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return acteDAO.existsById(id);
    }


}
