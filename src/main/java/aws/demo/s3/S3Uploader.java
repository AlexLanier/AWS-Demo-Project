// Directory: src/main/java/aws/demo/s3/S3Uploader.java
package aws.demo.s3;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Paths;

public class S3Uploader {
    public static void uploadFile(String bucketName, String key, String filePath) {
        try (S3Client s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {

            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3.putObject(putReq, Paths.get(filePath));
            System.out.println("Uploaded to S3: " + key);
        } catch (Exception e) {
            System.err.println("Error uploading to S3: " + e.getMessage());
            throw e;
        }
    }
}