package smartshare.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartshare.authenticationservice.constant.ResultStatus;
import smartshare.authenticationservice.model.User;
import smartshare.authenticationservice.service.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/",produces = "application/json")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    Map<String,String> resultOfOperation  = new HashMap();


    @GetMapping(value="**")
    public ResponseEntity<Map<String, String>> testMethod(RequestEntity request){
        System.out.println("inside");
        System.out.println(request);
        Map<String,String> sample  = new HashMap();
        sample.put("data","signIn");
        return new ResponseEntity<>( sample, HttpStatus.OK);
    }
    @PostMapping(value = "/signUp")
    public  ResponseEntity<Map<String, String>> registerUserToApplication(@RequestBody User userInfo){
        System.out.println("inside registerUserToTheApplication");
        System.out.println(userInfo);
        User savedUser = authenticationService.registerUserToApplication(userInfo);
        if(null != savedUser){
            resultOfOperation.put("userName", savedUser.getUserName());
            resultOfOperation.put("message", ResultStatus.Success.name());
            return new ResponseEntity<>( resultOfOperation, HttpStatus.OK);
        }
        return new ResponseEntity<>( resultOfOperation, HttpStatus.BAD_REQUEST);
    }

}
