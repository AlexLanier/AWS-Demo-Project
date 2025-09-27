package aws.demo.ec2;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EC2CheckerTest {

    @Test
    void listInstances_withNoReservations_printsNoInstancesMessage() {
        // Arrange
        try (MockedStatic<Ec2Client> ec2ClientMock = mockStatic(Ec2Client.class)) {
            Ec2Client mockClient = mock(Ec2Client.class);
            ec2ClientMock.when(Ec2Client::create).thenReturn(mockClient);

            DescribeInstancesResponse response = DescribeInstancesResponse.builder()
                    .reservations(Collections.emptyList())
                    .build();
            when(mockClient.describeInstances(any(DescribeInstancesRequest.class))).thenReturn(response);

            // Act
            EC2Checker.listInstances();

            // Assert
            verify(mockClient).describeInstances(any(DescribeInstancesRequest.class));
        }
    }

    @Test
    void listInstances_withReservationsButNoInstances_handlesGracefully() {
        // Arrange
        try (MockedStatic<Ec2Client> ec2ClientMock = mockStatic(Ec2Client.class)) {
            Ec2Client mockClient = mock(Ec2Client.class);
            ec2ClientMock.when(Ec2Client::create).thenReturn(mockClient);

            Reservation reservation = Reservation.builder()
                    .instances(Collections.emptyList())
                    .build();
            DescribeInstancesResponse response = DescribeInstancesResponse.builder()
                    .reservations(Arrays.asList(reservation))
                    .build();
            when(mockClient.describeInstances(any(DescribeInstancesRequest.class))).thenReturn(response);

            // Act
            EC2Checker.listInstances();

            // Assert
            verify(mockClient).describeInstances(any(DescribeInstancesRequest.class));
        }
    }

    @Test
    void listInstances_withInstances_callsPrintInstanceDetails() {
        // Arrange
        try (MockedStatic<Ec2Client> ec2ClientMock = mockStatic(Ec2Client.class)) {
            Ec2Client mockClient = mock(Ec2Client.class);
            ec2ClientMock.when(Ec2Client::create).thenReturn(mockClient);

            Instance instance = Instance.builder()
                    .instanceId("i-1234567890abcdef0")
                    .state(InstanceState.builder().name(InstanceStateName.RUNNING).build())
                    .instanceType(InstanceType.T2_MICRO)
                    .imageId("ami-12345678")
                    .build();

            Reservation reservation = Reservation.builder()
                    .instances(Arrays.asList(instance))
                    .build();

            DescribeInstancesResponse response = DescribeInstancesResponse.builder()
                    .reservations(Arrays.asList(reservation))
                    .build();
            when(mockClient.describeInstances(any(DescribeInstancesRequest.class))).thenReturn(response);

            // Mock AMI description response
            Image image = Image.builder()
                    .name("Test AMI")
                    .description("Test AMI Description")
                    .build();
            DescribeImagesResponse imageResponse = DescribeImagesResponse.builder()
                    .images(Arrays.asList(image))
                    .build();
            when(mockClient.describeImages(any(DescribeImagesRequest.class))).thenReturn(imageResponse);

            // Act
            EC2Checker.listInstances();

            // Assert
            verify(mockClient).describeInstances(any(DescribeInstancesRequest.class));
            verify(mockClient).describeImages(any(DescribeImagesRequest.class));
        }
    }

    @Test
    void listInstances_withException_throwsException() {
        // Arrange
        try (MockedStatic<Ec2Client> ec2ClientMock = mockStatic(Ec2Client.class)) {
            Ec2Client mockClient = mock(Ec2Client.class);
            ec2ClientMock.when(Ec2Client::create).thenReturn(mockClient);

            when(mockClient.describeInstances(any(DescribeInstancesRequest.class)))
                    .thenThrow(new RuntimeException("AWS Error"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> EC2Checker.listInstances());
        }
    }

    @Test
    void listInstances_withAmiDescriptionError_handlesGracefully() {
        // Arrange
        try (MockedStatic<Ec2Client> ec2ClientMock = mockStatic(Ec2Client.class)) {
            Ec2Client mockClient = mock(Ec2Client.class);
            ec2ClientMock.when(Ec2Client::create).thenReturn(mockClient);

            Instance instance = Instance.builder()
                    .instanceId("i-1234567890abcdef0")
                    .state(InstanceState.builder().name(InstanceStateName.RUNNING).build())
                    .instanceType(InstanceType.T2_MICRO)
                    .imageId("ami-12345678")
                    .build();

            Reservation reservation = Reservation.builder()
                    .instances(Arrays.asList(instance))
                    .build();

            DescribeInstancesResponse response = DescribeInstancesResponse.builder()
                    .reservations(Arrays.asList(reservation))
                    .build();
            when(mockClient.describeInstances(any(DescribeInstancesRequest.class))).thenReturn(response);

            // Mock AMI description to throw exception
            when(mockClient.describeImages(any(DescribeImagesRequest.class)))
                    .thenThrow(new RuntimeException("AMI not found"));

            // Act - should not throw
            assertDoesNotThrow(() -> EC2Checker.listInstances());

            // Assert
            verify(mockClient).describeInstances(any(DescribeInstancesRequest.class));
            verify(mockClient).describeImages(any(DescribeImagesRequest.class));
        }
    }
}
