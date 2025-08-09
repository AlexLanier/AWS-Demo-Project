// Directory: src/main/java/aws/demo/sqs/SQSPublisher.java
package aws.demo.sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class SQSPublisher {
    public static void sendMessage(String queueUrl, String message) {
        SqsClient sqs = SqsClient.create();

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();

        sqs.sendMessage(request);
        System.out.println("Message sent to SQS");
    }
}