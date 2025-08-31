package aws.demo;

import aws.demo.ec2.EC2Checker;
import aws.demo.lambda.LambdaInvoker;
import aws.demo.s3.S3Uploader;
import aws.demo.sqs.SQSPublisher;
import aws.demo.step.StepFunctionRunner;

public class AwsDemoApp {
    public static void main(String[] args) {
        System.out.println("AWS Java Demo Project Initialized.");
        
        try {
            // Test EC2 functionality
            System.out.println("\n=== Testing EC2 ===");
            EC2Checker.listInstances();
            
            // Test S3 functionality
            System.out.println("\n=== Testing S3 ===");
            S3Uploader.uploadFile("demo-bucket-alex-2025", "java-demo/test.txt", "/Users/alex/test.txt");

            // Test Lambda functionality
            System.out.println("\n=== Testing Lambda ===");
            LambdaInvoker.invoke("demo-python-lambda", "{\"hello\":\"from-java\"}");
            
            // Test SQS functionality
            System.out.println("\n=== Testing SQS ===");
            SQSPublisher.sendMessage(
                "https://sqs.us-east-1.amazonaws.com/050284121366/testQueueStandard",
                "{\"event\":\"demo\",\"value\":123}"
            );
           
            // Test Step Functions functionality
            System.out.println("\n=== Testing Step Functions ===");
            // Your state machine derived from the execution you shared:
            // Execution ARN: arn:aws:states:us-east-1:050284121366:execution:DemoEchoStateMachine:...
            // => State Machine ARN:
            String stateMachineArn = "arn:aws:states:us-east-1:050284121366:stateMachine:DemoEchoStateMachine";
            String input = "{\"source\":\"java-demo\",\"value\":123}";

            String execArn = StepFunctionRunner.startExecution(stateMachineArn, input);
            System.out.println("Started Step Functions execution: " + execArn);

            // Block until the execution finishes and print the result
            StepFunctionRunner.waitForCompletion(execArn);

        } catch (Exception e) {
            System.err.println("Error during AWS operations: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nDemo completed successfully!");
    }
}
