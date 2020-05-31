package com.example.sardapp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class AssistentId implements Serializable
{
    @NotNull @Column(name = "acte")      private Integer acte;
    @NotNull @Column(name = "usuari")    private String usuari;

    public AssistentId() {}

    public AssistentId(Integer idActe, String emailUsuari)
    {
        this.acte = idActe;
        this.usuari = emailUsuari;
    }

    public Integer getActe()
    {
        return acte;
    }

    public void setActe(Integer acte)
    {
        this.acte = acte;
    }

    public String getUsuari()
    {
        return usuari;
    }

    public void setUsuari(String usuari)
    {
        this.usuari = usuari;
    }
}
