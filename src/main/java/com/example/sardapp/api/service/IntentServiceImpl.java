package com.example.sardapp.api.service;

import com.example.sardapp.api.dao.IntentDAO;
import com.example.sardapp.entities.Intent;
import com.google.api.services.dialogflow.v2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IntentServiceImpl implements IntentService
{
    @Autowired
    private IntentDAO intentDAO;


    @Override
    public void save(Intent intent) {
        // Save intent to DB
        intentDAO.save(intent);
    }
    @Override
    public Intent findById(String name) {
        return intentDAO.findById(name);
    }

    @Override
    public String setUpEmptyDB() {
        return intentDAO.setUpEmptyDB();
    }

    @Override
    public List<Intent> findAll() {
        return intentDAO.findAll();
    }

    @Override
    public void processMessage(GoogleCloudDialogflowV2WebhookRequest request, GoogleCloudDialogflowV2WebhookResponse response) {
        GoogleCloudDialogflowV2QueryResult queryResult = request.getQueryResult();
        GoogleCloudDialogflowV2Intent intent = queryResult.getIntent();

        String session = request.getSession();
        String textResponse = queryResult.getFulfillmentText();

        if(intent != null) { // Some intent has been matched
            if(intent.getIsFallback()) {
                //List<GoogleCloudDialogflowV2Context> currentContexts = queryResult.getOutputContexts();
                //response.setOutputContexts(currentContexts);
                response.setFulfillmentText("Sorry, i did not understand");
            }
            else {
                String name = intent.getDisplayName();
                List<GoogleCloudDialogflowV2Context> outputContexts = intent.getOutputContexts(); // Output contexts per defecte

                //Intent myIntent = intentDAO.findById(name);

                //String textResponse = myIntent.getResponse();
                response.setFulfillmentText(textResponse);

                //String outputContext = myIntent.getOutcontext();
                //List<String> outputContextsList = parseOutputContext(outputContext);
                //List<GoogleCloudDialogflowV2Context> currentOutputContext = queryResult.getOutputContexts();
                //List<GoogleCloudDialogflowV2Context> outputContexts= createOutputContexts(outputContextsList, currentOutputContext, session);

                response.setOutputContexts(outputContexts);
            }
        }
        else {
            // Should never enter here, because the fallback intent should be triggered
            response.setFulfillmentText("Something went wrong (no intent has been matched, not even the fallback intent");

        }
    }

    private List<GoogleCloudDialogflowV2Context> createOutputContexts(List<String> outputContextsList,
                                                                      List<GoogleCloudDialogflowV2Context> currentOutputContext,
                                                                      String session)
    {
        List<GoogleCloudDialogflowV2Context> googleOutputContexts = new ArrayList<>();

        for (String outputContext : outputContextsList) {
            GoogleCloudDialogflowV2Context googleCloudDialogflowV2Context = new GoogleCloudDialogflowV2Context();
            googleCloudDialogflowV2Context.setName(session + "/contexts/" + outputContext);
            googleCloudDialogflowV2Context.setLifespanCount(100);

            googleOutputContexts.add(googleCloudDialogflowV2Context);
        }

        return googleOutputContexts;
    }

    private List<String> parseOutputContext(String outputContext) {
        String separator = ";";
        String[] array = outputContext.split(separator);
        return new ArrayList<>(Arrays.asList(array));
    }


}
