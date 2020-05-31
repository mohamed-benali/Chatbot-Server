package com.example.sardapp.api.controller;

import com.example.sardapp.api.dto.getUserDTO;
import com.example.sardapp.api.service.AssistentService;
import com.example.sardapp.api.service.UserService;
import com.example.sardapp.entities.Acte;
import com.example.sardapp.entities.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "User")
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private AssistentService assistentService;

    /*  Method: GET
        Obtain some data from database
    */
    /*  Get all users*/
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all users", notes = "Get all users with their information")
    public ResponseEntity findAll()
    {
        List<User> users = userService.findAll();
        if(users == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<getUserDTO>>(listUsers(users), HttpStatus.OK);
    }

    /* Get one user specified by an email*/
    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get one user", notes = "Get all user information by its email")
    public ResponseEntity getUser(@PathVariable String email)
    {
        User user = userService.findByEmail(email);

        if(user == null)
        {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new getUserDTO(user), HttpStatus.OK);
    }

    /* Get users by some filters*/
    @GetMapping(value = "/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users by filters", notes = "Get each user with parameter events checked")
    public ResponseEntity getUsersByFilters(@RequestParam(required = false) List<String> habilitats, @RequestParam(required = false) List<String> events,
                            @RequestParam(required = false) Integer edatMax, @RequestParam(required = false) Integer edatMin,
                            @RequestParam(required = false) String comarca, @RequestParam(required = false) Boolean transport)
    {
        if (!checkEventNames(events))
            return new ResponseEntity("Some event name is wrong. It should be 'aplecs' or 'ballades' or 'concerts' or 'concursos' or 'cursets' or 'altres'.", HttpStatus.BAD_REQUEST);

        if (!checkPreferencesNames(habilitats))
            return new ResponseEntity("Some 'habilitat' name is wrong. It should be 'comptar' or 'competidor' or 'coblaCompeticio'", HttpStatus.BAD_REQUEST);

        if ((edatMin != null && edatMax != null) && (edatMin < 1 || edatMax > 120 || edatMax < edatMin))
            return new ResponseEntity("Wrong range, check 'edatMin' and 'edatMax' values", HttpStatus.BAD_REQUEST);

        if (edatMin == null) edatMin = 1;
        if (edatMax == null) edatMax = 1000;

        List<User> users = userService.findByFilters(events, habilitats, edatMin, edatMax, comarca, transport);
        if(users == null)
        {
            return new ResponseEntity("No users with this filters", HttpStatus.OK);
        }
        return new ResponseEntity<List<getUserDTO>>(listUsers(users), HttpStatus.OK);
    }

    /*  Get all user acts*/
    @GetMapping(value = "/{email}/acts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all user acts", notes = "Get all user acts")
    public ResponseEntity getUserActs(@PathVariable String email)
    {
        List<Acte> actes = assistentService.getUserActs(email);
        if(actes == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(actes, HttpStatus.OK);
    }

    /*  Get past user acts*/
    @GetMapping(value = "/{email}/acts/past", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get past user acts", notes = "Get past user acts")
    public ResponseEntity getPastUserActs(@PathVariable String email)
    {
        List<Acte> actes = assistentService.getPastUserActs(email);
        if(actes == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(actes, HttpStatus.OK);
    }

    /*  Get next user acts*/
    @GetMapping(value = "/{email}/acts/next", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get next user acts", notes = "Get next user acts")
    public ResponseEntity getNextUserActs(@PathVariable String email)
    {
        List<Acte> actes = assistentService.getNextUserActs(email);
        if(actes == null)
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Acte>>(actes, HttpStatus.OK);
    }

    /*  Method: POST
        Create new entry into database
    */
    /*  Create new user*/
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new user", notes = "Create new user providing its information")
    public ResponseEntity addUser(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        User userFound = userService.findByEmail(user.getEmail());

        if(userFound != null)
        {
            return new ResponseEntity("User already exists with this email", HttpStatus.CONFLICT);
        }
        userService.save(user);
        return new ResponseEntity(new getUserDTO(user), HttpStatus.CREATED);
    }

    /* Log in user specified by its email and password*/
    @PostMapping(value = "/{email}/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Login user", notes = "Login user by its email and password")
    public ResponseEntity loginUser(@PathVariable String email, @RequestBody String password) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        User userDB = userService.findByEmail(email);

        if (userDB != null)
        {
            if (userService.login(email, password))
            {
                return new ResponseEntity("User logged successfully", HttpStatus.OK);
            }
            else return new ResponseEntity("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity("User not found",HttpStatus.NOT_FOUND);
    }


    /*  Method: PUT
        Modify an entry from database
    */
    /*  Modify a user's information */
    @PutMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modify user's information", notes = "Modify any field of a user by its email")
    public ResponseEntity editUser(@PathVariable String email, @RequestBody User user) throws IOException
    {
        User oldUser = userService.findByEmail(email);
        if(oldUser == null)
        {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
        if(user.getEmail() != null)
        {
            return new ResponseEntity("Cannot change user's email", HttpStatus.BAD_REQUEST);
        }
        User userUpdated = userService.editUser(email, user);
        return new ResponseEntity(new getUserDTO(userUpdated), HttpStatus.CREATED);
    }

    /*  Modify a user's profile image specified by an email */
    @PutMapping(value = "/{email}/updateProfileImage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modify user's profile image", notes = "Modify user's profile image by its email")
    public ResponseEntity updateProfileImage(@PathVariable String email, @RequestBody byte[] image) throws IOException
    {
        User user = userService.findByEmail(email);
        if(user == null)
        {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
        userService.addProfileImage(email, image);
        return new ResponseEntity(new getUserDTO(user), HttpStatus.CREATED);
    }

    /*  Modify a user's password specified by an email */
    @PutMapping(value = "/{email}/updatePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modify user's password", notes = "Modify user's password by its email")
    public ResponseEntity updatePassword(@PathVariable String email, @RequestParam String newPassword, @RequestParam String oldPassword) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        User user = userService.findByEmail(email);
        if(user == null)
        {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
        if(!userService.login(email, oldPassword))
        {
            return new ResponseEntity("Wrong old password", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(newPassword);
        userService.save(user);
        return new ResponseEntity("Password updated", HttpStatus.OK);
    }


    /*  Method: DELETE
        Delete an entry from database
    */
    /*  Delete an specific user by its email */
    @DeleteMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a user", notes = "Delete a user by its email")
    public ResponseEntity deleteUser(@PathVariable String email)
    {
        User user = userService.findByEmail(email);
        if (user == null)
        {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
        userService.deleteByEmail(email);
        return new ResponseEntity("User deleted successfully", HttpStatus.NO_CONTENT);
    }





    /*  ADDITIONAL FUNCTIONS
        Additional functions for checking values
    */
    public static boolean checkEventNames(List<String> events)
    {
        Boolean nameOK = false;
        if (events == null) return true;
        for (String event: events)
        {
            if (event.equals("aplecs") || event.equals("ballades") || event.equals("concerts") ||
                    event.equals("concursos") || event.equals("cursets") || event.equals("altres"))
                nameOK = true;
            else
            {
                nameOK = false;
                break;
            }
        }
        return nameOK;
    }

    public static boolean checkPreferencesNames(List<String> preferences)
    {
        Boolean nameOK = false;
        System.out.println("CHECK");
        if (preferences == null) return true;
        for (String preference: preferences)
        {
            if (preference.equals("competidor") || preference.equals("coblaCompeticio")
                    || preference.equals("comptar"))
                nameOK = true;
            else
            {
                nameOK = false;
                break;
            }
        }
        return nameOK;
    }

    public static List<getUserDTO> listUsers(List<User> users)
    {
        List<getUserDTO> usersDTO = new ArrayList<>();

        for (User u : users)
        {
            usersDTO.add(new getUserDTO(u));
        }

        return usersDTO;
    }

}
