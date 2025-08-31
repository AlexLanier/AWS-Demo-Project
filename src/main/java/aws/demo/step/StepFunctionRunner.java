// Directory: src/main/java/aws/demo/step/StepFunctionRunner.java
package aws.demo.step;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.DescribeExecutionRequest;
import software.amazon.awssdk.services.sfn.model.DescribeExecutionResponse;
import software.amazon.awssdk.services.sfn.model.ExecutionStatus;
import software.amazon.awssdk.services.sfn.model.StartExecutionRequest;
import software.amazon.awssdk.services.sfn.model.StartExecutionResponse;

public class StepFunctionRunner {

    // Starts an execution and returns its ARN
    public static String startExecution(String stateMachineArn, String inputJson) {
        try (SfnClient sfn = SfnClient.builder()
                .region(Region.US_EAST_1) // ensure this matches your SM region
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {

            StartExecutionRequest request = StartExecutionRequest.builder()
                    .stateMachineArn(stateMachineArn)
                    .name("java-demo-" + System.currentTimeMillis()) // unique name avoids collisions
                    .input(inputJson)
                    .build();

            StartExecutionResponse resp = sfn.startExecution(request);
            System.out.println("Step Function execution started.");
            return resp.executionArn();
        } catch (Exception e) {
            System.err.println("Error starting Step Function execution: " + e.getMessage());
            throw e;
        }
    }

    // Polls the execution until it finishes and prints final status/output
    public static void waitForCompletion(String executionArn) throws InterruptedException {
        try (SfnClient sfn = SfnClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {

            while (true) {
                DescribeExecutionResponse d = sfn.describeExecution(
                        DescribeExecutionRequest.builder().executionArn(executionArn).build());

                ExecutionStatus status = d.status();
                if (status == ExecutionStatus.RUNNING) {
                    Thread.sleep(1000);
                    continue;
                }

                System.out.println("Execution finished with status: " + status.toString());
                if (d.output() != null) {
                    System.out.println("Output: " + d.output());
                }
                if (d.error() != null && !d.error().isEmpty()) {
                    System.out.println("Error: " + d.error() + " - " + d.cause());
                }
                break;
            }
        }
    }
}
