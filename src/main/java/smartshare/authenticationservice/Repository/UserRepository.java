package smartshare.authenticationservice.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import smartshare.authenticationservice.model.User;


public interface UserRepository extends MongoRepository<User,Long> {

}
