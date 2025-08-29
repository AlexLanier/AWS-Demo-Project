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
            
            // Test S3 functionality (commented out to avoid actual uploads)
            System.out.println("\n=== Testing S3 ===");
            S3Uploader.uploadFile("demo-bucket-alex-2025", "java-demo/test.txt", "/Users/alex/test.txt");
            // Test Lambda functionality (commented out to avoid actual invocations)
            System.out.println("\n=== Testing Lambda ===");
            LambdaInvoker.invoke("demo-python-lambda", "{\"hello\":\"from-java\"}");
            
            // Test SQS functionality (commented out to avoid actual messages)
            System.out.println("\n=== Testing SQS ===");
            SQSPublisher.sendMessage(
                "https://sqs.us-east-1.amazonaws.com/050284121366/testQueueStandard",
                "{\"event\":\"demo\",\"value\":123}"
            );
           
            // Test Step Functions functionality (commented out to avoid actual executions)
            System.out.println("\n=== Testing Step Functions ===");
            System.out.println("Step function runner ready (commented out to avoid actual executions)");
            // StepFunctionRunner.startExecution("arn:aws:states:region:account:stateMachine:name", "{\"test\": \"data\"}");
            
        } catch (Exception e) {
            System.err.println("Error during AWS operations: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nDemo completed successfully!");
    }
}