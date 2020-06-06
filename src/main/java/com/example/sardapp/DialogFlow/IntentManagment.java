package com.example.sardapp.DialogFlow;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * DialogFlow API Intent sample.
 */
public class IntentManagment {
    private IntentsClient intentsClient;


    public IntentManagment() throws IOException {
        String projectId = "greetingsbot-qtakwv";

        String jsonPath = "./greetingsbot-qtakwv-c046349de531.json";
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
        //Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        IntentsSettings intentsSettings =
                IntentsSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();
        this.intentsClient = IntentsClient.create(intentsSettings);
    }

    /**
     * Get  intent with the given id
     *
     * @param intentId  The id of the intent.
     * @return Returns the intent
     */
    public Intent getIntent(String intentId) throws Exception {
        return intentsClient.getIntent(intentId);
    }


}
