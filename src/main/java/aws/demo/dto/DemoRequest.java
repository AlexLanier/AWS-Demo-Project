package aws.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for demo requests with validation
 */
public class DemoRequest {

    @NotNull(message = "Source cannot be null")
    @NotBlank(message = "Source cannot be blank")
    @Size(max = 100, message = "Source must not exceed 100 characters")
    private String source;

    @NotNull(message = "Value cannot be null")
    private Integer value;

    @Size(max = 500, message = "Message must not exceed 500 characters")
    private String message;

    // Constructors
    public DemoRequest() {}

    public DemoRequest(String source, Integer value, String message) {
        this.source = source;
        this.value = value;
        this.message = message;
    }

    // Getters and setters
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DemoRequest{" +
                "source='" + source + '\'' +
                ", value=" + value +
                ", message='" + message + '\'' +
                '}';
    }
}
