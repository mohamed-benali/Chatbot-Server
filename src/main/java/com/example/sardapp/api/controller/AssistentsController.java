package com.example.sardapp.api.controller;

import com.example.sardapp.api.service.ActeService;
import com.example.sardapp.api.service.AssistentService;
import com.example.sardapp.api.service.UserService;
import com.example.sardapp.entities.Acte;
import com.example.sardapp.entities.Assistent;
import com.example.sardapp.entities.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Assistent")
@RequestMapping("/api/actes")
public class AssistentsController
{
    @Autowired
    private AssistentService assistentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActeService acteService;

    /*  Method: GET
        Obtain some data from database
    */
    /*  Get all assistants */
    @GetMapping(value = "/{id}/assistants", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all assistants", notes = "Get all assistants from an act")
    public ResponseEntity getAssitants(@PathVariable Integer id)
    {
        List<User> assistants = assistentService.getAssistants(id);
        if (assistants == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(assistants, HttpStatus.OK);
    }

    /*  Check assistance*/
    @GetMapping(value = "{id}/assistants/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Check assistance", notes = "Check if user is an assistant of an act")
    public ResponseEntity checkAssistance(@PathVariable Integer id, @PathVariable String email)
    {;
        Assistent assistent = assistentService.findById(id, email);

        if(assistent != null) return new ResponseEntity(true, HttpStatus.FOUND);
        return new ResponseEntity(false, HttpStatus.NOT_FOUND);
    }

    /*  Method: POST
       Create new entry into database
   */
    /*  Create new assistant*/
    @PostMapping(value = "{id}/assistants", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add assistant", notes = "Add assistant to an act")
    public ResponseEntity addUser(@PathVariable Integer id, @RequestParam String email)
    {
        User user = userService.findByEmail(email);
        Acte acte = acteService.findById(id);
        Assistent assistent = assistentService.findById(id, email);

        if(acte == null) return new ResponseEntity("Act '" + id + "' not found", HttpStatus.NOT_FOUND);
        if(user == null) return new ResponseEntity("User '" + email + "' not found", HttpStatus.NOT_FOUND);
        if(assistent != null) return new ResponseEntity("This user is already an assitant to this act", HttpStatus.CONFLICT);

        Boolean commited = assistentService.newAssistant(id, email);
        return new ResponseEntity(commited, HttpStatus.CREATED);
    }


    /*  Method: DELETE
        Delete an entry from database
    */
    /*  Delete an asssitant from and act */
    @DeleteMapping(value = "/{id}/assistants/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete an assistant", notes = "Delete an asssitant from and act")
    public ResponseEntity deleteUser(@PathVariable Integer id, @PathVariable String email)
    {
        User user = userService.findByEmail(email);
        Acte acte = acteService.findById(id);
        Assistent assistent = assistentService.findById(id, email);

        if(acte == null) return new ResponseEntity("Act '" + id + "' not found", HttpStatus.NOT_FOUND);
        if(user == null) return new ResponseEntity("User '" + email + "' not found", HttpStatus.NOT_FOUND);
        if(assistent == null) return new ResponseEntity("This user is not an assitant from this act", HttpStatus.NOT_FOUND);

        Boolean commited = assistentService.deleteAssistant(id, email);
        return new ResponseEntity(commited, HttpStatus.NO_CONTENT);
    }
}
