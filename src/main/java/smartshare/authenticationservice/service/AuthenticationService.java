package smartshare.authenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartshare.authenticationservice.Repository.UserRepository;
import smartshare.authenticationservice.model.User;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    private User addUserInformationToDatabase(User user){

        try{
            return userRepository.insert(user);
        }
        catch (Exception exception){
            System.out.println(" Exception while saving user to mongo "+ exception);
        }
        return null;
    }

    private User addUserInformationInDatabaseAndMessagingQueue(User user){

        User savedUserInDb = addUserInformationToDatabase(user);

        return savedUserInDb;

    }

    private User prepareReceivedUserInfoForPersisting(User user){

        System.out.println(userRepository.count());
        user.setId(userRepository.count()+1);
        System.out.println("Prepared User" + user);
        return user;
    }

    public User registerUserToApplication(User user){

        return addUserInformationInDatabaseAndMessagingQueue(prepareReceivedUserInfoForPersisting(user));

    }

}
