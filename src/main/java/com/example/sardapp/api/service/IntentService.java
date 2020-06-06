package com.example.sardapp.api.service;

import com.example.sardapp.entities.Intent;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse;
import com.google.cloud.dialogflow.v2.Context;

import java.util.List;
import java.util.Map;

public interface IntentService
{
    void save(Intent intent);

    void processMessage(GoogleCloudDialogflowV2WebhookRequest request, GoogleCloudDialogflowV2WebhookResponse response) throws Exception;

    Intent findById(String name);

    String setUpEmptyDB();

    List<Intent> findAll();

    List<GoogleCloudDialogflowV2Context> parseOutputContextsToCloudDialogflow(List<Context> outputContextsCompleteIntent, String session);
}
