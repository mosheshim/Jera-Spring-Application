package moshe.shim.jera.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIErrorMessageDTO {
    private long timeStamp;
    private String message;
    private int status;
    private String failedURL;
    private Map<String, String> validationErrors;

    public APIErrorMessageDTO(String message, int status, String failedURL, Map<String, String> validationErrors) {
        this.timeStamp = new Date().getTime();
        this.message = message;
        this.status = status;
        this.failedURL = failedURL;
        this.validationErrors = validationErrors;
    }

}
