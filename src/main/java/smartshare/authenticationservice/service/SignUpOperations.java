package smartshare.authenticationservice.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import smartshare.authenticationservice.Repository.UserRepository;
import smartshare.authenticationservice.constant.KafkaKeys;
import smartshare.authenticationservice.model.User;

@Component
public class SignUpOperations<T> {

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectWriter jsonConverter;

    <U> U addUserInformationToDatabase(User user) {
        try {
            return (U) userRepository.insert(user);
        } catch (Exception exception) {
            System.out.println(" Exception while saving user to mongo " + exception);
            User unqualifiedUserDuringRegistrationProcess = new User(null, null, null);
            unqualifiedUserDuringRegistrationProcess.setUserOperationsResultStatus(exception.getMessage());
            return (U) unqualifiedUserDuringRegistrationProcess;
        }
    }

    <T> T addUserInformationToMessagingService(User user) {

        try {
            ListenableFuture<SendResult<String, String>> producerResult = kafkaTemplate.send("authentication", KafkaKeys.SignUp.name(), jsonConverter.writeValueAsString(user));
            if (!producerResult.get().getRecordMetadata().toString().isEmpty()) {
                return (T) Boolean.TRUE;
            }
        } catch (Exception exception) {
            System.out.println(" Exception while publishing user to Kafka " + exception.getCause() + exception.getMessage());
        }
        return (T) Boolean.FALSE;
    }

    <U> U prepareReceivedUserInfoForPersisting(User user) {
        user.setId(userRepository.count() + 1);
        user.setUserOperationsResultStatus("");
        return (U) user;
    }

}
