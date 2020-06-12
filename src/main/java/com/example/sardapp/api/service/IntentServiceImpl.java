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
    public Intent findById(int numero) {
        return intentDAO.findById(numero);
    }

    @Override
    public String setUpEmptyDB() {
        return intentDAO.setUpEmptyDB();
    }

    @Override
    public List<Intent> findAll() {
        return intentDAO.findAll();
    }



    public void print(List<GoogleCloudDialogflowV2Context> contexts, String s) {
        System.out.println();
        System.out.println(s);
        for(GoogleCloudDialogflowV2Context googleCloudDialogflowV2Context : contexts) {
            System.out.print(googleCloudDialogflowV2Context.getName() + " ");
            System.out.println();
        }
    }

    @Override
    public void processMessage(GoogleCloudDialogflowV2WebhookRequest request, GoogleCloudDialogflowV2WebhookResponse response) throws Exception {
        System.out.println("Break 3.1");

        GoogleCloudDialogflowV2QueryResult queryResult = request.getQueryResult();
        System.out.println("Break 3.2");

        GoogleCloudDialogflowV2Intent intent = queryResult.getIntent();
        System.out.println("Break 3.3");

        String session = request.getSession();
        System.out.println("session = " + session);

        System.out.println("Break 3.4");

        String textResponse = queryResult.getFulfillmentText();
        System.out.println("Break 3.5");

        if(intent != null) { // Some intent has been matched
            System.out.println("Break 3.5.1");
            String intentDisplayNamed = intent.getDisplayName();
            String intentName = intent.getName();

            IntentManagment intentManagment = new IntentManagment();
            com.google.cloud.dialogflow.v2.Intent completeIntent = intentManagment.getIntent(intentName);
            List<Context> outputContextsCompleteIntent = completeIntent.getOutputContextsList(); // Output contexts per defecte

            System.out.println("Break 3.5.2");


            List<GoogleCloudDialogflowV2Context> intentOutputContextsList = this.parseOutputContextsToCloudDialogflow(outputContextsCompleteIntent, session);
            List<GoogleCloudDialogflowV2Context> currentOutputContext = queryResult.getOutputContexts();

            print(intentOutputContextsList, "intentOutputContextsList");
            print(currentOutputContext, "currentOutputContext");

            List<GoogleCloudDialogflowV2Context> outputContexts = createOutputContexts(intentOutputContextsList, currentOutputContext);
            print(outputContexts, "outputContexts");


            boolean isFallback = intentDisplayNamed.equals("DEFAULT_FALLBACK_INTENT"); // TODO: Improve this
            System.out.println("Break 3.5.3");

            boolean isResetIntent = intentDisplayNamed.equals("begin_context"); // TODO: Improve this

            if(isFallback) {
                System.out.println("Break 3.5.3.1");
                response.setFulfillmentText("Sorry, i did not understand");
            }
            else if(outputContextsCompleteIntent != null &&
                    outputContextsCompleteIntent.isEmpty() &&
                    !intentDisplayNamed.equals("BACK_INTENT")) { // A query intent with no in/out contexts
                System.out.println("Break 3.5.3.2");
                response.setFulfillmentText(textResponse);
            }
            else if(isResetIntent) {
                this.intentDAO.setUpDB(intentName, textResponse);
                response.setFulfillmentText(textResponse);
                response.setOutputContexts(outputContexts);
            }
            else {
                System.out.println("Break 3.5.3.3");
                boolean isBack = intentDisplayNamed.equals("BACK_INTENT"); // TODO: Improve this
                System.out.println("Break 3.5.3.4");

                if(isBack) {
                    // TODO: Change Intent class: Add number(id) that is the position.
                    Intent stackElement = intentDAO.topStack();
                    System.out.println("Break 3.6.1");
                    if (stackElement != null) {
                        intentDAO.popStackContext(stackElement);
                        stackElement = intentDAO.topStack();
                        if(stackElement != null) {
                            com.google.cloud.dialogflow.v2.Intent completeBackIntent = intentManagment.getIntent(stackElement.getIntent());
                            List<Context> outputContextsCompleteBackIntent = completeBackIntent.getOutputContextsList(); // Output contexts per defecte
                            List<GoogleCloudDialogflowV2Context> backIntentOutputContextsList = this.parseOutputContextsToCloudDialogflow(outputContextsCompleteBackIntent, session);

                            print(backIntentOutputContextsList, "backIntentOutputContextsList");
                            print(currentOutputContext, "currentOutputContext");

                            List<GoogleCloudDialogflowV2Context> outputBackContexts = createOutputContexts(backIntentOutputContextsList, currentOutputContext);
                            print(outputBackContexts, "outputBackContexts");

                            // TODO: BETTER PERFORMANCE:
                            //  Push, on the stack, the output contexts of each intent. Then, its not needed to
                            //  use the API to call the intent

                            textResponse = stackElement.getResponse();
                            response.setFulfillmentText(textResponse);
                            response.setOutputContexts(outputBackContexts);
                            System.out.println("Break 3.6.1.2");
                        }
                        else {
                            response.setFulfillmentText("You cannot go back.");
                            System.out.println("Break 3.6.1.3");
                        }
                    }
                    else {
                        response.setFulfillmentText("You cannot go back.");
                        System.out.println("Break 3.6.1.4");
                    }
                } else {
                    intentDAO.pushStackContext(textResponse, intentName);

                    response.setFulfillmentText(textResponse);
                    response.setOutputContexts(outputContexts);
                    System.out.println("Break 3.6.2");
                }
            }
        }
        else {
            System.out.println("Break 3.7");

            // Should never enter here, because the fallback intent should be triggered
            response.setFulfillmentText("Something went wrong (no intent has been matched, not even the fallback intent");

        }
        System.out.println("Break 3.8");
    }

    @Override
    public List<GoogleCloudDialogflowV2Context> parseOutputContextsToCloudDialogflow(List<Context> outputContextsCompleteIntent,
                                                                                    String session) {
        List<GoogleCloudDialogflowV2Context> outputContexts = new ArrayList<>();
        for(Context context : outputContextsCompleteIntent) {
            String name = context.getName();
            String[] nameArray = name.split("/");
            String displayName = nameArray[nameArray.length -1];
            displayName = displayName.toLowerCase();

            int lifespan = context.getLifespanCount();

            GoogleCloudDialogflowV2Context googleCloudDialogflowV2Context = new GoogleCloudDialogflowV2Context();
            googleCloudDialogflowV2Context.setName(session + "/contexts/" + displayName);
            googleCloudDialogflowV2Context.setLifespanCount(lifespan);

            outputContexts.add(googleCloudDialogflowV2Context);
        }
        return outputContexts;
    }

    @Override // TODO: Si Dialogflow fa match intent per el begin context, aquesta funcio no seria necesaria
    public String setUpDB() throws Exception {/*
    TODO: Falta acabar: Potser guarda el displayName, i construir el nom del contexte segons la sessio especifica
        Posar un if, segons si el intent.numero = 1?


        IntentManagment intentManagment = new IntentManagment();
        com.google.cloud.dialogflow.v2.Intent completeIntent = intentManagment.getIntent("begin_context");

        String textResponse = completeIntent.getMessages(0).getText().getText(0);
        List<Context> outputContextsCompleteIntent = completeIntent.getOutputContextsList(); // Output contexts per defecte

        List<GoogleCloudDialogflowV2Context> intentOutputContextsList = this.parseOutputContextsToCloudDialogflow(outputContextsCompleteIntent);
        List<GoogleCloudDialogflowV2Context> currentOutputContext = queryResult.getOutputContexts();
        List<GoogleCloudDialogflowV2Context> outputContexts = createOutputContexts(intentOutputContextsList, currentOutputContext);


        return this.intentDAO.setUpDB();*/
        return null;
    }

    /**
     * Nomes creen els contextes que haurien de estar actius, la resta es crean amb lifespan 0(per eliminarlos).
     * @param outputContextsList Contextes que haurian de estar actius
     * @param currentOutputContextList Contextes actuals
     * @return
     */
    private List<GoogleCloudDialogflowV2Context> createOutputContexts(List<GoogleCloudDialogflowV2Context> outputContextsList,
                                                                      List<GoogleCloudDialogflowV2Context> currentOutputContextList)
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
