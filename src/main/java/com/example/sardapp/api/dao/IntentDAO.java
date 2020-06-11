package com.example.sardapp.api.dao;

import com.example.sardapp.entities.Intent;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;

import java.util.List;

public interface IntentDAO
{
    public boolean save(Intent intent);

    public Intent findById(int name);

    String setUpEmptyDB();

    List<Intent> findAll();

    void pushStackContext(String textResponse, List<GoogleCloudDialogflowV2Context> outputContexts);

    Intent topStack();

    void popStackContext(Intent stackElement);


    void setUpDB(List<GoogleCloudDialogflowV2Context> outputContexts, String textResponse);
}
