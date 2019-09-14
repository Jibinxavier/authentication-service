package smartshare.authenticationservice.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackages = "smartshare.authenticationservice.Repository" )
@Configuration
public class MongoConfiguration {

}
