package com.example.sardapp.api.controller;


import com.example.sardapp.api.service.IntentService;
import com.example.sardapp.entities.Intent;
import com.example.sardapp.entities.Intents;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@Api(tags = "Intent")
@RequestMapping("/api/intents")
public class IntentController
{
    @Autowired
    private IntentService intentService;

    private static JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();





    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all intents", notes = "Get all intents with their information")
    public ResponseEntity findAll()
    {
        List<Intent> intents = intentService.findAll();
        if(intents == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Intent>>(intents, HttpStatus.OK);
    }

    /* Get one user specified by an email*/
    @GetMapping(value = "/{numero}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get one intent", notes = "Get one intent information by its id")
    public ResponseEntity getUser(@PathVariable int numero)
    {
        Intent intent = intentService.findById(numero);

        if(intent == null)
        {
            return new ResponseEntity("Intent not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(intent, HttpStatus.OK);
    }







    /*  Method: POST
        Create new entry into database
    */
    /*  Create new intent*/
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new intent", notes = "Create new intent providing its information")
    public ResponseEntity addIntent(@RequestBody Intent intent) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        Intent intentFound = intentService.findById(intent.getNumero());

        if(intentFound != null)
        {
            return new ResponseEntity("Intent already exists with this id", HttpStatus.CONFLICT);
        }

        intentService.save(intent);

        return new ResponseEntity("Intent created succesfully", HttpStatus.CREATED);
    }

    /*  Method: POST
    Create new entry into database
*/
    /*  Create new intent*/
    @PostMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create all the intent", notes = "Create all the intents")
    public ResponseEntity addIntent(@RequestBody List<Intent> intents) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        for(Intent intent : intents) {
            Intent intentFound = intentService.findById(intent.getNumero());
            if(intentFound != null)
            {
                return new ResponseEntity("Intent already exists with this id", HttpStatus.CONFLICT);
            }
            intentService.save(intent);
        }

        return new ResponseEntity("Intent created succesfully", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/SetUpEmptyDB", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Set Up Empty DB", notes = "Sets Up a DB with the apropiate empty tables")
    public ResponseEntity setUpEmptyDB()
    {
        String responseBody = intentService.setUpEmptyDB();

        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/SetUpDB", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Set Up DB", notes = "Sets Up a DB with the appropriate tables and the stack with the begin intent")
    public ResponseEntity setUpDB() throws Exception {
        String responseBody = intentService.setUpDB();

        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }

    /*  Method: POST
        DialogFLow endpoint
    */
    @PostMapping(value = "/Dialogflow", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Processes DialogFlow request", notes = "Processes dialogflow requests and answers them")
    public ResponseEntity<?> dialogflow(@RequestBody String requestStr, HttpServletRequest servletRequest) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        try {
            System.out.println("Break 1");
            GoogleCloudDialogflowV2WebhookResponse response = new GoogleCloudDialogflowV2WebhookResponse();
            System.out.println("Break 2");

            GoogleCloudDialogflowV2WebhookRequest request = jacksonFactory.createJsonParser(requestStr).parse(GoogleCloudDialogflowV2WebhookRequest.class);
            System.out.println("Break 3");

            intentService.processMessage(request, response);
            System.out.println("Break 4");

            return new ResponseEntity<GoogleCloudDialogflowV2WebhookResponse>(response,HttpStatus.OK);
        }
        catch (Exception ex) {
            System.out.println("Break Error");

            System.out.println(ex.getMessage());
            return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



}
