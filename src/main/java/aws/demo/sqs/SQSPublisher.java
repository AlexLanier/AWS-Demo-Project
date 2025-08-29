// Directory: src/main/java/aws/demo/sqs/SQSPublisher.java
package aws.demo.sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public class SQSPublisher {
    public static void sendMessage(String queueUrl, String message) {
        try (SqsClient sqs = SqsClient.create()) { // Region/creds from env or ~/.aws
            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(message)
                    .build();

            SendMessageResponse resp = sqs.sendMessage(request);
            System.out.println("Message sent to SQS. MessageId=" + resp.messageId());
        } catch (Exception e) {
            System.err.println("Error sending message to SQS: " + e.getMessage());
            throw e;
        }
    }
}
