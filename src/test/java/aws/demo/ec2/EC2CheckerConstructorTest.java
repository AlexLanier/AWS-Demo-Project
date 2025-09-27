package aws.demo.ec2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to cover EC2Checker constructor and other uncovered methods
 */
class EC2CheckerConstructorTest {

    @Test
    void testConstructor() {
        // Test that constructor can be called
        assertDoesNotThrow(() -> {
            EC2Checker checker = new EC2Checker();
            assertNotNull(checker);
        });
    }

}
