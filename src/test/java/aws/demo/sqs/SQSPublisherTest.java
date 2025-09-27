package aws.demo.sqs;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SQSPublisherTest {

    @Test
    void sendMessage_withValidRequest_sendsMessage() {
        // Arrange
        try (MockedStatic<SqsClient> sqsClientMock = mockStatic(SqsClient.class)) {
            SqsClient mockClient = mock(SqsClient.class);
            sqsClientMock.when(SqsClient::create).thenReturn(mockClient);

            SendMessageResponse response = SendMessageResponse.builder()
                    .messageId("test-message-id")
                    .build();
            when(mockClient.sendMessage(any(SendMessageRequest.class))).thenReturn(response);

            // Act
            SQSPublisher.sendMessage("https://sqs.us-east-1.amazonaws.com/123456789012/test-queue", 
                    "{\"test\":\"message\"}");

            // Assert
            verify(mockClient).sendMessage(any(SendMessageRequest.class));
        }
    }

    @Test
    void sendMessage_withException_throwsException() {
        // Arrange
        try (MockedStatic<SqsClient> sqsClientMock = mockStatic(SqsClient.class)) {
            SqsClient mockClient = mock(SqsClient.class);
            sqsClientMock.when(SqsClient::create).thenReturn(mockClient);

            when(mockClient.sendMessage(any(SendMessageRequest.class)))
                    .thenThrow(new RuntimeException("SQS Error"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> 
                SQSPublisher.sendMessage("https://sqs.us-east-1.amazonaws.com/123456789012/test-queue", 
                        "{\"test\":\"message\"}"));
        }
    }

    @Test
    void sendMessage_withNullQueueUrl_handlesGracefully() {
        // Arrange
        try (MockedStatic<SqsClient> sqsClientMock = mockStatic(SqsClient.class)) {
            SqsClient mockClient = mock(SqsClient.class);
            sqsClientMock.when(SqsClient::create).thenReturn(mockClient);

            SendMessageResponse response = SendMessageResponse.builder()
                    .messageId("test-message-id")
                    .build();
            when(mockClient.sendMessage(any(SendMessageRequest.class))).thenReturn(response);

            // Act - should not throw
            assertDoesNotThrow(() -> 
                SQSPublisher.sendMessage(null, "{\"test\":\"message\"}"));

            // Assert
            verify(mockClient).sendMessage(any(SendMessageRequest.class));
        }
    }

    @Test
    void sendMessage_withNullMessage_handlesGracefully() {
        // Arrange
        try (MockedStatic<SqsClient> sqsClientMock = mockStatic(SqsClient.class)) {
            SqsClient mockClient = mock(SqsClient.class);
            sqsClientMock.when(SqsClient::create).thenReturn(mockClient);

            SendMessageResponse response = SendMessageResponse.builder()
                    .messageId("test-message-id")
                    .build();
            when(mockClient.sendMessage(any(SendMessageRequest.class))).thenReturn(response);

            // Act - should not throw
            assertDoesNotThrow(() -> 
                SQSPublisher.sendMessage("https://sqs.us-east-1.amazonaws.com/123456789012/test-queue", null));

            // Assert
            verify(mockClient).sendMessage(any(SendMessageRequest.class));
        }
    }

    @Test
    void sendMessage_withEmptyMessage_handlesGracefully() {
        // Arrange
        try (MockedStatic<SqsClient> sqsClientMock = mockStatic(SqsClient.class)) {
            SqsClient mockClient = mock(SqsClient.class);
            sqsClientMock.when(SqsClient::create).thenReturn(mockClient);

            SendMessageResponse response = SendMessageResponse.builder()
                    .messageId("test-message-id")
                    .build();
            when(mockClient.sendMessage(any(SendMessageRequest.class))).thenReturn(response);

            // Act
            SQSPublisher.sendMessage("https://sqs.us-east-1.amazonaws.com/123456789012/test-queue", "");

            // Assert
            verify(mockClient).sendMessage(any(SendMessageRequest.class));
        }
    }
}
