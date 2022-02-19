package moshe.shim.jera.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ErrorDetailsDTO {
    @Builder.Default
    private final Date timeStamp = new Date();
    private final int status;
    private final String message;
    private final Object details;
    private final String path;
}
