package aws.demo.s3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to cover S3Uploader constructor
 */
class S3UploaderConstructorTest {

    @Test
    void testConstructor() {
        // Test that constructor can be called
        assertDoesNotThrow(() -> {
            S3Uploader uploader = new S3Uploader();
            assertNotNull(uploader);
        });
    }
}
