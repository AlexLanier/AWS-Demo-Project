package aws.demo.sqs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to cover SQSPublisher constructor
 */
class SQSPublisherConstructorTest {

    @Test
    void testConstructor() {
        // Test that constructor can be called
        assertDoesNotThrow(() -> {
            SQSPublisher publisher = new SQSPublisher();
            assertNotNull(publisher);
        });
    }
}
