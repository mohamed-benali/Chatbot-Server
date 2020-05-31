package com.example.sardapp.api.service;

import com.example.sardapp.entities.Acte;

import java.sql.Date;
import java.util.List;

public interface ActeService {

    List<Acte> findAll();
    List<Acte> findByFilters(List<String> tipus, Date diaMinim, Date diaMaxim,
                             String hora, Boolean anul, List<String> comarca,
                             List<String> territori, List<String> cobla, List<String> poblcioMitjana);
    List<Acte> findAllByTipus(String tipus);
    List<Acte> findAllByDia(Date dia);
    List<Acte> findAllCancelled();
    List<Acte> findAllActsByComarca(String comarca);
    List<Acte> findAllActsByTerritori(String territori);
    List<Acte> findAllActsByPoblacioMitjana(String poblacioMitjana);

    Acte findById(Integer id);

    void save(Acte acte);
    void deleteById(Integer id);
    boolean existsById(Integer id);

}
