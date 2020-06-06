package com.example.sardapp.api.service;

import com.example.sardapp.DialogFlow.IntentManagment;
import com.example.sardapp.api.dao.IntentDAO;
import com.example.sardapp.entities.Intent;
import com.google.api.services.dialogflow.v2.model.*;
import com.google.cloud.dialogflow.v2.Context;
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
    public void processMessage(GoogleCloudDialogflowV2WebhookRequest request, GoogleCloudDialogflowV2WebhookResponse response) throws Exception {
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

            System.out.println("Break 3.5.2");

            String intentDisplayNamed = intent.getDisplayName();
            String intentName = intent.getName();

            IntentManagment intentManagment = new IntentManagment();
            com.google.cloud.dialogflow.v2.Intent completeIntent = intentManagment.getIntent(intentName);
            List<Context> outputContextsCompleteIntent = completeIntent.getOutputContextsList(); // Output contexts per defecte

            List<GoogleCloudDialogflowV2Context> intentOutputContextsList = this.parseOutputContextsToCloudDialogflow(outputContextsCompleteIntent, session);
            List<GoogleCloudDialogflowV2Context> currentOutputContext = queryResult.getOutputContexts();
            List<GoogleCloudDialogflowV2Context> outputContexts = createOutputContexts(intentOutputContextsList, currentOutputContext, session);


            boolean isFallback = intentDisplayNamed.equals("DEFAULT_FALLBACK_INTENT"); // TODO: Improve this
            System.out.println("Break 3.5.3");

            if(isFallback) {
                System.out.println("Break 3.5.3.1");
                response.setFulfillmentText("Sorry, i did not understand");
            }
            else if(outputContextsCompleteIntent != null && outputContextsCompleteIntent.isEmpty()) { // A query intent with no in/out contexts
                System.out.println("Break 3.5.3.2");
                response.setFulfillmentText(textResponse);
            }
            else {
                System.out.println("Break 3.5.3.3");
                System.out.println("Break 3.5.3.4");

                //Intent myIntent = intentDAO.findById(intentDisplayNamed);

                //String textResponse = myIntent.getResponse();
                response.setFulfillmentText(textResponse);
                System.out.println("Break 3.5.3.5");

                response.setOutputContexts(outputContexts);
                System.out.println("Break 3.5.3.6");

            }
        }
        else {
            System.out.println("Break 3.6");

            // Should never enter here, because the fallback intent should be triggered
            response.setFulfillmentText("Something went wrong (no intent has been matched, not even the fallback intent");

        }
        System.out.println("Break 3.7");
    }

    @Override
    public List<GoogleCloudDialogflowV2Context> parseOutputContextsToCloudDialogflow(List<Context> outputContextsCompleteIntent,
                                                                                    String session) {
        List<GoogleCloudDialogflowV2Context> outputContexts = new ArrayList<>();
        for(Context context : outputContextsCompleteIntent) {
            String name = context.getName();
            String[] nameArray = name.split("/");
            String displayName = nameArray[nameArray.length -1];

            int lifespan = context.getLifespanCount();

            GoogleCloudDialogflowV2Context googleCloudDialogflowV2Context = new GoogleCloudDialogflowV2Context();
            googleCloudDialogflowV2Context.setName(session + "/contexts/" + displayName);
            googleCloudDialogflowV2Context.setLifespanCount(lifespan);

            outputContexts.add(googleCloudDialogflowV2Context);
        }
        return outputContexts;
    }

    private List<GoogleCloudDialogflowV2Context> createOutputContexts(List<GoogleCloudDialogflowV2Context> outputContextsList,
                                                                      List<GoogleCloudDialogflowV2Context> currentOutputContextList,
                                                                      String session)
    {
        for (GoogleCloudDialogflowV2Context currentOutputContext : currentOutputContextList) {
            if (!outputContextsList.contains(currentOutputContext)) {
                currentOutputContext.setLifespanCount(0);
                outputContextsList.add(currentOutputContext);
            }
        }
        return outputContextsList;
    }

    private List<String> parseOutputContext(String outputContext) {
        String separator = ";";
        String[] array = outputContext.split(separator);
        return new ArrayList<>(Arrays.asList(array));
    }


}
