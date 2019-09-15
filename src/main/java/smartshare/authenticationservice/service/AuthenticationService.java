package smartshare.authenticationservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartshare.authenticationservice.model.User;

@Service
public class AuthenticationService {

    @Autowired
    private SignUpOperations signUpOperations;

    private static Object userFunction(OperationsOnUser operationsOnUser, User user) {
        return operationsOnUser.operationToBePerformed(user);
    }

    private User addUserInformationInDatabaseAndMessagingQueue(User user) {

        User registeredUser = (User) userFunction(signUpOperations::addUserInformationToDatabase, user);
        if (registeredUser.getUserOperationsResultStatus().equals("")) {
            Boolean messagePublishedToKafkaStatus = (Boolean) userFunction(signUpOperations::addUserInformationToMessagingService, user);
            if (!messagePublishedToKafkaStatus) {
                System.out.println("Error in publishing " + user + "to kafka...");
                // can implement user record recovery either from authentication side or authorization side
            }
        }
        return registeredUser;
    }

    public User registerUserToApplication(User user) {
        return addUserInformationInDatabaseAndMessagingQueue((User) userFunction(signUpOperations::prepareReceivedUserInfoForPersisting, user));
    }

}
