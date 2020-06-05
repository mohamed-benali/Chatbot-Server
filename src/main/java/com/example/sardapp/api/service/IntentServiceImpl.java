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
        System.out.println("Break 3.1");

        GoogleCloudDialogflowV2QueryResult queryResult = request.getQueryResult();
        System.out.println("Break 3.2");

        GoogleCloudDialogflowV2Intent intent = queryResult.getIntent();
        System.out.println("Break 3.3");

        String session = request.getSession();
        System.out.println("Break 3.4");

        String textResponse = queryResult.getFulfillmentText();
        System.out.println("Break 3.5");

        if(intent != null) { // Some intent has been matched
            System.out.println("Break 3.5.1");

            List<GoogleCloudDialogflowV2Context> outputContexts = intent.getOutputContexts(); // Output contexts per defecte
            System.out.println("Break 3.5.2");

            if(intent.getIsFallback()) {
                System.out.println("Break 3.5.1.1");

                //List<GoogleCloudDialogflowV2Context> currentContexts = queryResult.getOutputContexts();
                //response.setOutputContexts(currentContexts);
                response.setFulfillmentText("Sorry, i did not understand");
            }
            else if(outputContexts != null && outputContexts.isEmpty()) { // A query intent with no in/out contexts
                System.out.println("Break 3.5.1.2");
                response.setFulfillmentText(textResponse);
            }
            else {
                System.out.println("Break 3.5.1.3");

                String name = intent.getDisplayName();
                System.out.println("Break 3.5.1.4");

                //Intent myIntent = intentDAO.findById(name);

                //String textResponse = myIntent.getResponse();
                response.setFulfillmentText(textResponse);
                System.out.println("Break 3.5.1.5");

                //String outputContext = myIntent.getOutcontext();
                //List<String> outputContextsList = parseOutputContext(outputContext);
                //List<GoogleCloudDialogflowV2Context> currentOutputContext = queryResult.getOutputContexts();
                //List<GoogleCloudDialogflowV2Context> outputContexts= createOutputContexts(outputContextsList, currentOutputContext, session);
                response.setOutputContexts(outputContexts);
                System.out.println("Break 3.5.1.6");

            }
        }
        else {
            System.out.println("Break 3.6");

            // Should never enter here, because the fallback intent should be triggered
            response.setFulfillmentText("Something went wrong (no intent has been matched, not even the fallback intent");

        }
        System.out.println("Break 3.7");

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
