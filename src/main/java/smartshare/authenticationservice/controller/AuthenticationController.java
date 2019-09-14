package smartshare.authenticationservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartshare.authenticationservice.model.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/",produces = "application/json")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @GetMapping(value="**")
    public ResponseEntity<Map<String, String>> testMethod(RequestEntity request){
        System.out.println("inside");
        System.out.println(request);
        Map<String,String> sample  = new HashMap();;
        sample.put("data","signIn");
        return new ResponseEntity<>( sample, HttpStatus.OK);
    }
    @PostMapping(value = "/signUp")
    public  ResponseEntity<Map<String, String>> registerUserToTheApplication(@RequestBody User userInfo){
        System.out.println("inside registerUserToTheApplication");
        System.out.println(userInfo);
        Map<String,String> sample  = new HashMap();;
        sample.put("data","signIn");
        return new ResponseEntity<>( sample, HttpStatus.OK);
    }

}
