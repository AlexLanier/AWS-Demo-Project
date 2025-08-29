package aws.demo.lambda;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

public class LambdaInvoker {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static JsonNode invoke(String functionName, String payload) {
        try (LambdaClient lambda = LambdaClient.create()) {
            InvokeRequest request = InvokeRequest.builder()
                    .functionName(functionName)
                    .payload(SdkBytes.fromUtf8String(payload))
                    .build();

            InvokeResponse response = lambda.invoke(request);
            String body = response.payload().asUtf8String();

            System.out.println("Lambda response Status: " + response.statusCode());
            JsonNode json = MAPPER.readTree(body);
            System.out.println("Lambda JSON: " + json.toPrettyString());
            return json;
        } catch (Exception e) {
            System.err.println("Error invoking Lambda function: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
