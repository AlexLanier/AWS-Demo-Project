// Directory: src/main/java/aws/demo/step/StepFunctionRunner.java
package aws.demo.step;

import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.StartExecutionRequest;

public class StepFunctionRunner {
    public static void startExecution(String stateMachineArn, String inputJson) {
        try (SfnClient sfn = SfnClient.create()) {
            StartExecutionRequest request = StartExecutionRequest.builder()
                    .stateMachineArn(stateMachineArn)
                    .input(inputJson)
                    .build();

            sfn.startExecution(request);
            System.out.println("Step Function execution started.");
        } catch (Exception e) {
            System.err.println("Error starting Step Function execution: " + e.getMessage());
            throw e;
        }
    }
}