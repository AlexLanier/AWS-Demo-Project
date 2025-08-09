// Directory: src/main/java/aws/demo/lambda/LambdaInvoker.java
package aws.demo.lambda;

import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.nio.charset.StandardCharsets;

public class LambdaInvoker {
    public static void invoke(String functionName, String payload) {
        LambdaClient lambda = LambdaClient.create();

        InvokeRequest request = InvokeRequest.builder()
                .functionName(functionName)
                .payload(StandardCharsets.UTF_8.encode(payload))
                .build();

        InvokeResponse response = lambda.invoke(request);
        System.out.println("Lambda response: " + response.statusCode());
    }
}