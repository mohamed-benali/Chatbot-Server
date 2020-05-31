package com.example.sardapp.api.dao;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.example.sardapp.entities.Acte;

public interface ActeDAO {

    List<Acte> findAll();
    List<Acte> findByFilters(List<String> tipus, Date diaMinim, Date diaMaxim, String hora, Boolean anul,
                             List<String> comarca, List<String> territori, List<String> cobla,
                             List<String> poblacioMitjana);
    List<Acte> findAllByTipus(String tipus);
    List<Acte> findAllByDia(Date dia);
    List<Acte> findAllCanceled();
    List<Acte> findAllByComarca(String comarca);
    List<Acte> findAllByTerritori(String territori);
    List<Acte> findAllByPoblacioMitjana(String poblacioMitjana);

    Acte findById(Integer id);

    boolean save(Acte acte);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);

    }
