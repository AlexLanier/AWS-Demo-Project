package aws.demo.ec2;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeImagesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.Image;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Tag;

import java.util.Optional;

public class EC2Checker {

    public static void listInstances() {
        try (Ec2Client ec2 = Ec2Client.create()) {
            var response = ec2.describeInstances(DescribeInstancesRequest.builder().build());

            if (response.reservations().isEmpty()) {
                System.out.println("No EC2 instances found.");
                return;
            }

            response.reservations().stream()
                    .flatMap(r -> r.instances().stream())
                    .forEach(inst -> printInstanceDetails(ec2, inst));

        } catch (Exception e) {
            System.err.println("Error listing EC2 instances: " + e.getMessage());
            throw e;
        }
    }

    private static void printInstanceDetails(Ec2Client ec2, Instance instance) {
        System.out.println("\n=== EC2 Instance Details ===");
        System.out.println("Instance ID: " + instance.instanceId());
        System.out.println("State: " + instance.state().nameAsString());
        System.out.println("Instance Type: " + instance.instanceTypeAsString());
        System.out.println("Private IP: " + valueOrNA(instance.privateIpAddress()));
        System.out.println("Public IP: " + valueOrNA(instance.publicIpAddress()));
        System.out.println("AMI ID: " + instance.imageId());

        // Lookup AMI friendly name/description (e.g., "Red Hat Enterprise Linux 10 ...")
        describeAmi(ec2, instance.imageId()).ifPresent(img -> {
            System.out.println("AMI Name: " + valueOrNA(img.name()));
            System.out.println("AMI Description: " + valueOrNA(img.description()));
        });

        if (!instance.tags().isEmpty()) {
            System.out.println("Tags:");
            for (Tag tag : instance.tags()) {
                System.out.println("  - " + tag.key() + " = " + tag.value());
            }
        } else {
            System.out.println("Tags: (none)");
        }
        System.out.println("============================");
    }

    private static Optional<Image> describeAmi(Ec2Client ec2, String imageId) {
        try {
            var resp = ec2.describeImages(DescribeImagesRequest.builder()
                    .imageIds(imageId)
                    .build());
            if (!resp.images().isEmpty()) {
                return Optional.of(resp.images().get(0));
            }
        } catch (Exception e) {
            // Handle lack of permissions or cross-region/invalid AMI gracefully
            System.out.println("Could not retrieve AMI details for " + imageId + ": " + e.getMessage());
        }
        return Optional.empty();
    }

    private static String valueOrNA(String s) {
        return (s == null || s.isBlank()) ? "N/A" : s;
    }
}
