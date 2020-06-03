package com.example.sardapp.api.service;

import com.example.sardapp.entities.Intent;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse;

import java.util.List;
import java.util.Map;

public interface IntentService
{
    void save(Intent intent);

    void processMessage(GoogleCloudDialogflowV2WebhookRequest request, GoogleCloudDialogflowV2WebhookResponse response);

    Intent findById(String name);

    String setUpEmptyDB();

    List<Intent> findAll();
}
