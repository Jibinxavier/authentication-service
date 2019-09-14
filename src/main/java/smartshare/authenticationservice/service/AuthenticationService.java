package smartshare.authenticationservice.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import smartshare.authenticationservice.Repository.UserRepository;
import smartshare.authenticationservice.constant.KafkaKeys;
import smartshare.authenticationservice.model.User;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectWriter jsonConverter;

    private User addUserInformationToDatabase(User user){

        try{
            return userRepository.insert(user);
        }
        catch (Exception exception){
            System.out.println(" Exception while saving user to mongo "+ exception);
            return null;
        }
    }

    private Boolean addUserInformationToMessagingService(User user) {

        ListenableFuture<SendResult<String, String>> producerResult = null;
        try {
            producerResult = kafkaTemplate.send("authentication", KafkaKeys.SignUp.name(), jsonConverter.writeValueAsString(user));
            if (!producerResult.get().getRecordMetadata().toString().isEmpty()) {
                return true;
            }
        } catch (Exception exception) {
            System.out.println(" Exception while publishing user to Kafka " + exception.getCause() + exception.getMessage());
        }
        return false;
    }

    private User addUserInformationInDatabaseAndMessagingQueue(User user) {

        Boolean messagePublishedToKafkaStatus = addUserInformationToMessagingService(user);
        if (messagePublishedToKafkaStatus) {
            return addUserInformationToDatabase(user);
        }
        return null;
    }

    private User prepareReceivedUserInfoForPersisting(User user){
        user.setId(userRepository.count()+1);
        return user;
    }

    public User registerUserToApplication(User user){
        return addUserInformationInDatabaseAndMessagingQueue(prepareReceivedUserInfoForPersisting(user));
    }

}
