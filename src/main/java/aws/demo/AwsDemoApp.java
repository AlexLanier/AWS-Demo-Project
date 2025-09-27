// package aws.demo;

// import aws.demo.ec2.EC2Checker;
// import aws.demo.lambda.LambdaInvoker;
// import aws.demo.s3.S3Uploader;
// import aws.demo.sqs.SQSPublisher;
// import aws.demo.step.StepFunctionRunner;

// public class AwsDemoApp {
//     public static void main(String[] args) {
//         System.out.println("AWS Java Demo Project Initialized.");
        
//         try {
//             // Test EC2 functionality
//             System.out.println("\n=== Testing EC2 ===");
//             EC2Checker.listInstances();
            
//             // Test S3 functionality
//             System.out.println("\n=== Testing S3 ===");
//             S3Uploader.uploadFile("demo-bucket-alex-2025", "java-demo/test.txt", "/Users/alex/test.txt");

//             // Test Lambda functionality
//             System.out.println("\n=== Testing Lambda ===");
//             LambdaInvoker.invoke("demo-python-lambda", "{\"hello\":\"from-java\"}");
            
//             // Test SQS functionality
//             System.out.println("\n=== Testing SQS ===");
//             SQSPublisher.sendMessage(
//                 "https://sqs.us-east-1.amazonaws.com/050284121366/testQueueStandard",
//                 "{\"event\":\"demo\",\"value\":123}"
//             );
           
//             // Test Step Functions functionality
//             System.out.println("\n=== Testing Step Functions ===");
//             // Your state machine derived from the execution you shared:
//             // Execution ARN: arn:aws:states:us-east-1:050284121366:execution:DemoEchoStateMachine:...
//             // => State Machine ARN:
//             String stateMachineArn = "arn:aws:states:us-east-1:050284121366:stateMachine:DemoEchoStateMachine";
//             String input = "{\"source\":\"java-demo\",\"value\":123}";

//             String execArn = StepFunctionRunner.startExecution(stateMachineArn, input);
//             System.out.println("Started Step Functions execution: " + execArn);

//             // Block until the execution finishes and print the result
//             StepFunctionRunner.waitForCompletion(execArn);

//         } catch (Exception e) {
//             System.err.println("Error during AWS operations: " + e.getMessage());
//             e.printStackTrace();
//         }
        
//         System.out.println("\nDemo completed successfully!");
//     }
// }
package aws.demo;

import aws.demo.ec2.EC2Checker;
import aws.demo.lambda.LambdaInvoker;
import aws.demo.s3.S3Uploader;
import aws.demo.sqs.SQSPublisher;
import aws.demo.step.StepFunctionRunner;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AwsDemoApp {

  private static final ExecutorService EXEC = Executors.newCachedThreadPool();

  public static void main(String[] args) throws Exception {
    int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

    // GET /
    server.createContext("/", ex -> respond(ex, 200,
        "aws-demo-project is running. Try /healthz or POST /run\n"));

    // GET /healthz
    server.createContext("/healthz", ex -> respond(ex, 200, "ok"));

    // POST /run  (triggers your existing demo work in background)
    server.createContext("/run", ex -> {
      if (!"POST".equalsIgnoreCase(ex.getRequestMethod())) {
        respond(ex, 405, "Use POST /run\n");
        return;
      }
      EXEC.submit(() -> {
        try {
          runDemo();
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
      respond(ex, 202, "Started demo run. Check container logs for progress.\n");
    });

    server.setExecutor(Executors.newFixedThreadPool(4));
    server.start();
    System.out.println("HTTP server started on port " + port);
  }

  /** Your existing AWS demo logic */
  private static void runDemo() throws Exception {
    System.out.println("AWS Java Demo Project Initialized.");

    try {
      System.out.println("\n=== Testing EC2 ===");
      EC2Checker.listInstances();

      System.out.println("\n=== Testing S3 ===");
      // NOTE: This path likely doesn't exist in the container. Adjust or skip.
      // S3Uploader.uploadFile("demo-bucket-alex-2025", "java-demo/test.txt", "/Users/alex/test.txt");

      System.out.println("\n=== Testing Lambda ===");
      String lambdaFunctionName = System.getenv().getOrDefault("LAMBDA_FUNCTION_NAME", "demo-python-lambda");
      LambdaInvoker.invoke(lambdaFunctionName, "{\"hello\":\"from-java\"}");

      System.out.println("\n=== Testing SQS ===");
      String sqsQueueUrl = System.getenv().getOrDefault("SQS_QUEUE_URL", 
          "https://sqs.us-east-1.amazonaws.com/YOUR_ACCOUNT_ID/testQueueStandard");
      SQSPublisher.sendMessage(sqsQueueUrl, "{\"event\":\"demo\",\"value\":123}");

      System.out.println("\n=== Testing Step Functions ===");
      String stateMachineArn = System.getenv().getOrDefault("STEP_FUNCTION_ARN", 
          "arn:aws:states:us-east-1:YOUR_ACCOUNT_ID:stateMachine:DemoEchoStateMachine");
      String input = "{\"source\":\"java-demo\",\"value\":123}";
      String execArn = StepFunctionRunner.startExecution(stateMachineArn, input);
      System.out.println("Started Step Functions execution: " + execArn);
      StepFunctionRunner.waitForCompletion(execArn);

    } catch (Exception e) {
      System.err.println("Error during AWS operations: " + e.getMessage());
      e.printStackTrace();
    }

    System.out.println("\nDemo completed!");
  }

  private static void respond(HttpExchange ex, int status, String body) throws IOException {
    byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
    ex.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
    ex.sendResponseHeaders(status, bytes.length);
    try (OutputStream os = ex.getResponseBody()) {
      os.write(bytes);
    } finally {
      ex.close();
    }
  }
}
