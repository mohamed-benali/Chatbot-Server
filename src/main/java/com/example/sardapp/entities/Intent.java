package com.example.sardapp.entities;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Table(name = "intents")
public class Intent
{
    @Id @Column(name = "numero")            private int numero;
    @Column(name = "response")              private String response;
    @Column(name = "intent")                private String intent;

    public Intent() {}

    public String getResponse() { return response; }

    public void setResponse(String response) { this.response = response; }

    public String getIntent() { return intent; }

    public void setIntent(String outcontext) { this.intent = outcontext; }


    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

}
