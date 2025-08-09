// Directory: src/main/java/aws/demo/ec2/EC2Checker.java
package aws.demo.ec2;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.Reservation;

public class EC2Checker {
    public static void listInstances() {
        Ec2Client ec2 = Ec2Client.create();

        DescribeInstancesRequest request = DescribeInstancesRequest.builder().build();
        ec2.describeInstances(request).reservations().stream()
                .flatMap(r -> r.instances().stream())
                .forEach(instance -> System.out.println("Instance: " + instance.instanceId()));
    }
}