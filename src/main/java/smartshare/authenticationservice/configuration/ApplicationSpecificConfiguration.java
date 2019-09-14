package smartshare.authenticationservice.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationSpecificConfiguration {

    @Bean
    public ObjectWriter customObjectMapperForKafka() {
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
}
