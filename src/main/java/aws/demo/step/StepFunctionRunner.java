// Directory: src/main/java/aws/demo/step/StepFunctionRunner.java
package aws.demo.step;

import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.StartExecutionRequest;

public class StepFunctionRunner {
    public static void startExecution(String stateMachineArn, String inputJson) {
        SfnClient sfn = SfnClient.create();

        StartExecutionRequest request = StartExecutionRequest.builder()
                .stateMachineArn(stateMachineArn)
                .input(inputJson)
                .build();

        sfn.startExecution(request);
        System.out.println("Step Function execution started.");
    }
}