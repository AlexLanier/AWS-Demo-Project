// Directory: src/main/java/aws/demo/lambda/LambdaInvoker.java
package aws.demo.lambda;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.nio.charset.StandardCharsets;

public class LambdaInvoker {
    public static void invoke(String functionName, String payload) {
        try (LambdaClient lambda = LambdaClient.create()) {
            InvokeRequest request = InvokeRequest.builder()
                    .functionName(functionName)
                    .payload(SdkBytes.fromUtf8String(payload))
                    .build();

            InvokeResponse response = lambda.invoke(request);
            System.out.println("Lambda response: " + response.statusCode());
        } catch (Exception e) {
            System.err.println("Error invoking Lambda function: " + e.getMessage());
            throw e;
        }
    }
}