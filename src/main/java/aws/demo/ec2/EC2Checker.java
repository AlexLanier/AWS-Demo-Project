// Directory: src/main/java/aws/demo/ec2/EC2Checker.java
package aws.demo.ec2;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.Reservation;

public class EC2Checker {
    public static void listInstances() {
        try (Ec2Client ec2 = Ec2Client.create()) {
            DescribeInstancesRequest request = DescribeInstancesRequest.builder().build();
            var response = ec2.describeInstances(request);
            
            if (response.reservations().isEmpty()) {
                System.out.println("No EC2 instances found.");
            } else {
                response.reservations().stream()
                        .flatMap(r -> r.instances().stream())
                        .forEach(instance -> System.out.println("Instance: " + instance.instanceId() + 
                                " (State: " + instance.state().name() + ")"));
            }
        } catch (Exception e) {
            System.err.println("Error listing EC2 instances: " + e.getMessage());
            throw e;
        }
    }
}