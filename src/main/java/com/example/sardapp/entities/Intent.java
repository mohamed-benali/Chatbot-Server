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
    @Column(name = "response")          private String response;
    @Column(name = "outcontext")        private String outcontext;

    public Intent() {}

    public String getResponse() { return response; }

    public void setResponse(String response) { this.response = response; }

    public String getOutcontext() { return outcontext; }

    public void setOutcontext(String outcontext) { this.outcontext = outcontext; }


    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public List<GoogleCloudDialogflowV2Context> parseOutContexts() {
        List<String> outContextsList = parseOutputContext(this.getOutcontext());

        List<GoogleCloudDialogflowV2Context> googleCloudDialogflowV2ContextsRESULT = new ArrayList<>();

        for(String outContext : outContextsList) {
            GoogleCloudDialogflowV2Context googleCloudDialogflowV2Context = new GoogleCloudDialogflowV2Context();
            googleCloudDialogflowV2Context.setName(outContext);
            googleCloudDialogflowV2Context.setLifespanCount(100);

            googleCloudDialogflowV2ContextsRESULT.add(googleCloudDialogflowV2Context);
        }
        return googleCloudDialogflowV2ContextsRESULT;
    }

    private List<String> parseOutputContext(String outputContext) {
        String separator = ";";
        String[] array = outputContext.split(separator);
        return new ArrayList<>(Arrays.asList(array));
    }
}
