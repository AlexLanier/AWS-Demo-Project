package aws.demo.lambda;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LambdaInvokerTest {

    @Test
    void invoke_withValidRequest_returnsJsonNode() {
        // Arrange
        try (MockedStatic<LambdaClient> lambdaClientMock = mockStatic(LambdaClient.class)) {
            LambdaClient mockClient = mock(LambdaClient.class);
            lambdaClientMock.when(LambdaClient::create).thenReturn(mockClient);

            String responsePayload = "{\"statusCode\":200,\"body\":\"Hello from Lambda\"}";
            InvokeResponse response = InvokeResponse.builder()
                    .statusCode(200)
                    .payload(SdkBytes.fromUtf8String(responsePayload))
                    .build();
            when(mockClient.invoke(any(InvokeRequest.class))).thenReturn(response);

            // Act
            JsonNode result = LambdaInvoker.invoke("test-function", "{\"test\":\"data\"}");

            // Assert
            assertNotNull(result);
            assertEquals(200, result.get("statusCode").asInt());
            assertEquals("Hello from Lambda", result.get("body").asText());
            verify(mockClient).invoke(any(InvokeRequest.class));
        }
    }

    @Test
    void invoke_withInvalidJsonResponse_throwsRuntimeException() {
        // Arrange
        try (MockedStatic<LambdaClient> lambdaClientMock = mockStatic(LambdaClient.class)) {
            LambdaClient mockClient = mock(LambdaClient.class);
            lambdaClientMock.when(LambdaClient::create).thenReturn(mockClient);

            String invalidJson = "invalid json response";
            InvokeResponse response = InvokeResponse.builder()
                    .statusCode(200)
                    .payload(SdkBytes.fromUtf8String(invalidJson))
                    .build();
            when(mockClient.invoke(any(InvokeRequest.class))).thenReturn(response);

            // Act & Assert
            assertThrows(RuntimeException.class, () -> 
                LambdaInvoker.invoke("test-function", "{\"test\":\"data\"}"));
        }
    }

    @Test
    void invoke_withLambdaException_throwsRuntimeException() {
        // Arrange
        try (MockedStatic<LambdaClient> lambdaClientMock = mockStatic(LambdaClient.class)) {
            LambdaClient mockClient = mock(LambdaClient.class);
            lambdaClientMock.when(LambdaClient::create).thenReturn(mockClient);

            when(mockClient.invoke(any(InvokeRequest.class)))
                    .thenThrow(new RuntimeException("Lambda invocation failed"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> 
                LambdaInvoker.invoke("test-function", "{\"test\":\"data\"}"));
        }
    }

    @Test
    void invoke_withEmptyResponse_handlesGracefully() {
        // Arrange
        try (MockedStatic<LambdaClient> lambdaClientMock = mockStatic(LambdaClient.class)) {
            LambdaClient mockClient = mock(LambdaClient.class);
            lambdaClientMock.when(LambdaClient::create).thenReturn(mockClient);

            InvokeResponse response = InvokeResponse.builder()
                    .statusCode(200)
                    .payload(SdkBytes.fromUtf8String("{}"))
                    .build();
            when(mockClient.invoke(any(InvokeRequest.class))).thenReturn(response);

            // Act
            JsonNode result = LambdaInvoker.invoke("test-function", "{\"test\":\"data\"}");

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Test
    void invoke_withNullPayload_handlesGracefully() {
        // Arrange
        try (MockedStatic<LambdaClient> lambdaClientMock = mockStatic(LambdaClient.class)) {
            LambdaClient mockClient = mock(LambdaClient.class);
            lambdaClientMock.when(LambdaClient::create).thenReturn(mockClient);

            InvokeResponse response = InvokeResponse.builder()
                    .statusCode(200)
                    .payload(null)
                    .build();
            when(mockClient.invoke(any(InvokeRequest.class))).thenReturn(response);

            // Act & Assert
            assertThrows(RuntimeException.class, () -> 
                LambdaInvoker.invoke("test-function", "{\"test\":\"data\"}"));
        }
    }
}
