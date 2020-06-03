package com.example.sardapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "intents")
public class Intent
{
    @Id @Column(name = "nom")           private String nom;
    @Column(name = "response")          private String response;
    @Column(name = "outcontext")        private String outcontext;

    public Intent() {}

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getResponse() { return response; }

    public void setResponse(String response) { this.response = response; }

    public String getOutcontext() { return outcontext; }

    public void setOutcontext(String outcontext) { this.outcontext = outcontext; }


}
