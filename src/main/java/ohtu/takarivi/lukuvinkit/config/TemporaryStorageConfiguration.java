package ohtu.takarivi.lukuvinkit.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporaryStorageConfiguration {
    @Bean
    public Map<Long, List<Long>> selectedTipsMap() {
        // lists of reading tip IDs indexed by user ID
        return new HashMap<Long, List<Long>>();
    }
}
