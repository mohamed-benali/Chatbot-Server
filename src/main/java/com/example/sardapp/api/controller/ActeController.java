package com.example.sardapp.api.controller;


import com.example.sardapp.api.service.ActeService;
import com.example.sardapp.entities.Acte;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.util.List;

@RestController
@Api(tags = "Acte")
@RequestMapping("/api/actes")
public class ActeController {

    @Autowired
    private ActeService acteService;

    /*  Method: GET
        Obtain some data from database
    */
    /*  Get all acts */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all acts", notes = "Get all acts with their respective information")
    public ResponseEntity findAllActs()
    {
        List<Acte> acts = acteService.findAll();
        if(acts == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(acts, HttpStatus.OK);
    }

    /* Get all acts with a multifilter */
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get acts by some filters", notes = "Get all acts that fulfill the filters")
    public ResponseEntity findActsByFilters (@RequestParam(required = false) List<String> tipus, @RequestParam(required = false) Date diaMinim,
                                             @RequestParam(required = false) Date diaMaxim, @RequestParam(required = false) String hora,
                                             @RequestParam(required = false) Boolean Anul, @RequestParam(required = false) List<String> comarca,
                                             @RequestParam(required = false) List<String> territori, @RequestParam(required = false) List<String> cobla,
                                             @RequestParam(required = false) List<String> poblacioMitjana)
    {
        if (!checkTipusNames(tipus))
            return new ResponseEntity("Some type name is wrong. It should be 'Aplec' or 'Ballada' or 'Concert' or 'Concurs' or 'Curset' or 'Diversos (altres actes)'.", HttpStatus.BAD_REQUEST);

        List<Acte> acts = acteService.findByFilters(tipus, diaMinim, diaMaxim, hora, Anul, comarca, territori, cobla, poblacioMitjana);
        if(acts == null)
        {
            return new ResponseEntity("No users with this filters", HttpStatus.OK);
        }
        return new ResponseEntity<List<Acte>>(acts, HttpStatus.OK);
    }

    /* Get one act specified by an id*/
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get one act", notes = "Get all information of the act specified by the id")
    public ResponseEntity getAct(@PathVariable Integer id)
    {
        Acte act = acteService.findById(id);

        if(act == null)
        {
            return new ResponseEntity("Act not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(act, HttpStatus.OK);
    }

    /* Get all acts with a specific type */
    @GetMapping(value = "/tipus", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all acts with a specific type", notes = "Get all acts with the type specified by tipus")
    public ResponseEntity findAllActsByTipus(@RequestParam String tipus) {
        List<Acte> acts = acteService.findAllByTipus(tipus);
        if(acts == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(acts, HttpStatus.OK);
    }

    /* Get all acts with a specific date */
    @GetMapping(value = "/dia", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all acts with a specific date", notes = "Get all acts with the date specified by dia")
    public ResponseEntity findAllActsByDia(@RequestParam Date dia)
    {
        List<Acte> acts = acteService.findAllByDia(dia);
        if(acts == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(acts, HttpStatus.OK);
    }

    /* Get all acts that has been cancelled */
    @GetMapping(value = "/cancelat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all acts that have been cancelled", notes = "Get all acts that have been cancelled")
    public ResponseEntity findAllActsCancelled()
    {
        List<Acte> acts = acteService.findAllCancelled();
        if(acts == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(acts, HttpStatus.OK);
    }

    /* Get all acts with a specific region*/
    @GetMapping(value = "/comarca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all acts that have a specific region", notes = "Get all acts with the region specified by comarca")
    public ResponseEntity findAllActsByComarca(@RequestParam String comarca)
    {
        List<Acte> acts = acteService.findAllActsByComarca(comarca);
        if(acts == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(acts, HttpStatus.OK);
    }

    /* Get all acts with a specific territory */
    @GetMapping(value = "/territori", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all acts that have a specific territory", notes = "Get all acts with the territory specified by territori")
    public ResponseEntity findAllByTerritori(@RequestParam String territori)
    {
        List<Acte> acts = acteService.findAllActsByTerritori(territori);
        if(acts == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(acts, HttpStatus.OK);
    }

    /* Get all acts with a specific population */
    @GetMapping(value = "/poblacio", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all acts that have a specific population", notes = "Get all acts with the population specified by poblacioMitjana")
    public ResponseEntity findAllByPoblacioMitjana(@RequestParam String poblacioMitjana)
    {
        List<Acte> acts = acteService.findAllActsByPoblacioMitjana(poblacioMitjana);
        if(acts == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(acts, HttpStatus.OK);
    }

    /*  Method: POST
        Create new entry into database
    */
    /*  Create new act */
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new act", notes = "Create new act providing its information")
    public ResponseEntity addAct(@RequestBody Acte acte) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        Acte acteFound = acteService.findById(acte.getId());

        if(acteFound != null)
        {
            return new ResponseEntity("Act already exists with this Id", HttpStatus.CONFLICT);
        }
        //signUp(user.getEmail(), user.getPassword());
        acteService.save(acte);
        return new ResponseEntity(acte, HttpStatus.CREATED);
    }

    /*  Method: PUT
        Modify an entry from database
    */

    /*  Method: DELETE
        Delete an entry from database
    */
    /*  Delete an specific acte by its id */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete an acte", notes = "Delete an acte by its id")
    public ResponseEntity deleteActe(@PathVariable Integer id)
    {
        if (!acteService.existsById(id))
        {
            return new ResponseEntity("Acte not found", HttpStatus.NOT_FOUND);
        }
        acteService.deleteById(id);
        return new ResponseEntity("Acte deleted successfully", HttpStatus.NO_CONTENT);
    }

    /*  ADDITIONAL FUNCTIONS
        Additional functions for checking values
    */
    public static boolean checkTipusNames(List<String> tipus)
    {
        Boolean nameOK = false;
        if (tipus == null) return true;
        for (String type: tipus)
        {
            if (type.equals("Aplec") || type.equals("Ballada") || type.equals("Concert") ||
                    type.equals("Concurs") || type.equals("Curset") || type.equals("Diversos (altres actes)"))
                nameOK = true;
            else
            {
                nameOK = false;
                break;
            }
        }
        return nameOK;
    }
}
