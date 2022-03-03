import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "shim.mosh")
public class AppConfiguration {
    public String jwtSecret = "K~z}7>bR4z%W6n~M$N=4g]rCF?g&&_N}M$N=4g]rCF?g&&_N";
    private long jwtExpires = 2592000000L;
}